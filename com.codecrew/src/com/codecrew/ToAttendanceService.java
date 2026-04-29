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
public interface ToAttendanceService {
    void addAttendance(
    String studentId,
    String courseCode,
    int weekNo,
    int sessionNo,
    String sessionDate,
    String sessionType,
    String status
    ) 
            throws SQLException;
    
}
