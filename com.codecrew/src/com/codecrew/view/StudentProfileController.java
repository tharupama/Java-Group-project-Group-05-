/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.view;

import com.codecrew.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
                                        // methana thama inheritenss gaththae inter fase enen
public class StudentProfileController implements StudentProfileControllerInterface{

    public void updateStudent(StudentProfileEncapsulated studentProfileEncapsulated) throws ClassNotFoundException, SQLException {
        
        String sql = "UPDATE user SET Contact = ?, Email = ?, image_data = ? WHERE U_Id = ?";
        //Connection conn = DbConnection.getInstance().getConn();
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        try{
        pst.setLong(1, Long.parseLong(studentProfileEncapsulated.getContact()));
        pst.setString(2, studentProfileEncapsulated.getEmail());
        pst.setBytes(3, studentProfileEncapsulated.getProfileImg());
        pst.setString(4, studentProfileEncapsulated.getId());
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
                
        int afRows = pst.executeUpdate();
        if(afRows!=0){
            JOptionPane.showMessageDialog(null, "Updated sucessfully");
        }else{
            JOptionPane.showMessageDialog(null, "update error");
        }
        
        conn.close();
        pst.close();
    }
    
}
