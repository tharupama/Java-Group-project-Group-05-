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
public class AccountController implements AccountControllerInterface {
private static AccountController ACObj;

public static AccountController getInstance(){
    if(ACObj==null){
        ACObj=new AccountController();
    }
    return ACObj;
}

    public boolean saveAccount(AccountModel account) throws ClassNotFoundException, SQLException{
        String sql = "INSERT INTO user VALUES (?,?,?,?,?,?,?)";
        String sql2 = "SELECT Uname from user where Uname = ?";
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
        pst.close();
        pst2.close();
        return results>0;
        }else{
            return false;
        }

    }

    public boolean updateAccount(AccountModel account) throws SQLException, ClassNotFoundException {
        Connection conn = DbConnection.getInstance().getConn();
        if (account.getPassword() != null) {
            String sql = "Update user SET Uname=?, Contact=?, Email=?, Password=?, Role=?, Department=? WHERE U_Id=?";

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
            pst.close();
            return results > 0;
            

        } else {
            String sql = "Update user SET Uname=?, Contact=?, Email=?, Role=?, Department=? WHERE U_Id=?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, account.getName());
            pst.setInt(2, account.getContact());
            pst.setString(3, account.getEmail());

            pst.setString(4, account.getRole());
            
            pst.setString(5, account.getDept());
            pst.setString(6, account.getId());

            int results = pst.executeUpdate();
            pst.close();
            return results > 0;

        }
    }

    public boolean deleteAccount(String id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM user WHERE U_Id = ?";
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1,id);
        
        int result = pst.executeUpdate();
        pst.close();
        return result>0;
    }

    public void search(String text, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM user WHERE U_Id LIKE ? OR Uname LIKE ? OR Contact LIKE ? OR Email LIKE ? OR Role LIKE ? OR Department LIKE ?";
        
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
            
            dtm.addRow(new Object[]{result.getString("U_Id"), result.getString("Uname"),result.getString("Contact"),result.getString("Email"),result.getString("Role"),result.getString("Department")});
        }
        pst.close();
    }

    public void tableLoad(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
                dtm.setRowCount(0);
    Connection conn = DbConnection.getInstance().getConn();
    String sql = "SELECT * FROM user";
    PreparedStatement pst = conn.prepareStatement(sql);
    ResultSet result = pst.executeQuery();
    
    while(result.next()){
    String id = result.getString("U_Id");
    String name = result.getString("Uname");
    String contact = result.getString("Contact");
    String email = result.getString("Email");
    String role = result.getString("Role");
    String dept = result.getString("Department");
    
    dtm.addRow(new Object[]{id,name,contact,email,role,dept});
    }
    pst.close();
    }

    
}
