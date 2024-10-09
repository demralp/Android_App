package com.example.prototype;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            // Ensure the JDBC driver is loaded
            Class.forName("com.mysql.jdbc.Driver");

            // Replace with your database connection details
            String url = "jdbc:mysql://172.26.114.217:3306/yarkindb";
            ;
            String user = "yarkin";
            String password = "1234";

            // Get a connection to the database
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to establish connection to the database.");
        }

        return connection;
    }
}




