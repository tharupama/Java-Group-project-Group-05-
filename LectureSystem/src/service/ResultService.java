/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ResultService {
    //get best two quizes
    private double[]getBestTwoQuizzes(double q1,double q2,double q3){
      double all[]={q1,q2,q3}; 
      java.util.Arrays.sort(all);
/*       double lowest=Math.min(q1,Math.min(q2,q3));
      double bestTwo[]=new double[2];
      int index=0;
      
      for(double q : all){
          if(q!=lowest||index==1){
              bestTwo[index]=q;
              index++;
          }
          if(index==2){
              break;
          }
      }
      return bestTwo; */
    return new double[]{all[1], all[2]};
    }
 //calculate CA marks   
private double calculateCA(double q1,double q2,double q3,
                double assessment,double midTheory,double midPractical,String type){


        double bestTwo[]=getBestTwoQuizzes(q1,q2,q3);
        double quizPart=((bestTwo[0]*0.05)+(bestTwo[1]*0.05));
        double assessmentPart=assessment*0.05;

        double mid=0;
        if(type.equals("Theory")){
            mid=midTheory;
        }else if(type.equals("Practical")){
            mid=midPractical;
        }else{
            mid=(midTheory+midPractical)/2;
        }
        double midPart=mid*0.25;
        return quizPart+assessmentPart+midPart;
}

//Calculate final marks 60%
private double calculateFinalExam(double finalTheory,double finalPractical,
                                   String type){
        if(type.equals("Theory")){
            return (finalTheory/100)*60;
        }else if(type.equals("Practical")){
            return (finalPractical/100)*60;
        }else {
            return ((finalTheory+finalPractical)/200)*60;
        }    
    
}
//calculate Grade
private String calculateGrade(double caMarks,double finalExam60,double finalMarks){

    if(caMarks<16 &&finalExam60<25){
        return "E(CA&ESA)";
    }
    if(caMarks<16){
        return "E(CA)";
    }
     if(finalExam60<25){
     return "E(ESA)";
    }
     if(finalMarks>=85)return "A+";
     else if(finalMarks>=75)return "A";
     else if(finalMarks>=70)return "A-";
     else if(finalMarks>=65)return "B+";
     else if(finalMarks>=60)return "B";
     else if(finalMarks>=55)return "B-";
     else if(finalMarks>=50)return "C+";
     else if(finalMarks>=45)return "C";
     else if(finalMarks>=40)return "C-";
     else if(finalMarks>=35)return "D";
     else return "E";
}
//calculate status
private String calculateStatus(String grade){
    if(grade.equals("E")||grade.equals("D")||grade.equals("C-")||grade.equals("D")||
            grade.equals("E(CA&ESA)")||grade.equals("E(CA)")||grade.equals("E(ESA)")){
            return "REPEAT";
    }else{
        return "PASS"; 
    }
}

public void calculateAndUpdate(){
    String query="SELECT student_id,course_code,"+
            "Quiz_01,Quiz_02,Quiz_03,Assessment_01,"+
            "Mid_Theory,Mid_Practical,"+"Final_Theory,Final_Practical,Type "+
            " FROM mark";
    String updateQuery="UPDATE student_result SET "+
                "CA_marks=?,Final_Marks=?,Final_Grade=?,Result_Status=? "+
                " WHERE student_id=? AND course_code=?";
    try{
        Connection con=DBConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(query);
           ResultSet rs=ps.executeQuery();
          //get raw marks
           while(rs.next()){
               String studentId=rs.getString("student_id");
               String courseCode=rs.getString("course_code");
               String type=rs.getString("Type");
               
               double q1=rs.getDouble("Quiz_01");
               double q2 = rs.getDouble("Quiz_02");
               double q3 = rs.getDouble("Quiz_03");
               double assessment = rs.getDouble("Assessment_01");
               
               double midTheory = rs.getDouble("Mid_Theory");
               double midPractical = rs.getDouble("Mid_Practical");
               double finalTheory = rs.getDouble("Final_Theory");
               double finalPractical = rs.getDouble("Final_Practical");
               
          double caMarks=calculateCA(q1,q2,q3,assessment,midTheory,midPractical,type);
          double finalExam60=calculateFinalExam(finalTheory,finalPractical,type);
          double finalMarks=caMarks+finalExam60;
          
          String grade=calculateGrade(caMarks,finalExam60,finalMarks);
          String status=calculateStatus(grade);
          
          PreparedStatement update=con.prepareStatement(updateQuery);
            update.setDouble(1,caMarks);
            update.setDouble(2,finalMarks);
            
            update.setString(3,grade);
            update.setString(4,status);
            
            update.setString(5,studentId);
            update.setString(6,courseCode);
             update.executeUpdate();
               System.out.println("Updated : "+studentId+
                       " | "+ courseCode+
                       " | CA : "+ caMarks +
                       " | Final : "+finalMarks+
                       " | Grade : "+grade);  
             
           }
           System.out.println("All marks calculated successfully");
        
    }catch(Exception e){
        System.out.println(e.getMessage());
        
    }
}
//load All result for jtable
public DefaultTableModel getAllResults(){
    String columns[]={"Student ID", "Course", "Semester",
                   "CA Marks", "Final Marks", "Grade", "Status"};
   DefaultTableModel model=new DefaultTableModel(columns,0);
   
   String query="SELECT student_id, course_code, Semester, " +
                "CA_marks, Final_Marks, Final_Grade, Result_Status " +
                " FROM student_result ORDER BY student_id";
   try{
       Connection con=DBConnection.getConnection();
       PreparedStatement ps=con.prepareStatement(query);
       ResultSet rs=ps.executeQuery();
       
       while(rs.next()){
           Object[]row={
            rs.getString("student_id"),
            rs.getString("course_code"),
            rs.getString("Semester"),
            rs.getDouble("CA_marks"),
            rs.getDouble("Final_Marks"),
            rs.getString("Final_Grade"),
            rs.getString("Result_Status")
           };
           model.addRow(row);
       }
   }catch(Exception e){
       System.out.println(e.getMessage());   
   }
   return model;
}
//Search by iD

public DefaultTableModel searchResults(String studentId){
    String []columns= {"Student ID", "Course", "Semester",
                           "CA Marks", "Final Marks", "Grade", "Status"};
    DefaultTableModel model=new DefaultTableModel(columns,0);
    
    String query= "SELECT student_id, course_code, Semester, " +
                "CA_marks, Final_Marks, Final_Grade, Result_Status " +
                " FROM student_result WHERE student_id=?";
    try{
        Connection con=DBConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(query);
        ps.setString(1,studentId);
        ResultSet rs=ps.executeQuery();
        
        while(rs.next()){
            Object[]row={
            rs.getString("student_id"),
            rs.getString("course_code"),
            rs.getString("Semester"),
            rs.getDouble("CA_marks"),
            rs.getDouble("Final_Marks"),
            rs.getString("Final_Grade"),
            rs.getString("Result_Status")
            };
            model.addRow(row);
        }
    }catch(Exception e){
        System.out.println(e.getMessage());
    }
    return model;
}
}





