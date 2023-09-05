package utils;

public class PropertiesReader {

    public static String getBrowser() {
        return System.getProperty("browser", "chrome");
    }

    public static String getEnvironment() {
        return System.getProperty("environment", "DEV");
    }

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }
}