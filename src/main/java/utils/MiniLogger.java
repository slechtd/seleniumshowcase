package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MiniLogger {
    private static final boolean shouldLogToDB = PropertiesReader.shouldLogIntoDB();
    private static final String executionId = UUID.randomUUID().toString();
    private static Connection connection;
    private static final String testSuite = PropertiesReader.getTestSuite();
    private static final String browser = PropertiesReader.getBrowser();
    private static final String environment = PropertiesReader.getEnvironment();
    private static final boolean headless = PropertiesReader.isHeadless();
    private static final String info = "INFO";
    private static final String pass = "PASS";
    private static final String fail = "FAIL";

    static {
        if (shouldLogToDB) {
            connection = DatabaseConnector.getConnection();
            initializeExecution();
        }
    }

    public static void log(String message, String testCaseName) {
        if (shouldLogToDB) {
            addLogEntry(message, testCaseName, info);
        }
    }

    public static void pass(String message, String testCaseName) {
        if (shouldLogToDB) {
            addLogEntry("TEST PASSED: " + message, testCaseName, pass);
        }
    }

    public static void fail(String message, String testCaseName) {
        if (shouldLogToDB) {
            addLogEntry("TEST FAILED: " + message, testCaseName, fail);
        }
    }

    public static void fail(Throwable throwable, String testCaseName) {
        if (shouldLogToDB) {
            addLogEntry("TEST FAILED: " + throwable.toString(), testCaseName, fail);
        }
    }

    public static void addScreenshot(String path, String testCaseName) {
        String sql = "INSERT INTO screenshot_paths (execution_id, test_case_name, path) VALUES (?, ?, ?)";
        executePreparedStatement(sql, executionId, testCaseName, path);
    }

    private static void addLogEntry(String message, String testCaseName, String logType) {
        String sql = "INSERT INTO test_logs (execution_id, test_case_name, message, log_type, timestamp) VALUES (?, ?, ?, ?, ?)";
        executePreparedStatement(sql, executionId, testCaseName, message, logType, createTimestamp());
    }

    private static void initializeExecution() {
        String sql = "INSERT INTO executions (execution_id, test_suite, browser, environment, headless) VALUES (?, ?, ?, ?, ?)";
        executePreparedStatement(sql, executionId, testSuite, browser, environment, headless);
    }

    //ORIGINAL:
    /*private static void executePreparedStatement(String sql, Object... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    //DEBUG:
    private static void executePreparedStatement(String sql, Object... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            System.out.println("Executing SQL: " + preparedStatement.toString()); // Debugging line
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);  // Debugging line
            if (rowsAffected == 0) {
                System.err.println("No rows affected, insert failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException occurred: " + e.getMessage());
        }
    }


    private static String createTimestamp() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss-SSS").format(new Date());
    }
}

/*
Database Schema for MiniLogger

-- Table: executions
CREATE TABLE executions (
    execution_id VARCHAR(255) PRIMARY KEY,
    test_suite VARCHAR(255),
    browser VARCHAR(50),
    environment VARCHAR(50),
    headless BOOLEAN
);

-- Table: screenshot_paths
CREATE TABLE screenshot_paths (
    id SERIAL PRIMARY KEY,
    execution_id VARCHAR(255),
    test_case_name VARCHAR(255),
    path VARCHAR(255),
    FOREIGN KEY (execution_id) REFERENCES executions(execution_id)
);

-- Table: test_logs
CREATE TABLE test_logs (
    id SERIAL PRIMARY KEY,
    execution_id VARCHAR(255),
    test_case_name VARCHAR(255),
    message TEXT,
    log_type VARCHAR(50),
    timestamp VARCHAR(50),
    FOREIGN KEY (execution_id) REFERENCES executions(execution_id)
);
*/

