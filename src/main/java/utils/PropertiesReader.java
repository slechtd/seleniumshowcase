package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private static final String baseDir = System.getProperty("user.dir");
    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream data = new FileInputStream(baseDir + "/src/main/resources/configs/config.properties");
            properties.load(data);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static String getEnvironment() {
        return properties.getProperty("environment");
    }
}