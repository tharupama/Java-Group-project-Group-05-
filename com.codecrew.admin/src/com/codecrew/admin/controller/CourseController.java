/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.db.DbConnection;

import com.codecrew.admin.exception.CourseCodeNotFoundException;
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
public class CourseController extends CourseControllerAbstract{
    
    private static CourseController CCObj;
    
    public static CourseController getInstance(){
        if(CCObj==null){
            CCObj = new CourseController();
        }
        return CCObj;
    }

    @Override
    public void courseTableLoad(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        dtm.setRowCount(0);
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "SELECT * FROM course_unit";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
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
    }

    @Override
    public boolean courseSave(CourseModel courseModel) throws ClassNotFoundException, SQLException {
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "INSERT INTO course_unit VALUES(?,?,?,?,?,?,?,?,?,?)";
        int affectedRows;
        
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
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
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
    }

    public boolean delete(String id) throws ClassNotFoundException, SQLException {
        Connection conn = DbConnection.getInstance().getConn();
        
        String sql2 = "SELECT * FROM course_unit WHERE Course_code=?";
        try(PreparedStatement pst2 = conn.prepareStatement(sql2)){
            pst2.setString(1,id);
            ResultSet rst = pst2.executeQuery();
            if(!rst.next()){
                throw new CourseCodeNotFoundException(id);   
            }
        }
        
        String sql = "DELETE FROM course_unit WHERE Course_code = ?";
        int affectedRows;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
    }

    @Override
    public void search(String text, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
                String sql = "SELECT * FROM course_unit WHERE Course_code LIKE ? OR Name LIKE ? OR Type LIKE ? OR Credit LIKE ? OR Lec_Name LIKE ? OR Year LIKE ? OR Semester LIKE ? OR Department_Offering LIKE ? OR Theory_Hours LIKE ? OR Practical_Hours LIKE ?";
        
        Connection conn = DbConnection.getInstance().getConn();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1,"%"+text+"%");
            pst.setString(2,"%"+text+"%");
            pst.setString(3,"%"+text+"%");
            pst.setString(4,"%"+text+"%");
            pst.setString(5,"%"+text+"%");
            pst.setString(6,"%"+text+"%");
            pst.setString(7,"%"+text+"%");
            pst.setString(8,"%"+text+"%");
            pst.setString(9,"%"+text+"%");
            pst.setString(10,"%"+text+"%");
            ResultSet result = pst.executeQuery();
            dtm.setRowCount(0);
            if(result!=null){
                System.out.println("thinawa");
            }
            while(result.next()){
                
                dtm.addRow(new Object[]{result.getString("Course_code"), result.getString("Name"),result.getString("Type"),result.getString("Credit"),result.getString("Lec_Name"),result.getString("Year"),result.getString("Semester"),result.getString("Department_Offering"),result.getString("Theory_Hours"),result.getString("Practical_Hours")});
            }
        }
        
    }

    @Override
    public boolean updateCourse(CourseModel courseModel) throws SQLException, ClassNotFoundException {
        Connection conn = DbConnection.getInstance().getConn();
        
        String sql2 = "SELECT * FROM course_unit WHERE Course_code=?";
        try(PreparedStatement pst2 = conn.prepareStatement(sql2)){
            pst2.setString(1,courseModel.getCode());
            ResultSet rst = pst2.executeQuery();
            if(!rst.next()){
                throw new CourseCodeNotFoundException(courseModel.getCode());   
            }
        }
        
        String sql = "Update course_unit SET Name=?, Type=?, Credit=?, Lec_Name=?, Year=?, Semester=?, Department_Offering=?, Theory_Hours=?, Practical_Hours=? WHERE Course_code=?";
        int affectedRows;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, courseModel.getName());
            pst.setString(2, courseModel.getType());
            pst.setInt(3, courseModel.getCredit());
            pst.setString(4, courseModel.getLecName());
            pst.setInt(5, courseModel.getYear());
            pst.setString(6, courseModel.getSemester());
            pst.setString(7, courseModel.getDepartment());
            pst.setInt(8, courseModel.getTheoryHours());
            pst.setInt(9, courseModel.getPracticalHours());
            pst.setString(10, courseModel.getCode());
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
    }

    
    
}
