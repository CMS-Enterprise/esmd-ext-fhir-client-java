package gov.cms.esmd;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cms.esmd.auth.api.AuthenticationAPIHandler;
import gov.cms.esmd.bundlesubmission.api.BundlePractitionerAPIHandler;
import gov.cms.esmd.bundlesubmission.api.ClinicalDocumentUploadAPIHandler;
import gov.cms.esmd.bundlesubmission.api.PreSignedURLAPIHandler;
import gov.cms.esmd.common.util.GuidUtil;
import gov.cms.esmd.config.YamlConfigLoaderJackson;
import gov.cms.esmd.notification.api.NotificationAPIHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainConsoleApplication {

    public static void main(String[] args) {
        try {
            var loader = new YamlConfigLoaderJackson("app-settings-config-jackson.yml");
            var config = loader.getConfig();

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
                var preSignedURLHandler = new PreSignedURLAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken());
                var preSignedURL = preSignedURLHandler.getPreSignedURLAsync(commonSharedGuid);
                if (preSignedURL.isSuccess()) {
                    log.info("PreSignedURL Retrieved Success - {}", new ObjectMapper().writeValueAsString(preSignedURL.getResponse()));
                    var urlList = preSignedURLHandler.processPreSignedURLResponse(preSignedURL.getResponse());
                    var clinicalDocumentUploadHandler = new ClinicalDocumentUploadAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken());
                    urlList.forEach(url -> {

                        var uploadDocumentResponse = clinicalDocumentUploadHandler.uploadClinicalDocument(url.getPartValueUrl().getValueUrl(), url.getPartValueString().getValueString());
                        try {
                            log.info("upload document response - {}", new ObjectMapper().writeValueAsString(uploadDocumentResponse.getResponse()));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                    });
                } else {
                    log.error("PreSignedURL Retrieval Failed - {}", preSignedURL.getError());
                }


                log.info("Now Processing Bundle Practitioner request!");
                var bundlePractitionerHandler = new BundlePractitionerAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken());
                var bundlePractitioner = bundlePractitionerHandler.processBundlePractitionerRequest(commonSharedGuid);
                if (bundlePractitioner.isSuccess()) {
                    log.info("Successfully process bundlePractitioner  - {}", new ObjectMapper().writeValueAsString(bundlePractitioner.getResponse()));
                } else {
                    log.error("Bundle Practitioner Failed - {}", bundlePractitioner.getError());
                }

            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
