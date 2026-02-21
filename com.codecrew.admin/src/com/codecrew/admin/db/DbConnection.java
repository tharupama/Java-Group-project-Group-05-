/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class DbConnection {
    private static DbConnection dbObj;
    private Connection conn;
    private String uName;
    private String pWord;
    
    private DbConnection() throws ClassNotFoundException, SQLException{
   
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/posdb",uName,pWord);
    }
    
    public Connection getConn(){
        return conn;
    }
    
    public static DbConnection getInstance() throws ClassNotFoundException, SQLException{
        
        if(dbObj == null){
        dbObj = new DbConnection();
        }
        
        return dbObj;
        
    }
    public void setUname(String uName){
        this.uName=uName;
    }
        public void setPassword(String pWord){
        this.pWord=pWord;
    }
}
