package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    public static ExtentReports extentReport;
    public static String extentReportPrefix;
    public static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private static final String baseDir = System.getProperty("user.dir");

    public ExtentManager() {
        super();
    }

    public static void getReport() {
        if(extentReport == null) {
            setupExtentReport();
        }
    }

    private static void setupExtentReport() {
        extentReport = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(baseDir + "/out/reports/" + extentReportPrefixName() + ".html");
        extentReport.attachReporter(spark);
        spark.config().setDocumentTitle("Test Results");
        spark.config().setTheme(Theme.DARK);

        extentReport.setSystemInfo("Environment", PropertiesReader.getEnvironment());
        extentReport.setSystemInfo("Browser", PropertiesReader.getBrowser());
    }

    private static String extentReportPrefixName() {
        String date = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        extentReportPrefix = PropertiesReader.getEnvironment() + "_" + date;
        return extentReportPrefix;
    }

    public static void flushReport() {
        extentReport.flush();
    }

    public synchronized static ExtentTest getTest() {
        return extentTest.get();
    }

    public synchronized static void createTest(String name, String description) {
        ExtentTest test = extentReport.createTest(name, description);
        extentTest.set(test);
        getTest();
    }

    public synchronized static void log(String message) {
        getTest().info(message);
    }

    public synchronized static void pass(String message) {
        getTest().pass(message);
    }

    public synchronized static void fail(String message) {
        getTest().fail(message);
    }

    public synchronized static void attachImage() {
        getTest().addScreenCaptureFromPath(ScreenshotManager.getScreenShotDestinationPath());
    }
}
