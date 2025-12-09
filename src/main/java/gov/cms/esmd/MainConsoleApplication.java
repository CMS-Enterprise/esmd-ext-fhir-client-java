package gov.cms.esmd;


import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cms.esmd.auth.api.AuthenticationAPIHandler;
import gov.cms.esmd.bundlesubmission.api.PreSignedURLAPIHandler;
import gov.cms.esmd.common.util.GuidUtil;
import gov.cms.esmd.config.model.AppConfig;
import gov.cms.esmd.config.YamlConfigLoader;
import gov.cms.esmd.notification.api.NotificationAPIHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainConsoleApplication {

    public static void main(String[] args) {
        try {
            YamlConfigLoader loader = new YamlConfigLoader("app-settings-config.yml");
            AppConfig config = loader.getConfig();

            // Print some properties to verify
            log.info("HTTP Timeout: {}", config.getAppSettings().getHttpClientRequestTimeOutSeconds());
            log.info("FHIR Server URL: {}", config.getAppSettings().getFhirServerUrl());
            log.info("Authentication Endpoint: {}", config.getAppSettings().getAuthenticationAPI().getEndpointURL());
            log.info("Delivery Confirmation Endpoint: {}", config.getAppSettings().getDeliveryConfirmationAPI().getEndpointURL());
            var commonSharedGuid = GuidUtil.generateGuid();
            log.info("Common Shared GUID {}", commonSharedGuid);


            // Get Token
            log.info("Getting Token....");
            var token = new AuthenticationAPIHandler(config.getAppSettings()).getToken();
            if (token.isSuccess()) {
                log.info("token value is - {}", token.getResponse().getAccessToken());
                log.info("Now Processing Notification request!");
                var notification = new NotificationAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken()).getNotifications();
                if (notification.isSuccess()) {
                    log.info("Notification Retrieved Success - {}", new ObjectMapper().writeValueAsString(notification.getResponse()));
                } else {
                    log.error("Notification Retrieval Failed - {}", notification.getError());
                }

                log.info("Now Processing PreSignedURL request!");

                var preSignedURL = new PreSignedURLAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken()).getPreSignedURLAsync(commonSharedGuid);
                if (preSignedURL.isSuccess()) {
                    log.info("PreSignedURL Retrieved Success - {}", new ObjectMapper().writeValueAsString(preSignedURL.getResponse()));
                } else {
                    log.error("PreSignedURL Retrieval Failed - {}", preSignedURL.getError());
                }

            } else {
                log.error("Token failed {}", token.getError());
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
