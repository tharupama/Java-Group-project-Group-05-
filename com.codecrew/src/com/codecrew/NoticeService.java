/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;

import com.codecrew.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author HP
 */
//add notice
public class NoticeService {
    
    public boolean addNotice(String noticeId,String title,
            String description,String postedBy){
        String Query="INSERT INTO LecNotice VALUES (?, ?, ?, ?, CURDATE())";
        try{
            Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(Query);
            
            ps.setString(1,noticeId);
            ps.setString(2, title);
            ps.setString(3,description);
            ps.setString(4,postedBy);
            
            ps.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
 public DefaultTableModel getAllNotices(){
      String[]columns={"Notice Id","Title","Description","Posted By","Date"};
      DefaultTableModel model=new DefaultTableModel(columns,0);
      
      try{
      Connection con = DBConnection.getConnection();
      PreparedStatement ps=con.prepareStatement(
               "SELECT * FROM LecNotice ORDER BY Posted_Date DESC");
      ResultSet rs=ps.executeQuery();
      
      while(rs.next()){
          Object[]row={
              rs.getString("Notice_id"),
              rs.getString("Title"),
              rs.getString("Description"),
              rs.getString("Posted_By"),
              rs.getString("Posted_Date")
          };
          model.addRow(row);
      }
      }catch (Exception e){
          System.out.println(e.getMessage());
      }
      
  return model;
  }
  
  public boolean deleteNotice(String noticeId){
      try{
          Connection con =DBConnection.getConnection();
          PreparedStatement ps=con.prepareStatement(
          "DELETE FROM LecNotice WHERE Notice_id=?");
          
          ps.setString(1,noticeId);
          ps.executeUpdate();
          return true;
      }catch(Exception e){
          System.out.println(e.getMessage());
          return false;
      }
  }
}
