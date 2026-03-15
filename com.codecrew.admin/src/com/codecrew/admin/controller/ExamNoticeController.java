/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.db.DbConnection;
import com.codecrew.admin.model.NoticeModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class ExamNoticeController extends NoticeController{
    private static ExamNoticeController examNoticeObj;

    public boolean saveNotice(NoticeModel noticeModel) throws ClassNotFoundException, SQLException {
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "INSERT INTO notice (type,title,download_link,content,course_id,exam_date,time_from,time_to) VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.Date sqlDate = new java.sql.Date(noticeModel.getDate().getTime());
        
        
        
        pst.setString(1, noticeModel.getType());
        pst.setString(2, noticeModel.getTitle());
        pst.setString(3, noticeModel.getDownloadLink());
        pst.setString(4, noticeModel.getContent());
        pst.setString(5, noticeModel.getCourseId());
        pst.setDate(6, sqlDate);
        pst.setObject(7, noticeModel.getTimeFrom());
        pst.setObject(8, noticeModel.getTimeTo());
        
        
        int affectedRows = pst.executeUpdate();
        return affectedRows>0;
    }
   public static ExamNoticeController getInstance(){
        if(examNoticeObj==null){
            examNoticeObj=new ExamNoticeController();
        }
        return examNoticeObj;
    }

    public void noticeTableLoad(DefaultTableModel examDtm) throws ClassNotFoundException, SQLException {
                examDtm.setRowCount(0);
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "SELECT * FROM notice WHERE type=?"; 
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, "Exam");
        
        ResultSet result =  pst.executeQuery();
        
        while(result.next()){
            String noticeId = result.getString("notice_id");
            String Created = result.getTimestamp("created_at").toString();
            String Updated = result.getTimestamp("updated_at").toString();
            String Type = result.getString("type");
            String Title = result.getString("title");
            String downloadLink = result.getString("download_link");
            String Content = result.getString("content");
            String courseId = result.getString("course_id");
            String Date = result.getString("exam_date");
            String timeFrom = result.getString("time_from");
            String timeTo = result.getString("time_to");
            
            examDtm.addRow(new Object[] {noticeId,Created,Updated,Type,Title,Content,downloadLink,courseId,Date,timeFrom,timeTo});
        
        }
    }   

    @Override
    public boolean updateNotice(NoticeModel noticeModel) throws SQLException, ClassNotFoundException {
         Connection conn = DbConnection.getInstance().getConn();
        String sql = "UPDATE notice SET type=?,title=?,download_link=?,content=?,course_id=?,exam_date=?,time_from=?,time_to=? WHERE notice_id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.Date sqlDate = new java.sql.Date(noticeModel.getDate().getTime());
        pst.setString(1, noticeModel.getType());
        pst.setString(2, noticeModel.getTitle());
        pst.setString(3, noticeModel.getDownloadLink());
        pst.setString(4, noticeModel.getContent());
        pst.setString(5, noticeModel.getCourseId());
        pst.setDate(6, sqlDate);
        pst.setObject(7, noticeModel.getTimeFrom());
        pst.setObject(8, noticeModel.getTimeTo()); 
        pst.setInt(9, noticeModel.getId()); 
        
        int affectedRows = pst.executeUpdate();
        return affectedRows>0;
    }
    
}
