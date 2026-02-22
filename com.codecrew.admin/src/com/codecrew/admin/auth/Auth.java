/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.auth;

import com.codecrew.admin.db.DbConnection;
import com.mysql.cj.jdbc.PreparedStatementWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author USER
 */
public class Auth {

    public static ResultSet getAuth(String uName, String pWord) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM account where name = ?";
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, uName);
        ResultSet result = pst.executeQuery();
        
        if(result.next()){
            String storedHash = result.getString("password");

            boolean pCheck = BCrypt.checkpw(pWord, storedHash);
            
            if(pCheck==true){
                return result;
            }else{
                return null;
            }
            
        }else{
            return null;
        }
        

    }
    
}
