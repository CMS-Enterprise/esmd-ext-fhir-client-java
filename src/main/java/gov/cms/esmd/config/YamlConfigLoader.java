package gov.cms.esmd.config;

import java.io.InputStream;
import java.util.Map;

import gov.cms.esmd.common.util.JsonUtil;
import gov.cms.esmd.config.model.AppConfig;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.LoaderOptions;

@Getter
public class YamlConfigLoader {

    private final AppConfig config;

    public YamlConfigLoader(String yamlFilePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(yamlFilePath)) {
            if (inputStream == null) {
                throw new RuntimeException("YAML file not found: " + yamlFilePath);
            }

            // Create LoaderOptions
            LoaderOptions loaderOptions = new LoaderOptions();
            // Constructor with LoaderOptions
            Constructor constructor = new Constructor(AppConfig.class, loaderOptions);

            // Create Yaml instance
            Yaml yaml = new Yaml(constructor);

            // Load YAML
            AppConfig yamlConfig = yaml.load(inputStream);
            Map<String, String> replacements = Map.of(
                    "${endPointBaseUrl}", yamlConfig.getAppSettings().getEndPointBaseUrl(),
                    "${fhirServerUrl}", yamlConfig.getAppSettings().getFhirServerUrl()
            );
            this.config = JsonUtil.replaceValues(yamlConfig, replacements, AppConfig.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML", e);
        }
    }

}
