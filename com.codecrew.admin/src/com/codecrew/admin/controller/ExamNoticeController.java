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
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class ExamNoticeController extends NoticeController{

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
   
    
}
