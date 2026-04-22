package com.codecrew.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management",
                "root",
                "MahimA1001@"
            );
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}