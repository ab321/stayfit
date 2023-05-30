package com.example.stayfit.model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static Connection openConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:derby:stayfit-db");
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot connect to database", e);
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot close database", e);
        }
    }
}
