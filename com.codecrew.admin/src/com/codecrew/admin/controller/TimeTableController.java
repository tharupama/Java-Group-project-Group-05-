/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.db.DbConnection;
import com.codecrew.admin.model.TimeTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author USER
 */
public class TimeTableController {
    
    private static TimeTableController tTObj;
    
    public static TimeTableController getInstance(){
        if (tTObj==null){
            tTObj = new TimeTableController();
        }
        
        return tTObj; 
    }

    public boolean save(TimeTableModel timeTableModel) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO time_table (Course_code,Type,date,Day,time_from,time_to) VALUES(?,?,?,?,?,?)";
        Connection conn = DbConnection.getInstance().getConn();
        
        java.sql.Date sqlDate = new java.sql.Date(timeTableModel.getDate().getTime());
        
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, timeTableModel.getCourseCode());
        pst.setString(2, timeTableModel.getType());
        pst.setDate(3, sqlDate);
        pst.setString(4, timeTableModel.getDay().toString());
        pst.setObject(5, timeTableModel.getTimeFrom());
        pst.setObject(6, timeTableModel.getTimeTo());
        
        int affectedRows = pst.executeUpdate();
        return affectedRows>0;
    }
}
