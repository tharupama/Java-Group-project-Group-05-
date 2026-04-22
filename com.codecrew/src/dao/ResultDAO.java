package com.codecrew.dao;

import com.codecrew.model.StudentResult;
import com.codecrew.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ResultDAO - Student results, marks, grades
 */
public class ResultDAO {

    public List<StudentResult> getResultsByStudent(String studentId) {
        List<StudentResult> list = new ArrayList<>();
        String sql = "SELECT sr.*, cu.Name AS course_name, cu.Credit FROM student_result sr " +
                     "JOIN course_unit cu ON cu.Course_code=sr.course_code " +
                     "WHERE sr.student_id=? ORDER BY sr.course_code";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("getResultsByStudent error: " + e.getMessage());
        }
        return list;
    }

    public ResultSet getResultsTableData(String studentId) {
        String sql = "SELECT sr.course_code, cu.Name, cu.Credit, sr.CA_marks, " +
                     "sr.Final_Marks, sr.Final_Grade, sr.Result_Status, sr.SGPA, sr.CGPA " +
                     "FROM student_result sr " +
                     "JOIN course_unit cu ON cu.Course_code=sr.course_code " +
                     "WHERE sr.student_id=? ORDER BY sr.course_code";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, studentId);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("getResultsTableData error: " + e.getMessage());
        }
        return null;
    }

    public ResultSet getAllResultsForCourse(String courseCode) {
        String sql = "SELECT sr.student_id, s.Fname, s.Lname, sr.CA_marks, " +
                     "sr.Final_Marks, sr.Final_Grade, sr.Result_Status " +
                     "FROM student_result sr " +
                     "JOIN student s ON s.Registration_No=sr.student_id " +
                     "WHERE sr.course_code=? ORDER BY sr.student_id";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, courseCode);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("getAllResultsForCourse error: " + e.getMessage());
        }
        return null;
    }

    public double getSGPA(String studentId) {
        String sql = "SELECT SGPA FROM student_result WHERE student_id=? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("SGPA");
            }
        } catch (SQLException e) {
            System.err.println("getSGPA error: " + e.getMessage());
        }
        return 0.0;
    }

    public double getCGPA(String studentId) {
        String sql = "SELECT CGPA FROM student_result WHERE student_id=? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("CGPA");
            }
        } catch (SQLException e) {
            System.err.println("getCGPA error: " + e.getMessage());
        }
        return 0.0;
    }

    public boolean updateMarks(String studentId, String courseCode,
                               double quiz1, double quiz2, double quiz3,
                               double assessment, double midTheory, double midPractical,
                               double finalTheory, double finalPractical) {
        String sql = "UPDATE mark SET Quiz_01=?,Quiz_02=?,Quiz_03=?,Assessment_01=?," +
                     "Mid_Theory=?,Mid_Practical=?,Final_Theory=?,Final_Practical=? " +
                     "WHERE student_id=? AND course_code=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, quiz1); ps.setDouble(2, quiz2); ps.setDouble(3, quiz3);
            ps.setDouble(4, assessment); ps.setDouble(5, midTheory);
            ps.setDouble(6, midPractical); ps.setDouble(7, finalTheory);
            ps.setDouble(8, finalPractical);
            ps.setString(9, studentId); ps.setString(10, courseCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updateMarks error: " + e.getMessage());
        }
        return false;
    }

    public ResultSet getMarksForStudent(String studentId, String courseCode) {
        String sql = "SELECT * FROM mark WHERE student_id=? AND course_code=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setString(2, courseCode);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("getMarksForStudent error: " + e.getMessage());
        }
        return null;
    }

    /** Call stored procedure to recalculate CA marks */
    public void recalculateCAMarks() {
        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall("{CALL calculate_CA_marks()}")) {
            cs.execute();
        } catch (SQLException e) {
            System.err.println("recalculateCAMarks error: " + e.getMessage());
        }
    }

    /** Call stored procedure to recalculate Final Marks */
    public void recalculateFinalMarks() {
        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall("{CALL calculateFinalMark()}")) {
            cs.execute();
        } catch (SQLException e) {
            System.err.println("recalculateFinalMarks error: " + e.getMessage());
        }
    }

    /** Call stored procedure to recalculate SGPA */
    public void recalculateSGPA() {
        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall("{CALL calculateSGPA()}")) {
            cs.execute();
        } catch (SQLException e) {
            System.err.println("recalculateSGPA error: " + e.getMessage());
        }
    }

    /** Call stored procedure to recalculate CGPA */
    public void recalculateCGPA() {
        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall("{CALL calculateCGPA()}")) {
            cs.execute();
        } catch (SQLException e) {
            System.err.println("recalculateCGPA error: " + e.getMessage());
        }
    }

    private StudentResult mapRow(ResultSet rs) throws SQLException {
        return new StudentResult(
            rs.getInt("Result_Id"),
            rs.getString("student_id"),
            rs.getString("course_code"),
            rs.getString("Semester"),
            rs.getDouble("CA_marks"),
            rs.getDouble("Final_Marks"),
            rs.getString("Final_Grade"),
            rs.getString("Result_Status"),
            rs.getDouble("SGPA"),
            rs.getDouble("CGPA")
        );
    }
}
