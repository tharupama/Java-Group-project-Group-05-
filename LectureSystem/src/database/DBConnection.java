/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management",
                "root",
                "1234"  
            );
              System.out.println("Database Connection successfully");
            return con;

        } catch (Exception e) {
            System.out.println("Connection Failed");
            return null;
        }
    }
}

