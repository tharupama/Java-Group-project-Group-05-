/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;

import  java.sql.Connection;
import com.codecrew.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author HP
 */
public class MarksService {
    
    public boolean saveMarks(String studentId,String courseCode,
            double quiz1,double quiz2,double quiz3,
            double assessment,double midTheory,double midPractical,
            double finalTheory,double finalPractical,String type){
        String UpdateQue="UPDATE mark SET "+
                "Quiz_01=?,Quiz_02=?,Quiz_03=?,"+
                "Assessment_01=?,"+
                "Mid_Theory=?, Mid_Practical=?,"+
                "Final_Theory=?, Final_Practical=?,"+
                "Type=? "+
                "WHERE student_id=? AND course_code=?";
        
        try{
            
            Connection con=DBConnection.getConnection();
            PreparedStatement pr=con.prepareStatement(UpdateQue);
                
        pr.setDouble(1,quiz1);
        pr.setDouble(2,quiz2);   
        pr.setDouble(3,quiz3);
        pr.setDouble(4,assessment);
        pr.setDouble(5,midTheory);
        pr.setDouble(6,midPractical);
        pr.setDouble(7,finalTheory);
        pr.setDouble(8,finalPractical);
        pr.setString(9,type);
        pr.setString(10,studentId);
        pr.setString(11,courseCode);
        
        int row =pr.executeUpdate();
          if(row>0){
              return true;
          }
        }
    catch(Exception e){
            System.out.println(e.getMessage());
    }
    return false;
    }
}
