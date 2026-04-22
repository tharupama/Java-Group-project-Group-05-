package com.codecrew;

import com.codecrew.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class TimetableService {


    // Load ALL Timetable

    public DefaultTableModel getAllTimetable() {

        String[] columns = {"ID", "Course Code", "Type", 
                           "Date", "Day", "From", "To", "Venue"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT t.id, t.Course_code, t.Type, t.date, " +
                "t.Day, t.time_from, t.time_to, t.venue, c.Name " +
                "FROM time_table t " +
                "INNER JOIN course_unit c ON c.Course_code = t.Course_code " +
                "ORDER BY t.date, t.time_from"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("Course_code"),
                    rs.getString("Type"),
                    rs.getString("date"),
                    rs.getString("Day"),
                    rs.getString("time_from"),
                    rs.getString("time_to"),
                    rs.getString("venue")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            System.out.println("Load Timetable Error: " + e.getMessage());
        }

        return model;
    }


    // Filter by Type (LECTURE or EXAM)
  
    public DefaultTableModel getByType(String type) {

        String[] columns = {"ID", "Course Code", "Type",
                           "Date", "Day", "From", "To", "Venue"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT t.id, t.Course_code, t.Type, t.date, " +
                "t.Day, t.time_from, t.time_to, t.venue " +
                "FROM time_table t " +
                "WHERE t.Type=? " +
                "ORDER BY t.date, t.time_from"
            );

            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("Course_code"),
                    rs.getString("Type"),
                    rs.getString("date"),
                    rs.getString("Day"),
                    rs.getString("time_from"),
                    rs.getString("time_to"),
                    rs.getString("venue")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            System.out.println("Filter Timetable Error: " + e.getMessage());
        }

        return model;
    }
}
