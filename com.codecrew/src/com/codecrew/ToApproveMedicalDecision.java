/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author nipun
 */
public class ToApproveMedicalDecision implements ToMedicalDecision{
    
@Override
    public void apply(int recordId, String approvedBy) throws SQLException {
        String getData = "SELECT ST_Id, Session_Id FROM tomedical_record WHERE Record_Id=?";
        String updateMedical = "UPDATE tomedical_record SET Status='Approved', Approved_By=?, Approved_Date=CURDATE() WHERE Record_Id=?";
        String updateAttendance = "UPDATE toattendance SET Status='Medical' WHERE ST_Id=? AND Session_Id=?";

        Connection con = null;
        try {
            con = ToConnect.getConnection();
            con.setAutoCommit(false);

            String stId = null;
            int sessionId = 0;

            try (PreparedStatement pst1 = con.prepareStatement(getData)) {
                pst1.setInt(1, recordId);
                try (ResultSet rs = pst1.executeQuery()) {
                    if (rs.next()) {
                        stId = rs.getString("ST_Id");
                        sessionId = rs.getInt("Session_Id");
                    }
                }
            }

            try (PreparedStatement pst2 = con.prepareStatement(updateMedical)) {
                pst2.setString(1, approvedBy);
                pst2.setInt(2, recordId);
                pst2.executeUpdate();
            }

            try (PreparedStatement pst3 = con.prepareStatement(updateAttendance)) {
                pst3.setString(1, stId);
                pst3.setInt(2, sessionId);
                pst3.executeUpdate();
            }

            con.commit();
        } catch (SQLException ex) {
            if (con != null) con.rollback();
            throw ex;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
}