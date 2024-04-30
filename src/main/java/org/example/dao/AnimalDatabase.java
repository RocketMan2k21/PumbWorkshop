package org.example.dao;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AnimalDatabase {
    private String databaseUrl = "jdbc:sqlserver://BEST_LAPTOP;databaseName=animals_db;integratedSecurity=true;trustServerCertificate=true";
    private String username = "root";
    private String password = "";

    private Connection connection;

    public AnimalDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to establish a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, username, password);
    }

    // Method to close a database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionSource getConnectionSource() throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("spark");
        ((JdbcConnectionSource)connectionSource).setPassword("spark");
        return connectionSource;

    }


}
