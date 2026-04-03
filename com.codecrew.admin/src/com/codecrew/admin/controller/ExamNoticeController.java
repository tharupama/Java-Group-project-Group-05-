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

    @Override
    public boolean saveNotice(NoticeModel noticeModel) throws ClassNotFoundException, SQLException {
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "INSERT INTO notice (type,title,download_link,content,course_id,exam_date,time_from,time_to) VALUES(?,?,?,?,?,?,?,?)";
        int affectedRows;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            java.sql.Date sqlDate = new java.sql.Date(noticeModel.getDate().getTime());
            pst.setString(1, noticeModel.getType());
            pst.setString(2, noticeModel.getTitle());
            pst.setString(3, noticeModel.getDownloadLink());
            pst.setString(4, noticeModel.getContent());
            pst.setString(5, noticeModel.getCourseId());
            pst.setDate(6, sqlDate);
            pst.setObject(7, noticeModel.getTimeFrom());
            pst.setObject(8, noticeModel.getTimeTo());
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
    }
   public static ExamNoticeController getInstance(){
        if(examNoticeObj==null){
            examNoticeObj=new ExamNoticeController();
        }
        return examNoticeObj;
    }

    @Override
    public void noticeTableLoad(DefaultTableModel examDtm) throws ClassNotFoundException, SQLException {
                examDtm.setRowCount(0);
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "SELECT * FROM notice WHERE type=?"; 
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
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
    }   

    @Override
    public boolean updateNotice(NoticeModel noticeModel) throws SQLException, ClassNotFoundException {
         Connection conn = DbConnection.getInstance().getConn();
        String sql = "UPDATE notice SET type=?,title=?,download_link=?,content=?,course_id=?,exam_date=?,time_from=?,time_to=? WHERE notice_id = ?";
        int affectedRows;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
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
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
    }
    
    @Override
        public void search(DefaultTableModel dtm, String type) throws ClassNotFoundException, SQLException {
            
        dtm.setRowCount(0);
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "SELECT * FROM notice WHERE type=? AND (title LIKE ? OR download_link LIKE ? OR content LIKE ? OR course_id LIKE ? OR exam_date LIKE ?)"; 
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "Exam");
            pst.setString(2, "%"+type+"%");
            pst.setString(3, "%"+type+"%");
            pst.setString(4, "%"+type+"%");
            pst.setString(5, "%"+type+"%");
            pst.setString(6, "%"+type+"%");
            
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
                
                dtm.addRow(new Object[] {noticeId,Created,Updated,Type,Title,Content,downloadLink,courseId,Date,timeFrom,timeTo});        
            }
        }
    }
    
}
