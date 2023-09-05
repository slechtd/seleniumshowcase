package utils;

public class PropertiesReader {

    public static String getBrowser() {
        return System.getProperty("browser", "chrome"); // Provide a default value if not set
    }

    public static String getEnvironment() {
        return System.getProperty("environment", "DEV"); // Provide a default value if not set
    }
}
