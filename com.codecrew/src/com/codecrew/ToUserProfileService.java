/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author nipun
 */
public class ToUserProfileService {
    public int updateProfile(ToUserProfile profile) throws Exception {
        String rawPassword = new String(profile.getPassword());
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));

        String sql = "UPDATE user SET Uname=?, Contact=?, Email=?, Password=?, Department=? WHERE U_Id=?";

        try (Connection con = ToConnect.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, profile.getUsername());
            pst.setLong(2, profile.getContact());
            pst.setString(3, profile.getEmail());
            pst.setString(4, hashedPassword);
            pst.setString(5, profile.getDepartment());
            pst.setString(6, profile.getUserId());

            return pst.executeUpdate();
        }
    } 
}
