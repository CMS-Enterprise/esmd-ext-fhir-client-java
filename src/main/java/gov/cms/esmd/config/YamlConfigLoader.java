package gov.cms.esmd.config;

import java.io.InputStream;

import gov.cms.esmd.config.model.AppConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.LoaderOptions;

public class YamlConfigLoader {

    private AppConfig config;

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
            this.config = yaml.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML", e);
        }
    }

    public AppConfig getConfig() {
        return config;
    }
}
