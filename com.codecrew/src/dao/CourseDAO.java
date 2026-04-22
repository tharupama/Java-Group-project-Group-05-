package com.codecrew.dao;

import com.codecrew.model.CourseUnit;
import com.codecrew.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseDAO - Course unit operations
 */
public class CourseDAO {

    public List<CourseUnit> getAllCourses() {
        List<CourseUnit> list = new ArrayList<>();
        String sql = "SELECT * FROM course_unit ORDER BY Department_Offering, Course_code";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("getAllCourses error: " + e.getMessage());
        }
        return list;
    }

    public List<CourseUnit> getCoursesByDepartment(String dept) {
        List<CourseUnit> list = new ArrayList<>();
        String sql = "SELECT * FROM course_unit WHERE Department_Offering=? ORDER BY Course_code";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dept);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getCoursesByDepartment error: " + e.getMessage());
        }
        return list;
    }

    public List<CourseUnit> getCoursesByStudent(String studentId) {
        List<CourseUnit> list = new ArrayList<>();
        String sql = "SELECT cu.* FROM course_unit cu " +
                     "JOIN student_course sc ON sc.Course_code=cu.Course_code " +
                     "WHERE sc.ST_Id=? ORDER BY cu.Course_code";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getCoursesByStudent error: " + e.getMessage());
        }
        return list;
    }

    public CourseUnit getCourseById(String courseCode) {
        String sql = "SELECT * FROM course_unit WHERE Course_code=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, courseCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("getCourseById error: " + e.getMessage());
        }
        return null;
    }

    public boolean addCourse(CourseUnit c) {
        String sql = "INSERT INTO course_unit(Course_code,Name,Type,Credit,Lec_Name,Year,Semester,Department_Offering,Theory_Hours,Practical_Hours) " +
                     "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCourseCode());
            ps.setString(2, c.getName());
            ps.setString(3, c.getType());
            ps.setInt(4, c.getCredit());
            ps.setString(5, c.getLecName());
            ps.setInt(6, c.getYear());
            ps.setString(7, c.getSemester());
            ps.setString(8, c.getDepartmentOffering());
            ps.setInt(9, c.getTheoryHours());
            ps.setInt(10, c.getPracticalHours());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("addCourse error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateCourse(CourseUnit c) {
        String sql = "UPDATE course_unit SET Name=?,Type=?,Credit=?,Lec_Name=?,Year=?,Semester=?," +
                     "Department_Offering=?,Theory_Hours=?,Practical_Hours=? WHERE Course_code=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getType());
            ps.setInt(3, c.getCredit());
            ps.setString(4, c.getLecName());
            ps.setInt(5, c.getYear());
            ps.setString(6, c.getSemester());
            ps.setString(7, c.getDepartmentOffering());
            ps.setInt(8, c.getTheoryHours());
            ps.setInt(9, c.getPracticalHours());
            ps.setString(10, c.getCourseCode());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updateCourse error: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteCourse(String courseCode) {
        String sql = "DELETE FROM course_unit WHERE Course_code=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, courseCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("deleteCourse error: " + e.getMessage());
        }
        return false;
    }

    private CourseUnit mapRow(ResultSet rs) throws SQLException {
        return new CourseUnit(
            rs.getString("Course_code"),
            rs.getString("Name"),
            rs.getString("Type"),
            rs.getInt("Credit"),
            rs.getString("Lec_Name"),
            rs.getInt("Year"),
            rs.getString("Semester"),
            rs.getString("Department_Offering"),
            rs.getInt("Theory_Hours"),
            rs.getInt("Practical_Hours")
        );
    }

    /**
     * Get courses enrolled by a specific student
     */
    public List<CourseUnit> getEnrolledCourses(String studentId) {
        List<CourseUnit> list = new ArrayList<>();
        String sql = "SELECT cu.* FROM course_unit cu " +
                     "JOIN course_enrollment ce ON ce.Course_code=cu.Course_code " +
                     "WHERE ce.ST_Id=? ORDER BY cu.Year, cu.Semester, cu.Course_code";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getEnrolledCourses error: " + e.getMessage());
        }
        return list;
    }
}
