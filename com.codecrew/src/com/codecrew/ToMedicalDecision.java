/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;
import java.sql.SQLException;
/**
 *
 * @author nipun
 */
public interface  ToMedicalDecision {
    void apply(int recordId, String approvedBy) throws SQLException;
}
