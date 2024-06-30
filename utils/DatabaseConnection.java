package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Sistema;integratedSecurity=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}





