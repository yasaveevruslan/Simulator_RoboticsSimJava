package com.roboticssimjava;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection
{
    public Connection databaseLink;

    public Connection getDatabaseLink() {
        String databaseName = "datafile";
        String databaseUser = "root";
        String databasePassword = "12347890";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
