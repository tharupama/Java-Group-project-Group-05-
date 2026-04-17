/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.model.CourseModel;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public abstract class CourseControllerAbstract {
    private static CourseController CCObj;
    
    public abstract void courseTableLoad(DefaultTableModel dtm) throws ClassNotFoundException, SQLException;
    public abstract boolean courseSave(CourseModel courseModel) throws ClassNotFoundException, SQLException;
    public abstract boolean delete(String id) throws ClassNotFoundException, SQLException;
    public abstract void search(String text, DefaultTableModel dtm) throws ClassNotFoundException, SQLException;
    public abstract boolean updateCourse(CourseModel courseModel) throws SQLException, ClassNotFoundException;
    
    public static CourseController getInstance(){
        if(CCObj==null){
            CCObj = new CourseController();
        }
        return CCObj;
    }
}
