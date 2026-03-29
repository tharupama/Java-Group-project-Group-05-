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
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

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

    public void timeTableLoad(DefaultTableModel timeDtm) throws ClassNotFoundException, SQLException {
        timeDtm.setRowCount(0);
        String sql = "SELECT * FROM time_table";
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            
            String id = rst.getString("id");
            String courseCode = rst.getString("Course_code");
            String type = rst.getString("Type");
            String date = rst.getDate("date").toString();
            String day = rst.getString("Day").toString();
            String timeFrom = rst.getString("time_from");
            String timeTo = rst.getString("time_to");
            
            timeDtm.addRow(new Object[]{id,courseCode,type,date,day,timeFrom,timeTo});
        }
    }

    public void search(DefaultTableModel dtm, String text) throws ClassNotFoundException, SQLException {
               dtm.setRowCount(0);
        String sql = "SELECT * FROM time_table WHERE id LIKE ? OR Course_code LIKE ? OR Type LIKE ? OR date LIKE ? OR Day LIKE ? OR time_from LIKE ? OR time_to LIKE ?";
        Connection conn = DbConnection.getInstance().getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, "%"+text+"%");
        pst.setString(2, "%"+text+"%");
        pst.setString(3, "%"+text+"%");
        pst.setString(4, "%"+text+"%");
        pst.setString(5, "%"+text+"%");
        pst.setString(6, "%"+text+"%");
        pst.setString(7, "%"+text+"%");
        
        ResultSet rst = pst.executeQuery();
        
        while(rst.next()){
            
            String id = rst.getString("id");
            String courseCode = rst.getString("Course_code");
            String type = rst.getString("Type");
            String date = rst.getDate("date").toString();
            String day = rst.getString("Day").toString();
            String timeFrom = rst.getString("time_from");
            String timeTo = rst.getString("time_to");
            
            dtm.addRow(new Object[]{id,courseCode,type,date,day,timeFrom,timeTo});
        }
    }

    
}
