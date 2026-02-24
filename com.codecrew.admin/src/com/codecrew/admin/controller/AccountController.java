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
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.ResultSet;

/**
 *
 * @author USER
 */
public class AccountController {
    public static boolean saveAccount(AccountModel account) throws ClassNotFoundException, SQLException{
        String sql = "INSERT INTO user VALUES (?,?,?,?,?,?,?)";
        String sql2 = "SELECT name from account where name = ?";
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        PreparedStatement pst2 = conn.prepareStatement(sql2);
        pst2.setString(1, account.getName());
        ResultSet rs =  pst2.executeQuery();
        if(!rs.next()){
            String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12));
        
        pst.setString(1, account.getId());
        pst.setString(2, account.getName());
        pst.setInt(3, account.getContact());
        pst.setString(4, account.getEmail());
        pst.setString(5, hashedPassword);
        pst.setString(6, account.getRole());
        pst.setString(7, account.getDept());
        
        int results = pst.executeUpdate();
    
        return results>0;
        }else{
            return false;
        }
        
        
        
    }

    public static boolean updateAccount(AccountModel account) throws SQLException, ClassNotFoundException {
        Connection conn = DbConnection.getInstance().getConn();
        if (account.getPassword() != null) {
            String sql = "Update account SET name=?, contact=?, email=?, password=?, role=?, department=? WHERE id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12));

            pst.setString(1, account.getName());
            pst.setInt(2, account.getContact());
            pst.setString(3, account.getEmail());
            pst.setString(4, hashedPassword);
            pst.setString(5, account.getRole());
            
            pst.setString(6, account.getDept());
            pst.setString(7, account.getId());

            int results = pst.executeUpdate();

            return results > 0;

        } else {
            String sql = "Update account SET name=?, contact=?, email=?, role=?, department=? WHERE id=?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, account.getName());
            pst.setInt(2, account.getContact());
            pst.setString(3, account.getEmail());

            pst.setString(4, account.getRole());
            
            pst.setString(5, account.getDept());
            pst.setString(6, account.getId());

            int results = pst.executeUpdate();

            return results > 0;

        }
    }

    public static boolean deleteAccount(String id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM account WHERE id = ?";
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1,id);
        
        int result = pst.executeUpdate();
        
        return result>0;
    }

    public static void search(String text, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM account WHERE id LIKE ? OR name LIKE ? OR contact LIKE ? OR email LIKE ? OR role LIKE ? OR department LIKE ?";
        
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1,"%"+text+"%");
        pst.setString(2,"%"+text+"%");
        pst.setString(3,"%"+text+"%");
        pst.setString(4,"%"+text+"%");
        pst.setString(5,"%"+text+"%");
        pst.setString(6,"%"+text+"%");
        ResultSet result = pst.executeQuery();
        dtm.setRowCount(0);
        if(result!=null){
            System.out.println("thinawa");
        }
        while(result.next()){
            
            dtm.addRow(new Object[]{result.getString("id"), result.getString("name"),result.getString("contact"),result.getString("email"),result.getString("role"),result.getString("department")});
        }
    }

    public static void tableLoad(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
                dtm.setRowCount(0);
    Connection conn = DbConnection.getInstance().getConn();
    String sql = "SELECT * FROM account";
    PreparedStatement pst = conn.prepareStatement(sql);
    ResultSet result = pst.executeQuery();
    
    while(result.next()){
    String id = result.getString("id");
    String name = result.getString("name");
    String contact = result.getString("contact");
    String email = result.getString("email");
    String role = result.getString("role");
    String dept = result.getString("department");
    
    dtm.addRow(new Object[]{id,name,contact,email,role,dept});
    }
    }


   
 
    
}
