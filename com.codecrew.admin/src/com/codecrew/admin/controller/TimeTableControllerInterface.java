/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.controller;

import com.codecrew.admin.model.TimeTableModel;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
    public interface TimeTableControllerInterface {
    public boolean save(TimeTableModel timeTableModel) throws ClassNotFoundException, SQLException;
    public void timeTableLoad(DefaultTableModel timeDtm) throws ClassNotFoundException, SQLException;
    public void search(DefaultTableModel dtm, String text) throws ClassNotFoundException, SQLException;
    public boolean update(TimeTableModel timeTableModel, int id) throws ClassNotFoundException, SQLException;
    public boolean delete(int id) throws ClassNotFoundException, SQLException;
}
