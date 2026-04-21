/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.model.NoticeModel;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public interface NoticeControllerInterface {
    public boolean saveNotice(NoticeModel noticeModel) throws ClassNotFoundException, SQLException;
    public void noticeTableLoad(DefaultTableModel generalDtm) throws ClassNotFoundException, SQLException;
    public boolean updateNotice(NoticeModel noticeModel) throws ClassNotFoundException, SQLException;
    public boolean noticeDelete(String text) throws ClassNotFoundException, SQLException;
    public void search(DefaultTableModel dtm, String type) throws ClassNotFoundException, SQLException;
}
