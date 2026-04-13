
package service;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class GPAService {

    // ─────────────────────────────────────────
    // Grade → Grade Point
    // ─────────────────────────────────────────
    private double getGradePoint(String grade) {
        switch (grade) {
            case "A+": return 4.0;
            case "A":  return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B":  return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C":  return 2.0;
            case "C-": return 1.7;
            case "D":  return 1.3;
            default:   return 0.0;
        }
    }

    // ─────────────────────────────────────────
    // Calculate + Update SGPA and CGPA
    // ─────────────────────────────────────────
    public void calculateAndUpdateGPA() {
        try {
            Connection con = DBConnection.getConnection();

            // Get distinct students and semesters
            PreparedStatement ps = con.prepareStatement(
                "SELECT DISTINCT student_id, Semester " +
                "FROM student_result"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String semester = rs.getString("Semester");

                // Get all results for this student + semester
                PreparedStatement ps2 = con.prepareStatement(
                    "SELECT s.Final_Grade, c.Credit " +
                    "FROM student_result s " +
                    "INNER JOIN course_unit c ON c.Course_code = s.course_code " +
                    "WHERE s.student_id=? AND s.Semester=?"
                );

                ps2.setString(1, studentId);
                ps2.setString(2, semester);

                ResultSet rs2 = ps2.executeQuery();

                double totalPoints = 0;
                double totalCredits = 0;

                while (rs2.next()) {
                    String grade = rs2.getString("Final_Grade");
                    double credit = rs2.getDouble("Credit");

                    double gradePoint = getGradePoint(grade);

                    totalPoints += gradePoint * credit;
                    totalCredits += credit;
                }

                // Calculate SGPA
                double sgpa = 0;
                if (totalCredits > 0) {
                    sgpa = totalPoints / totalCredits;
                    sgpa = Math.round(sgpa * 100.0) / 100.0; // round 2 decimals
                }

                // Since only 1 semester → CGPA = SGPA
                double cgpa = sgpa;

                // Update student_result
                PreparedStatement update = con.prepareStatement(
                    "UPDATE student_result SET SGPA=?, CGPA=? " +
                    "WHERE student_id=? AND Semester=?"
                );

                update.setDouble(1, sgpa);
                update.setDouble(2, cgpa);
                update.setString(3, studentId);
                update.setString(4, semester);
                update.executeUpdate();

                System.out.println("GPA Updated: " + studentId +
                                 " | SGPA=" + sgpa +
                                 " | CGPA=" + cgpa);
            }

            System.out.println("All GPA calculated successfully!");

        } catch (Exception e) {
            System.out.println("GPA Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // Load All GPA Results for JTable
    // ─────────────────────────────────────────
    public DefaultTableModel getAllGPA() {

        String[] columns = {"Student ID", "Semester", "SGPA", "CGPA"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT DISTINCT student_id, Semester, SGPA, CGPA " +
                "FROM student_result " +
                "ORDER BY student_id"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("student_id"),
                    rs.getString("Semester"),
                    rs.getDouble("SGPA"),
                    rs.getDouble("CGPA")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            System.out.println("Load GPA Error: " + e.getMessage());
        }

        return model;
    }

    // ─────────────────────────────────────────
    // Search GPA by Student ID
    // ─────────────────────────────────────────
    public DefaultTableModel searchGPA(String studentId) {

        String[] columns = {"Student ID", "Semester", "SGPA", "CGPA"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT DISTINCT student_id, Semester, SGPA, CGPA " +
                "FROM student_result " +
                "WHERE student_id=?"
            );

            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getString("student_id"),
                    rs.getString("Semester"),
                    rs.getDouble("SGPA"),
                    rs.getDouble("CGPA")
                };
                model.addRow(row);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            }
 return model;
}

}


