package com.codecrew.dao;

import com.codecrew.model.Attendance;
import com.codecrew.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AttendanceDAO - Student attendance operations
 */
public class AttendanceDAO {

    /** Get all attendance for a student in a course */
    public List<Attendance> getAttendance(String stId, String courseCode) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE ST_Id=? AND Course_code=? ORDER BY Week_Number, Session_Type";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, stId);
            ps.setString(2, courseCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getAttendance error: " + e.getMessage());
        }
        return list;
    }

    /** Get attendance for all students in a course on a specific date */
    public List<Attendance> getAttendanceByDate(String courseCode, String date) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT a.*, s.Fname, s.Lname FROM attendance a " +
                     "JOIN student s ON s.Registration_No=a.ST_Id " +
                     "WHERE a.Course_code=? AND a.Session_Date=? ORDER BY a.ST_Id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, courseCode);
            ps.setString(2, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getAttendanceByDate error: " + e.getMessage());
        }
        return list;
    }

    /** Get attendance summary (from VIEW) for a student */
    public ResultSet getAttendanceSummary(String stId) {
        String sql = "SELECT * FROM attendance_summary WHERE ST_Id=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, stId);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("getAttendanceSummary error: " + e.getMessage());
        }
        return null;
    }

    /** Get full summary table for a course */
    public ResultSet getCourseAttendanceSummary(String courseCode) {
        String sql = "SELECT a.ST_Id, s.Fname, s.Lname, a.Course_code, a.Total_Sessions, " +
                     "a.Present, a.Absent, a.Medical, a.Percentage, a.Eligibility " +
                     "FROM attendance_summary a JOIN student s ON s.Registration_No=a.ST_Id " +
                     "WHERE a.Course_code=? ORDER BY a.ST_Id";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, courseCode);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("getCourseAttendanceSummary error: " + e.getMessage());
        }
        return null;
    }

    /** Update attendance status */
    public boolean updateStatus(String attendanceId, String status) {
        String sql = "UPDATE attendance SET Status=? WHERE Attendance_Id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, attendanceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updateStatus error: " + e.getMessage());
        }
        return false;
    }

    /** Mark attendance for a student in a course for a given week */
    public boolean markAttendance(Attendance att) {
        String sql = "INSERT INTO attendance(Attendance_Id,ST_Id,Course_code,Session_Date,Week_Number,Session_Type,Status) " +
                     "VALUES(?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE Status=VALUES(Status)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, att.getAttendanceId());
            ps.setString(2, att.getStId());
            ps.setString(3, att.getCourseCode());
            ps.setString(4, att.getSessionDate());
            ps.setInt(5, att.getWeekNumber());
            ps.setString(6, att.getSessionType());
            ps.setString(7, att.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("markAttendance error: " + e.getMessage());
        }
        return false;
    }

    /** Bulk update: mark all students in a course/week as Present */
    public boolean markAllPresent(String courseCode, String date) {
        String sql = "UPDATE attendance SET Status='Present' WHERE Course_code=? AND Session_Date=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, courseCode);
            ps.setString(2, date);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("markAllPresent error: " + e.getMessage());
        }
        return false;
    }

    /** Generate next attendance ID */
    public String generateNextId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(Attendance_Id,4) AS UNSIGNED)) FROM attendance";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int max = rs.getInt(1);
                return String.format("ATT%04d", max + 1);
            }
        } catch (SQLException e) {
            System.err.println("generateNextId error: " + e.getMessage());
        }
        return "ATT0001";
    }

    private Attendance mapRow(ResultSet rs) throws SQLException {
        return new Attendance(
            rs.getString("Attendance_Id"),
            rs.getString("ST_Id"),
            rs.getString("Course_code"),
            rs.getString("Session_Date"),
            rs.getInt("Week_Number"),
            rs.getString("Session_Type"),
            rs.getString("Status")
        );
    }

    /**
     * Get attendance summary for a specific student in a specific course
     */
    public ResultSet getStudentCourseSummary(String stId, String courseCode) {
        String sql = "SELECT * FROM attendance_summary WHERE ST_Id=? AND Course_code=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, stId);
            ps.setString(2, courseCode);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("getStudentCourseSummary error: " + e.getMessage());
        }
        return null;
    }
}
