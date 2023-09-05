package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MiniManager {

    private static final String baseDir = System.getProperty("user.dir");

    public static void setupMiniReporter() {
        MiniLogger.setReportFileName(miniReportPrefixName() + ".json");
        MiniLogger.setReportFilePath(baseDir + "/out/reports");
        MiniLogger.setInfo("Environment", PropertiesReader.getEnvironment());
        MiniLogger.setInfo("Browser", PropertiesReader.getBrowser());
        MiniLogger.setInfo("Headless", String.valueOf(PropertiesReader.isHeadless()));
        MiniLogger.setInfo("Date", createDate());
    }

    private static String miniReportPrefixName() {
        return PropertiesReader.getEnvironment() + "_" + createDate();
    }

    private static String createDate() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss-SSS").format(new Date());
    }
}

