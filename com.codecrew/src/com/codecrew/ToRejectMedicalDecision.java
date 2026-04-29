/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author nipun
 */
public class ToRejectMedicalDecision implements ToMedicalDecision{
 @Override
    public void apply(int recordId, String approvedBy) throws SQLException {
        String sql = "UPDATE tomedical_record SET Status='Rejected', Approved_By=?, Approved_Date=CURDATE() WHERE Record_Id=?";

        try (Connection con = ToConnect.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, approvedBy);
            pst.setInt(2, recordId);
            pst.executeUpdate();
        }
    }
}