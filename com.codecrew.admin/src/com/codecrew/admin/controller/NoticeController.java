/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.db.DbConnection;
import com.codecrew.admin.model.NoticeModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author USER
 */
public class NoticeController {

    public boolean saveNotice(NoticeModel noticeModel) throws ClassNotFoundException, SQLException {
        
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "INSERT INTO notice (type,title,download_link,content) VALUES(?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, noticeModel.getType());
        pst.setString(2, noticeModel.getTitle());
        pst.setString(3, noticeModel.getDownloadLink());
        pst.setString(4, noticeModel.getContent());
        
        int affectedRows = pst.executeUpdate();
        return affectedRows>0;
    }

    
    
}
