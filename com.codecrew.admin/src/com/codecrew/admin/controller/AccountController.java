/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.db.DbConnection;
import com.codecrew.admin.model.AccountModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author USER
 */
public class AccountController {
    public static boolean saveAccount(AccountModel account) throws ClassNotFoundException, SQLException{
        String sql = "INSERT INTO account VALUES (?,?,?,?,?,?)";
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        
        String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12));
        
        pst.setString(1, account.getId());
        pst.setString(2, account.getName());
        pst.setInt(3, account.getContact());
        pst.setString(4, account.getEmail());
        pst.setString(5, hashedPassword);
        pst.setString(6, account.getRole());
        
        int results = pst.executeUpdate();
    
        return results>0;
    }
    
}
