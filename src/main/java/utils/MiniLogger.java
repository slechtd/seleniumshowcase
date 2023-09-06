package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MiniLogger {

    private static final Connection connection;
    private static final String executionId;
    private static final String browser;
    private static final String environment;
    private static final boolean headless;
    private static final String testSuite;
    private static String screenshotPath;

    static {
        String dbHost = PropertiesReader.getDbHost();
        String dbUser = PropertiesReader.getDbUser();
        String dbPassword = PropertiesReader.getDbPassword();

        executionId = UUID.randomUUID().toString();
        testSuite = PropertiesReader.getTestSuite();
        browser = PropertiesReader.getBrowser();
        environment = PropertiesReader.getEnvironment();
        headless = PropertiesReader.isHeadless();

        try {
            connection = DriverManager.getConnection(dbHost, dbUser, dbPassword);
            initializeExecution();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database.");
        }
    }

    // Initialize the execution entry in the database
    private static void initializeExecution() throws SQLException {
        String sql = "INSERT INTO executions (test_suite, execution_id, browser, environment, headless) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, testSuite);
            preparedStatement.setString(2, executionId);
            preparedStatement.setString(3, browser);
            preparedStatement.setString(4, environment);
            preparedStatement.setBoolean(5, headless);
            preparedStatement.executeUpdate();
        }
    }

    public static void addScreenshot(String path, String testCaseName) {
        screenshotPath = path;
    }

    public static void log(String message, String testCaseName) {
        addLogEntry(message, testCaseName, "INFO");
    }

    public static void pass(String message, String testCaseName) {
        addLogEntry("TEST PASSED: " + message, testCaseName, "PASS");
    }

    public static void fail(String message, String testCaseName) {
        addLogEntry("TEST FAILED: " + message, testCaseName, "FAIL");
    }

    public static void fail(Throwable throwable, String testCaseName) {
        String exceptionMessage = throwable.toString();
        addLogEntry("TEST FAILED: " + exceptionMessage, testCaseName, "FAIL");
    }

    private static void addLogEntry(String message, String testCaseName, String logType) {
        String timestamp = createTimestamp();
        String sql = "INSERT INTO test_logs (execution_id, test_case_name, message, log_type, timestamp, screenshot_path) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, executionId);
            preparedStatement.setString(2, testCaseName);
            preparedStatement.setString(3, message);
            preparedStatement.setString(4, logType);
            preparedStatement.setString(5, timestamp);
            preparedStatement.setString(6, screenshotPath);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String createTimestamp() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss-SSS").format(new Date());
    }
}
