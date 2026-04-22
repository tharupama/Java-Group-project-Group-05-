package com.codecrew;

import com.codecrew.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.codecrew.Lecturer;

public class LoginService {

    public Lecturer login(String username, String password) {

        try {
            // Get DB connection from DBConnection class
            Connection con = DBConnection.getConnection();

            // Check username + password + role in user table
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM user WHERE Uname=? AND Password=? AND Role='Lecturer'"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // If found → create Lecturer object and return it
            if (rs.next()) {
                Lecturer lec = new Lecturer(
                    rs.getString("U_Id"),
                    rs.getString("Uname"),
                    rs.getString("Department"),
                    rs.getString("Email")
                );
                return lec; // login success
            }

        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
        }

        return null; // login failed
    }
}
