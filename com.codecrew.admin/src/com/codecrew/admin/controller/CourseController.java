/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.db.DbConnection;
import com.codecrew.admin.model.CourseModel;
import com.mysql.cj.jdbc.PreparedStatementWrapper;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author USER
 */
public class CourseController {
    
    private static CourseController CCObj;
    
    public static CourseController getInstance(){
        if(CCObj==null){
            CCObj = new CourseController();
        }
        return CCObj;
    }

    public void courseTableLoad(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        dtm.setRowCount(0);
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "SELECT * FROM course_unit";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet result =  pst.executeQuery();
        
        while(result.next()){
            String code = result.getString("Course_code");
            String name = result.getString("Name");
            String type = result.getString("Type");
            String credit = result.getString("Credit");
            String lec_name = result.getString("Lec_Name");
            String year = result.getString("Year");
            String semester = result.getString("Semester");
            String dept = result.getString("Department_Offering");
            String t_hours = result.getString("Theory_Hours");
            String p_hours = result.getString("Practical_Hours");
            
            dtm.addRow(new Object[] {code,name,type,credit,lec_name,year,semester,dept,t_hours,p_hours});
            
        }
    }

    public boolean courseSave(CourseModel courseModel) throws ClassNotFoundException, SQLException {
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "INSERT INTO course_unit VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, courseModel.getCode());
        pst.setString(2, courseModel.getName());
        pst.setString(3, courseModel.getType());
        pst.setInt(4, courseModel.getCredit());
        pst.setString(5, courseModel.getLecName());
        pst.setInt(6, courseModel.getYear());
        pst.setString(7, courseModel.getSemester());
        pst.setString(8, courseModel.getDepartment());
        pst.setInt(9, courseModel.getTheoryHours());
        pst.setInt(10, courseModel.getPracticalHours());
        
        int affectedRows = pst.executeUpdate();
        
        return affectedRows>0;
    }

    
    
}
