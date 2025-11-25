package gov.cms.esmd.main;

import gov.cms.esmd.config.model.AppConfig;
import gov.cms.esmd.config.YamlConfigLoader;
import org.junit.jupiter.api.Test;

public class MainAppTest {

    @Test
    public void getConfig() {
        YamlConfigLoader loader = new YamlConfigLoader("app-settings-config.yml");
        AppConfig config = loader.getConfig();
        System.out.println("Base folder: " + config.getAppSettings().getBaseFileLocationFolder());


    }
}

