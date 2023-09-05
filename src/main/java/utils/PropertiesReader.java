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

    public static String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isHeadless() {
        String headlessValue = System.getProperty("headless");
        return "true".equalsIgnoreCase(headlessValue);
    }
}