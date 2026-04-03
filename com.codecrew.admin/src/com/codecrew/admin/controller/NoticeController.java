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
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import javax.swing.JTextField;

/**
 *
 * @author USER
 */
public class NoticeController implements NoticeControllerInterface{
    private static NoticeController noticeObj;

    @Override
    public boolean saveNotice(NoticeModel noticeModel) throws ClassNotFoundException, SQLException {
        
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "INSERT INTO notice (type,title,download_link,content) VALUES(?,?,?,?)";
        int affectedRows;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, noticeModel.getType());
            pst.setString(2, noticeModel.getTitle());
            pst.setString(3, noticeModel.getDownloadLink());
            pst.setString(4, noticeModel.getContent());
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
    }

    public static NoticeController getInstance(){
        if(noticeObj==null){
            noticeObj=new NoticeController();
        }
        return noticeObj;
    }

    @Override
    public void noticeTableLoad(DefaultTableModel generalDtm) throws ClassNotFoundException, SQLException {
        generalDtm.setRowCount(0);
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "SELECT * FROM notice WHERE type=?"; 
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "General");
            
            ResultSet result =  pst.executeQuery();
            
            while(result.next()){
                String noticeId = result.getString("notice_id");
                String Created = result.getTimestamp("created_at").toString();
                String Updated = result.getTimestamp("updated_at").toString();
                String Type = result.getString("type");
                String Title = result.getString("title");
                String downloadLink = result.getString("download_link");
                String Content = result.getString("content");
                
                generalDtm.addRow(new Object[] {noticeId,Created,Updated,Type,Title,Content,downloadLink});
                
            }
        }
    }

    @Override
    public boolean updateNotice(NoticeModel noticeModel) throws ClassNotFoundException, SQLException {
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "Update notice SET type=?,title=?,download_link=?,content=? WHERE notice_id = ?";
        int affectedRows;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, noticeModel.getType());
            pst.setString(2, noticeModel.getTitle());
            pst.setString(3, noticeModel.getDownloadLink());
            pst.setString(4, noticeModel.getContent());
            pst.setInt(5, noticeModel.getId());
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
        
    }

    @Override
    public boolean noticeDelete(String text) throws ClassNotFoundException, SQLException {
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "DELETE FROM notice WHERE notice_id = ?";
        int affectedRows;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, text);
            affectedRows = pst.executeUpdate();
        }
        return affectedRows>0;
    }

    @Override
    public void search(DefaultTableModel dtm, String type) throws ClassNotFoundException, SQLException {
        dtm.setRowCount(0);
        Connection conn = DbConnection.getInstance().getConn();
        String sql = "SELECT * FROM notice WHERE type=? AND (title LIKE ? OR download_link LIKE ? OR content LIKE ?)"; 
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "General");
            pst.setString(2, "%"+type+"%");
            pst.setString(3, "%"+type+"%");
            pst.setString(4, "%"+type+"%");
            
            ResultSet result =  pst.executeQuery();
            
            while(result.next()){
                String noticeId = result.getString("notice_id");
                String Created = result.getTimestamp("created_at").toString();
                String Updated = result.getTimestamp("updated_at").toString();
                String Type = result.getString("type");
                String Title = result.getString("title");
                String downloadLink = result.getString("download_link");
                String Content = result.getString("content");
                
                dtm.addRow(new Object[] {noticeId,Created,Updated,Type,Title,Content,downloadLink});        
            }
        }
    }
    
}
