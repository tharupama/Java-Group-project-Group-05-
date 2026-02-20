/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class DbConnection {
    private static DbConnection dbcon;
    private Connection conn;
    
    private DbConnection() throws ClassNotFoundException, SQLException{
   
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/posdb","root","1234");
    }
    
    public Connection getConn(){
        return conn;
    }
    
    public static DbConnection getInstance() throws ClassNotFoundException, SQLException{
        
        if(dbcon == null){
        dbcon = new DbConnection();
        }
        
        return dbcon;
        
    }
    
}
