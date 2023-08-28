package utils;

public class EnvironmentManager {

    public static String getEnvironmentUrl() throws Exception {
        String environment = PropertiesReader.getEnvironment();
        return switch (environment) {
            case "DEV" -> "https://automationteststore.com/";
            case "RC" -> "https://rc.automationteststore.com/"; //not a real url, demonstration purposes only.
            case "REG" -> "https://.reg.automationteststore.com/"; //not a real url, demonstration purposes only.
            default -> throw new Exception("Invalid environment found in config.properties: " + environment);
        };
    }
}
