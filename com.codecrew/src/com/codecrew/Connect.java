/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author nipun
 */
public class Connect {
    // Method should return Connection, not Connect
    public Connection getConnection() {
        Connection con = null;
        try {
            // Remove 'url:', 'user:', 'password:' labels (invalid syntax)
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management", 
                "root", 
                "1234"
            );
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
