package service;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LecturerProfileService {

    public boolean updateLoggedLecturer(String userId, String name, String email, String department) {
        String sql = "UPDATE user SET Uname = ?, Email = ?, Department = ? WHERE U_Id = ? AND Role = 'Lecturer'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return false;
            }

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, department);
            ps.setString(4, userId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Profile Update Error: " + e.getMessage());
            return false;
        }
    }
}
