package gov.cms.esmd.documentretrieval.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cms.esmd.utilities.bean.DeliveryConfirmationAPI;
import gov.cms.esmd.documentretrieval.bean.DeliveryConfirmationAPIResponse;
import gov.cms.esmd.documentretrieval.bean.DeliveryConfirmationAPIFailedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

@Slf4j
@RequiredArgsConstructor
public class DeliveryConfirmationAPIHandler {

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public DeliveryConfirmationAPIHandler(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
        // Configure camel case and pretty printing like C# DefaultOptions
        objectMapper.setPropertyNamingStrategy(
                com.fasterxml.jackson.databind.PropertyNamingStrategies.LOWER_CAMEL_CASE);
        objectMapper.writerWithDefaultPrettyPrinter();
    }

    public DeliveryConfirmationAPIHandlerResult processDeliveryConfirmation(
            String token,
            DeliveryConfirmationAPI deliveryConfirmationAPI) throws Exception {

        // Prepare HTTP POST
        HttpPost postRequest = new HttpPost(deliveryConfirmationAPI.getEndpointURL());
        postRequest.setHeader("Authorization", "Bearer " + token);
        postRequest.setHeader("Content-Type", deliveryConfirmationAPI.getContentType());
        postRequest.setEntity(new StringEntity(
                objectMapper.writeValueAsString(deliveryConfirmationAPI.getRequest()),
                StandardCharsets.UTF_8
        ));

        log.info("Request: {}", objectMapper.writeValueAsString(deliveryConfirmationAPI.getRequest()));

        // Handle timeout
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<DeliveryConfirmationAPIHandlerResult> future = executor.submit(() -> {
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                String jsonContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode < 200 || statusCode >= 300) {
                    log.error("Http Response Code: {}", statusCode);
                    log.error("Http Response Content: {}", jsonContent);
                    DeliveryConfirmationAPIFailedResponse failedResponse =
                            objectMapper.readValue(jsonContent, DeliveryConfirmationAPIFailedResponse.class);
                    return new DeliveryConfirmationAPIHandlerResult(null, failedResponse);
                } else {
                    DeliveryConfirmationAPIResponse successResponse =
                            objectMapper.readValue(jsonContent, DeliveryConfirmationAPIResponse.class);
                    return new DeliveryConfirmationAPIHandlerResult(successResponse, null);
                }
            }
        });

        try {
            // Timeout
            return future.get(
                    (long) deliveryConfirmationAPI.getHttpClientRequestTimeOutSeconds(),
                    TimeUnit.SECONDS
            );
        } catch (TimeoutException e) {
            future.cancel(true);
            log.error("Request timed out after {} seconds", deliveryConfirmationAPI.getHttpClientRequestTimeOutSeconds());
            throw e;
        } finally {
            executor.shutdownNow();
        }
    }


    @lombok.Value
    public static class DeliveryConfirmationAPIHandlerResult {
        DeliveryConfirmationAPIResponse success;
        DeliveryConfirmationAPIFailedResponse failed;
    }
}
