package com.codecrew.admin.controller;

import com.codecrew.admin.model.AccountModel;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public interface AccountControllerInterface {

    public boolean saveAccount(AccountModel account) throws ClassNotFoundException, SQLException;
    public boolean updateAccount(AccountModel account) throws SQLException, ClassNotFoundException ;
    public boolean deleteAccount(String id)throws ClassNotFoundException, SQLException;
    public void search(String text, DefaultTableModel dtm)throws ClassNotFoundException, SQLException;
    public void tableLoad(DefaultTableModel dtm)throws ClassNotFoundException, SQLException;
    public void tableLoadRole(DefaultTableModel dtm, String uRole) throws ClassNotFoundException, SQLException;
    public void tableLoadDept(DefaultTableModel dtm, String department) throws ClassNotFoundException, SQLException;
    public void tableLoad(DefaultTableModel dtm, String uRole, String department) throws ClassNotFoundException, SQLException ;
}
