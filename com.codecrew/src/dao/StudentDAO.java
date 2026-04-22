package com.codecrew.dao;

import com.codecrew.model.Student;
import com.codecrew.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO - Data Access Object for Student operations
 */
public class StudentDAO {

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY Registration_No";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getAllStudents error: " + e.getMessage());
        }
        return list;
    }

    public List<Student> getStudentsByDepartment(String department) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE Department=? ORDER BY Registration_No";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, department);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getStudentsByDepartment error: " + e.getMessage());
        }
        return list;
    }

    public Student getStudentById(String regNo) {
        String sql = "SELECT * FROM student WHERE Registration_No=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("getStudentById error: " + e.getMessage());
        }
        return null;
    }

    public boolean addStudent(Student s) {
        String sql = "INSERT INTO student(Registration_No,Fname,Lname,Year,Department,Batch) VALUES(?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getRegistrationNo());
            ps.setString(2, s.getFname());
            ps.setString(3, s.getLname());
            ps.setInt(4, s.getYear());
            ps.setString(5, s.getDepartment());
            ps.setInt(6, s.getBatch());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("addStudent error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateStudent(Student s) {
        String sql = "UPDATE student SET Fname=?,Lname=?,Year=?,Department=?,Batch=? WHERE Registration_No=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getFname());
            ps.setString(2, s.getLname());
            ps.setInt(3, s.getYear());
            ps.setString(4, s.getDepartment());
            ps.setInt(5, s.getBatch());
            ps.setString(6, s.getRegistrationNo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updateStudent error: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteStudent(String regNo) {
        String sql = "DELETE FROM student WHERE Registration_No=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regNo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("deleteStudent error: " + e.getMessage());
        }
        return false;
    }

    public List<Student> searchStudents(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE Registration_No LIKE ? OR Fname LIKE ? OR Lname LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw); ps.setString(2, kw); ps.setString(3, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("searchStudents error: " + e.getMessage());
        }
        return list;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
            rs.getString("Registration_No"),
            rs.getString("Fname"),
            rs.getString("Lname"),
            rs.getInt("Year"),
            rs.getString("Department"),
            rs.getInt("Batch")
        );
    }
}
