package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection getConnection() {
        loadJdbcDriver();
        return initializeConnection();
    }

    private static void loadJdbcDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    private static Connection initializeConnection() {
        Connection connection;
        try {
            String dbHost = PropertiesReader.getDbHost();
            String dbUser = PropertiesReader.getDbUser();
            String dbPassword = PropertiesReader.getDbPassword();
            connection = DriverManager.getConnection(dbHost, dbUser, dbPassword);
        } catch (SQLException e) {
            printSQLExceptionDetails(e);
            throw new RuntimeException("Failed to connect to database.");
        }
        return connection;
    }

    private static void printSQLExceptionDetails(SQLException e) {
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        System.err.println("Message: " + e.getMessage());

        for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
            System.err.println("Cause: " + t);
        }
    }
}
