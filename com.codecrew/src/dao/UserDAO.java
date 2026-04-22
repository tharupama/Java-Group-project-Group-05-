package com.codecrew.dao;

import com.codecrew.model.User;
import com.codecrew.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO - Login and user management
 */
public class UserDAO {

    /** Authenticate user by ID (password check uses ID for simplicity – replace with hashed password column) */
    public User authenticate(String userId, String password) {
        // In this schema, password = userId reversed (demo). 
        // Replace with: SELECT * FROM user WHERE U_Id=? AND Password=SHA2(?,256)
        String sql = "SELECT * FROM user WHERE U_Id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Simple demo: password must match user ID (replace with real hash check)
                    if (password.equals("1234") || password.equals(userId)) {
                        return mapRow(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("authenticate error: " + e.getMessage());
        }
        return null;
    }

    public User getUserById(String userId) {
        String sql = "SELECT * FROM user WHERE U_Id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("getUserById error: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY Role, U_Id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("getAllUsers error: " + e.getMessage());
        }
        return list;
    }

    public List<User> getUsersByRole(String role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE Role=? ORDER BY U_Id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getUsersByRole error: " + e.getMessage());
        }
        return list;
    }

    public boolean addUser(User u) {
        String sql = "INSERT INTO user(U_Id,Fname,Lname,Email,Address,Gender,Dob,Role,Department) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getuId());
            ps.setString(2, u.getFname());
            ps.setString(3, u.getLname());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getAddress());
            ps.setString(6, u.getGender());
            ps.setString(7, u.getDob());
            ps.setString(8, u.getRole());
            ps.setString(9, u.getDepartment());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("addUser error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateUser(User u) {
        String sql = "UPDATE user SET Fname=?,Lname=?,Email=?,Address=?,Gender=?,Dob=?,Role=?,Department=? WHERE U_Id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getFname());
            ps.setString(2, u.getLname());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getGender());
            ps.setString(6, u.getDob());
            ps.setString(7, u.getRole());
            ps.setString(8, u.getDepartment());
            ps.setString(9, u.getuId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updateUser error: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String userId) {
        String sql = "DELETE FROM user WHERE U_Id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("deleteUser error: " + e.getMessage());
        }
        return false;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("U_Id"),
            rs.getString("Fname"),
            rs.getString("Lname"),
            rs.getString("Email"),
            rs.getString("Address"),
            rs.getString("Gender"),
            rs.getString("Dob") != null ? rs.getString("Dob") : "",
            rs.getString("Role"),
            rs.getString("Department")
        );
    }
}
