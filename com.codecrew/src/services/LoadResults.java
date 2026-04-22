package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import com.codecrew.util.DBConnection;

public class LoadResults {

    public void loadGradesGPA(javax.swing.JTable GPAResult, String studentId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT course_code, Semester, CA_marks, Final_Marks, " +
                         "Final_Grade, Result_Status, SGPA, CGPA " +
                         "FROM student_result WHERE student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, studentId);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) GPAResult.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("course_code"),
                    rs.getString("Semester"),
                    rs.getDouble("CA_marks"),
                    rs.getString("Final_Marks"),   // NULL safe as String
                    rs.getString("Final_Grade"),
                    rs.getString("Result_Status"),
                    rs.getString("SGPA"),          // NULL safe as String
                    rs.getString("CGPA")           // NULL safe as String
                });
            }
            rs.close(); pst.close(); con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading results: " + e.getMessage());
        }
    }
}