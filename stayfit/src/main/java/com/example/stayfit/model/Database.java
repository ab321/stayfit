package com.example.stayfit.model;

import org.apache.derby.jdbc.ClientDataSource;


import javax.sql.DataSource;

public class Database {
    static final String DATABASE = "db";
    static final String USERNAME = "app";
    static final String PASSWORD = "app";
    public static final String URL = "jdbc:derby://localhost:1527/" + DATABASE + ";create=true";

    public static DataSource getDataSource(){
        ClientDataSource dataSource = new ClientDataSource();
        dataSource.setDatabaseName(DATABASE);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

}
