package com.codecrew;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToConnect {

    public static Connection getConnection(){
        Connection con = null;

        try {
            

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3309/student_management?useSSL=false&serverTimezone=UTC",
                "root",
                "1234"
            );
            System.out.println("Connected!");
            
        } catch (SQLException ex) {
            Logger.getLogger(ToConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        return con;
    }
}