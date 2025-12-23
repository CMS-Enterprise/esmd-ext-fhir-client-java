package gov.cms.esmd.bundlesubmission;

import gov.cms.esmd.auth.api.AuthenticationAPIHandler;
import gov.cms.esmd.bundlesubmission.api.BundleSubmissionAPIHandler;
import gov.cms.esmd.bundlesubmission.api.ClinicalDocumentUploadAPIHandler;
import gov.cms.esmd.bundlesubmission.api.PreSignedURLAPIHandler;
import gov.cms.esmd.common.util.GuidUtil;
import gov.cms.esmd.config.YamlConfigLoaderJackson;
import gov.cms.esmd.config.model.AppConfigJackson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicBoolean;

@ExtendWith(MockitoExtension.class)
public class BundleSubmissionAllHandlersTest {


    @Test
    void test_path() throws Exception {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        System.out.println("==== CLASSPATH CHECK ====");
        System.out.println("Resource (root): " + cl.getResource(""));
        System.out.println("app-settings-config.yml: " +
                cl.getResource("app-settings-config.yml"));
        System.out.println("config/app-settings-config.yml: " +
                cl.getResource("config/app-settings-config.yml"));
    }

    @Test
    void test() throws Exception {

        YamlConfigLoaderJackson loader = new YamlConfigLoaderJackson("app-settings-config.yml");
        AppConfigJackson config = loader.getConfig();
        AtomicBoolean success = new AtomicBoolean(false);

        var commonSharedGuid = GuidUtil.generateGuid();


        var token = new AuthenticationAPIHandler(config.getAppSettings()).getToken();
        if (token.isSuccess()) {

            var preSignedURLHandler = new PreSignedURLAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken());
            var preSignedURL = preSignedURLHandler.getPreSignedURLAsync(commonSharedGuid);
            if (preSignedURL.isSuccess()) {
                var urlList = preSignedURLHandler.processPreSignedURLResponse(preSignedURL.getResponse());
                var clinicalDocumentUploadHandler = new ClinicalDocumentUploadAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken());
                urlList.forEach(url -> {

                    var uploadDocumentResponse = clinicalDocumentUploadHandler.uploadClinicalDocument(url.getPartValueUrl().getValueUrl(), url.getPartValueString().getValueString());
                    if (uploadDocumentResponse.isSuccess()) {
                        var bundleSubmissionAPIHandler = new BundleSubmissionAPIHandler(config.getAppSettings(), token.getResponse().getAccessToken());
                        var bundleSubmissionResponse = bundleSubmissionAPIHandler.processBundleSubmissionRequest(uploadDocumentResponse.getResponse(), commonSharedGuid);
                        success.set(bundleSubmissionResponse.isSuccess());
                    }

                });
            }





        }
        Assertions.assertTrue(success.get());



    }


}
