package com.example.prototype;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    //protected static String database = "yarkindb";
   // protected static String ip = "172.26.114.217";
    //protected static String user = "yarkin";
   // protected static String pass ="1234";
   // protected static String port = "3306";

    protected static String database = "yarkindb";
    protected static String ip = "172.26.114.217";
    protected static String user = "yarkin";
    protected static String pass ="1234";
    protected static String port = "3306";

    public Connection CONN() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            String connectionstring = "jdbc:mysql://" + ip + ":" + port + "/" + database;
            conn = DriverManager.getConnection(connectionstring, user, pass);
        } catch (ClassNotFoundException e) {
            Log.e("Driver Error", "ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            Log.e("SQL Error", "SQLException: " + e.getMessage());
        }
        return conn;
    }
}
