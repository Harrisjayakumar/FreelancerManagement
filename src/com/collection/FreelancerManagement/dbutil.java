package com.collection.FreelancerManagement;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbutil {
    public Connection getDBConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancer", "Harris", "");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}