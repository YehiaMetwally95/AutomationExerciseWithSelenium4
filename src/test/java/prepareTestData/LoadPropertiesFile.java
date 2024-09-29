package prepareTestData;

import utils.PropertiesManager;

import java.io.IOException;

public class LoadPropertiesFile {
    static String propertiesFilePath = "src/main/resources/Configurations.properties";

    public static void loadPropertiesFile()
    {
        PropertiesManager.filePath = propertiesFilePath;
        try {
            PropertiesManager.loadPropertiesIntoSystem();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
