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
    private static final String executionStartTimestamp = createTimestamp();
    private static final String info = "INFO";
    private static final String pass = "PASS";
    private static final String fail = "FAIL";

    static {
        if (shouldLogToDB) {
            connection = DatabaseConnector.getConnection();
            initializeExecution();
        }
    }

    // PUBLIC METHODS

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
        if (shouldLogToDB) {
            String sql = "INSERT INTO screenshot_paths (execution_id, test_case_name, path) VALUES (?, ?, ?)";
            executePreparedStatement(sql, executionId, testCaseName, path);
        }
    }

    public static void addExecutionEndTimestamp() {
        if (shouldLogToDB) {
            String timestamp = createTimestamp();
            String sql = "UPDATE executions SET executionEndTimestamp = ? WHERE execution_id = ?";
            executePreparedStatement(sql, timestamp, executionId);
        }
    }

    // PRIVATE METHODS

    private static void addLogEntry(String message, String testCaseName, String logType) {
        String sql = "INSERT INTO test_logs (execution_id, test_case_name, message, log_type, timestamp) VALUES (?, ?, ?, ?, ?)";
        executePreparedStatement(sql, executionId, testCaseName, message, logType, createTimestamp());
    }

    private static void initializeExecution() {
        String sql = "INSERT INTO executions (execution_id, test_suite, browser, environment, headless, executionStartTimestamp) VALUES (?, ?, ?, ?, ?, ?)";
        executePreparedStatement(sql, executionId, testSuite, browser, environment, headless, executionStartTimestamp);
    }

    private static void executePreparedStatement(String sql, Object... params) {
        try (PreparedStatement preparedStatement = prepareStatementWithParams(sql, params)) {
            debugExecutingSQL(preparedStatement);
            int rowsAffected = executeSQL(preparedStatement);
            debugRowsAffected(rowsAffected);
            checkRowsAffected(rowsAffected);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static PreparedStatement prepareStatementWithParams(String sql, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

    private static String createTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    // DEBUG METHODS

    private static void debugExecutingSQL(PreparedStatement preparedStatement) {
        System.out.println("Executing SQL: " + preparedStatement.toString());
    }

    private static int executeSQL(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }

    private static void debugRowsAffected(int rowsAffected) {
        System.out.println("Rows affected: " + rowsAffected);
    }

    private static void checkRowsAffected(int rowsAffected) {
        if (rowsAffected == 0) {
            System.err.println("No rows affected, insert failed.");
        }
    }

    private static void handleSQLException(SQLException e) {
        e.printStackTrace();
        System.err.println("SQLException occurred: " + e.getMessage());
    }
}

// TARGET DB SCHEMA

/*
CREATE TABLE executions (
    execution_id VARCHAR(255) PRIMARY KEY,
    test_suite VARCHAR(255),
    browser VARCHAR(50),
    environment VARCHAR(50),
    headless BOOLEAN,
    executionStartTimestamp DATETIME,
    executionEndTimestamp DATETIME
);

CREATE TABLE screenshot_paths (
    id SERIAL PRIMARY KEY,
    execution_id VARCHAR(255),
    test_case_name VARCHAR(255),
    path VARCHAR(255),
    FOREIGN KEY (execution_id) REFERENCES executions(execution_id)
);

CREATE TABLE test_logs (
    id SERIAL PRIMARY KEY,
    execution_id VARCHAR(255),
    test_case_name VARCHAR(255),
    message TEXT,
    log_type VARCHAR(50),
    timestamp DATETIME,
    FOREIGN KEY (execution_id) REFERENCES executions(execution_id)
);
*/

