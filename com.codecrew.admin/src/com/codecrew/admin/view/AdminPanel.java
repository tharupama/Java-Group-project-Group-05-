/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.codecrew.admin.view;

import com.codecrew.admin.controller.AccountController;
import com.codecrew.admin.controller.CourseController;
import com.codecrew.admin.controller.ExamNoticeController;
import com.codecrew.admin.controller.NoticeController;
import com.codecrew.admin.controller.TimeTableController;
import com.codecrew.admin.db.DbConnection;
import com.codecrew.admin.enums.Day;

import com.codecrew.admin.exception.CourseCodeNotFoundException;
import com.codecrew.admin.exception.AcountNotFoundException;
import com.codecrew.admin.model.AccountModel;
import com.codecrew.admin.model.CourseModel;
import com.codecrew.admin.model.NoticeModel;
import com.codecrew.admin.model.TimeTableModel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerNumberModel;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
    
/**
 *
 * @author USER
 */
public class AdminPanel extends javax.swing.JFrame {

    /**
     * Creates new form AdminPanel
     */
    public AdminPanel() throws ClassNotFoundException, SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        tableLoad();
        courseTableLoad();
        noticeTableLoad();
        timeTableLoad();
        //theoryHoursLabel.setVisible(false);
        practicalHoursLabel.setVisible(false);
        //theoryHoursBox.setVisible(false);
        practicalHoursBox.setVisible(false);
        sem1.setSelected(true);
        courseidLabel.setVisible(false);
            courseIdField.setVisible(false);
            dateLabel.setVisible(false);
            DateField.setVisible(false);
            timeLabel.setVisible(false);
            hourSpinner.setVisible(false);
            minuteSpinner.setVisible(false);

            timeToLabel.setVisible(false);
            hourToSpinner.setVisible(false);
            minuteToSpinner.setVisible(false);

            
            generalTable.setVisible(true);
            jScrollPane3.setVisible(true);
            examTable.setVisible(false);
            jScrollPane4.setVisible(false);
            

 
    }
    
    public void clearBox(){
        idBox.setText("");
        nameBox.setText("");
        contactBox.setText("");
        emailBox.setText("");
        passwordBox.setText("");
        roleBox.setSelectedIndex(0);
        deptBox.setSelectedIndex(0);
    }
 
    
    public void courseFieldClear(){
    courseCodeBox.setText("");
    courseNameBox.setText("");
    typeBox.setSelectedIndex(0);
    courseCreditBox.setText("");
    lecNameBox.setText("");
    courseYearBox.setText("");
    semesterButtonGroup.clearSelection();
    courseDepartmentBox.setSelectedIndex(0);
    theoryHoursBox.setText("");
    practicalHoursBox.setText("");
    }
    
    public void noticeFieldClear(){
    idLabel.setText("Id");
    //typeCombo.setSelectedItem("General");
    noticeTitle.setText("");
    courseIdField.setText("");
    downloadLinkField.setText("");
    contentArea.setText("");
    DateField.setDate(null);
    hourSpinner.setValue(0);
    minuteSpinner.setValue(0);
    hourToSpinner.setValue(0);
    minuteToSpinner.setValue(0);
    }
    
    public void timeTableFieldClear(){
        timeTableIdLabel.setText("Id");
        TTCourseCodeField.setText("");
        TTTypeCombo.setSelectedItem("LECTURE");
        TTDateField.setDate(null);
        TTDayCombo.setSelectedItem("MONDAY");
        
        TTFromHoursSpinner.setValue(0);
        TTFromMinutesSpinner.setValue(0);
        
        TTToHoursSpinner.setValue(0);
        TTToMinutesSpinner.setValue(0);
    }
    
    public void tableToFields(){
    int selectedRow = accountTable.getSelectedRow();
    idBox.setText(accountTable.getValueAt(selectedRow, 0).toString());
    nameBox.setText(accountTable.getValueAt(selectedRow, 1).toString());
    contactBox.setText(accountTable.getValueAt(selectedRow, 2).toString());
    emailBox.setText(accountTable.getValueAt(selectedRow, 3).toString());
    roleBox.setSelectedItem(accountTable.getValueAt(selectedRow, 4).toString());
    deptBox.setSelectedItem(accountTable.getValueAt(selectedRow, 5).toString());

    }
    
    public void courseTableToField(){
    int selectedRow = courseTable.getSelectedRow();
    courseCodeBox.setText(courseTable.getValueAt(selectedRow, 0).toString());
    courseNameBox.setText(courseTable.getValueAt(selectedRow, 1).toString());
    typeBox.setSelectedItem(courseTable.getValueAt(selectedRow, 2).toString());
    courseCreditBox.setText(courseTable.getValueAt(selectedRow, 3).toString());
    lecNameBox.setText(courseTable.getValueAt(selectedRow, 4).toString());
    courseYearBox.setText(courseTable.getValueAt(selectedRow, 5).toString());
    
    String sem = courseTable.getValueAt(selectedRow, 6).toString();
    if(sem.equals("Semester 1")){
        sem1.setActionCommand(sem);
        sem1.setSelected(true);
    }else if(sem.equals("Semester 2")){
        sem2.setActionCommand(sem);
        sem2.setSelected(true);
    }
    
    courseDepartmentBox.setSelectedItem(courseTable.getValueAt(selectedRow, 7).toString());
    theoryHoursBox.setText(courseTable.getValueAt(selectedRow, 8).toString());
    practicalHoursBox.setText(courseTable.getValueAt(selectedRow, 9).toString());
 
    }
    
    public void noticeTableToField(){
    int selectedRow = generalTable.getSelectedRow();
    idLabel.setText(generalTable.getValueAt(selectedRow, 0).toString());
    typeCombo.setSelectedItem(generalTable.getValueAt(selectedRow, 3).toString());
    noticeTitle.setText(generalTable.getValueAt(selectedRow, 4).toString());
    downloadLinkField.setText(generalTable.getValueAt(selectedRow, 6).toString());
    contentArea.setText(generalTable.getValueAt(selectedRow, 5).toString());
    }
    
    public void examNoticeTableToField() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int selectedRow = examTable.getSelectedRow();
    idLabel.setText(examTable.getValueAt(selectedRow, 0).toString());
    typeCombo.setSelectedItem(examTable.getValueAt(selectedRow, 3).toString());
    noticeTitle.setText(examTable.getValueAt(selectedRow, 4).toString());
    downloadLinkField.setText(examTable.getValueAt(selectedRow, 6).toString());
    contentArea.setText(examTable.getValueAt(selectedRow, 5).toString());
    
    courseIdField.setText(examTable.getValueAt(selectedRow, 7).toString());
    DateField.setDate(sdf.parse(examTable.getValueAt(selectedRow, 8).toString()));

    hourSpinner.setValue(Integer.valueOf(examTable.getValueAt(selectedRow, 9).toString().split(":")[0]));
    minuteSpinner.setValue(Integer.valueOf(examTable.getValueAt(selectedRow, 9).toString().split(":")[1]));
    
    hourToSpinner.setValue(Integer.valueOf(examTable.getValueAt(selectedRow, 10).toString().split(":")[0]));
    minuteToSpinner.setValue(Integer.valueOf(examTable.getValueAt(selectedRow, 10).toString().split(":")[1]));
    }
    
    public void timeTableToField() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int selectedRow = timeTable.getSelectedRow();
        timeTableIdLabel.setText(timeTable.getValueAt(selectedRow, 0).toString());
        TTCourseCodeField.setText(timeTable.getValueAt(selectedRow, 1).toString());
        TTTypeCombo.setSelectedItem(timeTable.getValueAt(selectedRow,2).toString());
        TTDateField.setDate(sdf.parse(timeTable.getValueAt(selectedRow, 3).toString()));
        TTDayCombo.setSelectedItem(timeTable.getValueAt(selectedRow, 4).toString());
        
        TTFromHoursSpinner.setValue(Integer.valueOf(timeTable.getValueAt(selectedRow, 5).toString().split(":")[0]));
        TTFromMinutesSpinner.setValue(Integer.valueOf(timeTable.getValueAt(selectedRow, 5).toString().split(":")[1]));
        
        TTToHoursSpinner.setValue(Integer.valueOf(timeTable.getValueAt(selectedRow, 6).toString().split(":")[0]));
        TTToMinutesSpinner.setValue(Integer.valueOf(timeTable.getValueAt(selectedRow, 6).toString().split(":")[1]));
    }
    
    public void courseTableLoad() throws ClassNotFoundException, SQLException{
        DefaultTableModel dtm = (DefaultTableModel)courseTable.getModel();
        CourseController.getInstance().courseTableLoad(dtm);
    }
    
    public void noticeTableLoad() throws ClassNotFoundException, SQLException{
        DefaultTableModel generalDtm = (DefaultTableModel)generalTable.getModel();
        DefaultTableModel examDtm = (DefaultTableModel)examTable.getModel();
        
        NoticeController.getInstance().noticeTableLoad(generalDtm);
        ExamNoticeController.getInstance().noticeTableLoad(examDtm);
    }
    
    public void timeTableLoad() throws ClassNotFoundException, SQLException{
        DefaultTableModel timeDtm = (DefaultTableModel)timeTable.getModel();
        TimeTableController.getInstance().timeTableLoad(timeDtm);
    }
    
    public void tableLoad() throws ClassNotFoundException, SQLException{
        DefaultTableModel dtm = (DefaultTableModel) accountTable.getModel();
        AccountController.getInstance().tableLoad(dtm);
    }
    
    public void tableLoad(String role, String department) throws ClassNotFoundException, SQLException{
        DefaultTableModel dtm = (DefaultTableModel) accountTable.getModel();
        AccountController.getInstance().tableLoad(dtm,role,department);
    }
    
    public void tableLoadRole(String role) throws ClassNotFoundException, SQLException{
        DefaultTableModel dtm = (DefaultTableModel) accountTable.getModel();
        AccountController.getInstance().tableLoadRole(dtm,role);
    }
     public void tableLoadDept(String department) throws ClassNotFoundException, SQLException{
        DefaultTableModel dtm = (DefaultTableModel) accountTable.getModel();
        AccountController.getInstance().tableLoadDept(dtm, department);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        semesterButtonGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        idBox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        nameBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        contactBox = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        emailBox = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        roleBox = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        passwordBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        accountTable = new javax.swing.JTable();
        searchBox = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        deptBox = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        departmentCombo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        roleCombo = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        courseCodeBox = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        courseCreditBox = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        typeBox = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        courseNameBox = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        courseYearBox = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        sem2 = new javax.swing.JRadioButton();
        sem1 = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        courseDepartmentBox = new javax.swing.JComboBox<>();
        theoryHoursLabel = new javax.swing.JLabel();
        theoryHoursBox = new javax.swing.JTextField();
        practicalHoursLabel = new javax.swing.JLabel();
        practicalHoursBox = new javax.swing.JTextField();
        saveBtn1 = new javax.swing.JButton();
        updateBtn1 = new javax.swing.JButton();
        deleteBtn1 = new javax.swing.JButton();
        courseClear = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        courseTable = new javax.swing.JTable();
        courseSearchBox = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lecNameBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        noticeTitle = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        contentArea = new java.awt.TextArea();
        jLabel31 = new javax.swing.JLabel();
        downloadLinkField = new javax.swing.JTextField();
        idLabel = new javax.swing.JLabel();
        typeCombo = new javax.swing.JComboBox<>();
        timeLabel = new javax.swing.JLabel();
        courseIdField = new javax.swing.JTextField();
        DateField = new com.toedter.calendar.JDateChooser();
        courseidLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        minuteSpinner = new javax.swing.JSpinner();
        hourSpinner = new javax.swing.JSpinner();
        saveBtn2 = new javax.swing.JButton();
        updateBtn2 = new javax.swing.JButton();
        deleteBtn2 = new javax.swing.JButton();
        courseClear1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        examTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        generalTable = new javax.swing.JTable();
        noticeSearchBox = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        timeToLabel = new javax.swing.JLabel();
        hourToSpinner = new javax.swing.JSpinner();
        minuteToSpinner = new javax.swing.JSpinner();
        jLabel35 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        TTCourseCodeField = new javax.swing.JTextField();
        timeTableIdLabel = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        TTTypeCombo = new javax.swing.JComboBox<>();
        TTDateField = new com.toedter.calendar.JDateChooser();
        dateLabel1 = new javax.swing.JLabel();
        TTDayCombo = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        timeLabel1 = new javax.swing.JLabel();
        TTFromHoursSpinner = new javax.swing.JSpinner();
        TTFromMinutesSpinner = new javax.swing.JSpinner();
        timeToLabel1 = new javax.swing.JLabel();
        TTToHoursSpinner = new javax.swing.JSpinner();
        TTToMinutesSpinner = new javax.swing.JSpinner();
        saveBtn3 = new javax.swing.JButton();
        updateBtn3 = new javax.swing.JButton();
        deleteBtn3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        timeTableSearchBox = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        timeTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(51, 0, 255));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Acount Management");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 380, 50));

        jLabel9.setBackground(new java.awt.Color(255, 204, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Id");
        jLabel9.setOpaque(true);
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 140, 40));

        idBox.setBackground(new java.awt.Color(255, 51, 51));
        idBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        idBox.setForeground(new java.awt.Color(255, 255, 255));
        idBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idBoxActionPerformed(evt);
            }
        });
        jPanel2.add(idBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 122, 300, 40));

        jLabel10.setBackground(new java.awt.Color(255, 204, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Name");
        jLabel10.setOpaque(true);
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 140, 40));

        nameBox.setBackground(new java.awt.Color(255, 51, 51));
        nameBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        nameBox.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(nameBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 300, 40));

        jLabel11.setBackground(new java.awt.Color(255, 204, 0));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Contact");
        jLabel11.setOpaque(true);
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 140, 40));

        contactBox.setBackground(new java.awt.Color(255, 51, 51));
        contactBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        contactBox.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(contactBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 300, 40));

        jLabel12.setBackground(new java.awt.Color(255, 204, 0));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Email");
        jLabel12.setOpaque(true);
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 140, 40));

        emailBox.setBackground(new java.awt.Color(255, 51, 51));
        emailBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        emailBox.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(emailBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 300, 40));

        jLabel13.setBackground(new java.awt.Color(255, 204, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Role");
        jLabel13.setOpaque(true);
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 140, 40));

        roleBox.setBackground(new java.awt.Color(255, 51, 51));
        roleBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        roleBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lecturer", "Student", "Technical Officer", "Admin", " " }));
        jPanel2.add(roleBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 300, 40));

        jLabel14.setBackground(new java.awt.Color(255, 204, 0));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Password");
        jLabel14.setOpaque(true);
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 140, 40));

        passwordBox.setBackground(new java.awt.Color(255, 51, 51));
        passwordBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        passwordBox.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(passwordBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 300, 40));

        jButton1.setBackground(new java.awt.Color(153, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 540, 120, 40));

        saveBtn.setBackground(new java.awt.Color(102, 255, 0));
        saveBtn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(0, 0, 0));
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel2.add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 120, 40));

        updateBtn.setBackground(new java.awt.Color(255, 204, 0));
        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        updateBtn.setForeground(new java.awt.Color(0, 0, 0));
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        jPanel2.add(updateBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 490, 120, 40));

        deleteBtn.setBackground(new java.awt.Color(255, 0, 0));
        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(0, 0, 0));
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        jPanel2.add(deleteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 490, 120, 40));

        jButton5.setBackground(new java.awt.Color(255, 51, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setText("Clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 540, 120, 40));

        accountTable.setBackground(new java.awt.Color(255, 255, 255));
        accountTable.setForeground(new java.awt.Color(0, 0, 0));
        accountTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "name", "contact", "email", "role", "department"
            }
        ));
        accountTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountTableMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                accountTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(accountTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 200, 670, 400));

        searchBox.setBackground(new java.awt.Color(255, 255, 255));
        searchBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        searchBox.setForeground(new java.awt.Color(0, 0, 0));
        searchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBoxActionPerformed(evt);
            }
        });
        searchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchBoxKeyReleased(evt);
            }
        });
        jPanel2.add(searchBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 450, 40));

        jLabel15.setBackground(new java.awt.Color(0, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Search");
        jLabel15.setOpaque(true);
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 20, 140, 30));

        deptBox.setBackground(new java.awt.Color(255, 51, 51));
        deptBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        deptBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ICT", "ET", "BST", "MULTIDISCIPLINARY", " " }));
        jPanel2.add(deptBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 300, 40));

        jLabel16.setBackground(new java.awt.Color(255, 204, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Department");
        jLabel16.setOpaque(true);
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 140, 40));

        departmentCombo.setBackground(new java.awt.Color(255, 255, 255));
        departmentCombo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        departmentCombo.setForeground(new java.awt.Color(0, 0, 0));
        departmentCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "ICT", "ET", "BST", "MULTIDISCIPLINARY", " " }));
        departmentCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departmentComboActionPerformed(evt);
            }
        });
        jPanel2.add(departmentCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 150, 200, 40));

        jLabel17.setBackground(new java.awt.Color(102, 0, 204));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Department");
        jLabel17.setOpaque(true);
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 130, 200, 20));

        roleCombo.setBackground(new java.awt.Color(255, 255, 255));
        roleCombo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        roleCombo.setForeground(new java.awt.Color(0, 0, 0));
        roleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Lecturer", "Student", "Technical Officer", "Admin" }));
        roleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleComboActionPerformed(evt);
            }
        });
        jPanel2.add(roleCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 150, 200, 40));

        jLabel18.setBackground(new java.awt.Color(102, 0, 204));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Role");
        jLabel18.setOpaque(true);
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 130, 200, 20));

        jPanel1.setBackground(new java.awt.Color(204, 204, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setBackground(new java.awt.Color(153, 204, 0));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Filter");
        jLabel19.setOpaque(true);
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 6, 110, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 90, 450, 110));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/admin/view/university-of-ruhuna.jpg"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, 638));

        jTabbedPane1.addTab("Account Management", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Course Management");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 380, 50));

        jLabel20.setBackground(new java.awt.Color(255, 204, 0));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Course code");
        jLabel20.setOpaque(true);
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 40));

        courseCodeBox.setBackground(new java.awt.Color(255, 51, 51));
        courseCodeBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        courseCodeBox.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(courseCodeBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 220, 40));

        jLabel21.setBackground(new java.awt.Color(255, 204, 0));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Name");
        jLabel21.setOpaque(true);
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 160, 40));

        courseCreditBox.setBackground(new java.awt.Color(255, 51, 51));
        courseCreditBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        courseCreditBox.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(courseCreditBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 220, 40));

        jLabel22.setBackground(new java.awt.Color(255, 204, 0));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Type");
        jLabel22.setOpaque(true);
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 160, 40));

        typeBox.setBackground(new java.awt.Color(255, 51, 51));
        typeBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        typeBox.setForeground(new java.awt.Color(0, 0, 0));
        typeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theory", "Practical", "Both" }));
        typeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeBoxActionPerformed(evt);
            }
        });
        jPanel3.add(typeBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 220, 40));

        jLabel23.setBackground(new java.awt.Color(255, 204, 0));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Credit");
        jLabel23.setOpaque(true);
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 160, 40));

        courseNameBox.setBackground(new java.awt.Color(255, 51, 51));
        courseNameBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        courseNameBox.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(courseNameBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 220, 40));

        jLabel24.setBackground(new java.awt.Color(255, 204, 0));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Semester");
        jLabel24.setOpaque(true);
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 180, 160, 40));

        courseYearBox.setBackground(new java.awt.Color(255, 51, 51));
        courseYearBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        courseYearBox.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(courseYearBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 130, 220, 40));

        jLabel25.setBackground(new java.awt.Color(255, 204, 0));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Year");
        jLabel25.setOpaque(true);
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 130, 160, 40));

        semesterButtonGroup.add(sem2);
        sem2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sem2.setForeground(new java.awt.Color(0, 0, 0));
        sem2.setText("Semester 2");
        sem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sem2ActionPerformed(evt);
            }
        });
        jPanel3.add(sem2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 180, 100, 40));

        semesterButtonGroup.add(sem1);
        sem1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sem1.setForeground(new java.awt.Color(0, 0, 0));
        sem1.setText("Semester 1");
        sem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sem1ActionPerformed(evt);
            }
        });
        jPanel3.add(sem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 180, 100, 40));

        jLabel26.setBackground(new java.awt.Color(255, 204, 0));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Department");
        jLabel26.setOpaque(true);
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 160, 40));

        courseDepartmentBox.setBackground(new java.awt.Color(255, 51, 51));
        courseDepartmentBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        courseDepartmentBox.setForeground(new java.awt.Color(0, 0, 0));
        courseDepartmentBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ICT", "ET", "BST" }));
        jPanel3.add(courseDepartmentBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 220, 40));

        theoryHoursLabel.setBackground(new java.awt.Color(255, 204, 0));
        theoryHoursLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        theoryHoursLabel.setForeground(new java.awt.Color(0, 0, 0));
        theoryHoursLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        theoryHoursLabel.setText("Theory hours");
        theoryHoursLabel.setOpaque(true);
        jPanel3.add(theoryHoursLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, 160, 40));

        theoryHoursBox.setBackground(new java.awt.Color(255, 51, 51));
        theoryHoursBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        theoryHoursBox.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(theoryHoursBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 220, 40));

        practicalHoursLabel.setBackground(new java.awt.Color(255, 204, 0));
        practicalHoursLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        practicalHoursLabel.setForeground(new java.awt.Color(0, 0, 0));
        practicalHoursLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        practicalHoursLabel.setText("Practical hours");
        practicalHoursLabel.setOpaque(true);
        jPanel3.add(practicalHoursLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, 160, 40));

        practicalHoursBox.setBackground(new java.awt.Color(255, 51, 51));
        practicalHoursBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        practicalHoursBox.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(practicalHoursBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 220, 40));

        saveBtn1.setBackground(new java.awt.Color(102, 255, 0));
        saveBtn1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        saveBtn1.setForeground(new java.awt.Color(0, 0, 0));
        saveBtn1.setText("Save");
        saveBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtn1ActionPerformed(evt);
            }
        });
        jPanel3.add(saveBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 120, 40));

        updateBtn1.setBackground(new java.awt.Color(255, 204, 0));
        updateBtn1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        updateBtn1.setForeground(new java.awt.Color(0, 0, 0));
        updateBtn1.setText("Update");
        updateBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtn1ActionPerformed(evt);
            }
        });
        jPanel3.add(updateBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 120, 40));

        deleteBtn1.setBackground(new java.awt.Color(255, 0, 0));
        deleteBtn1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        deleteBtn1.setForeground(new java.awt.Color(0, 0, 0));
        deleteBtn1.setText("Delete");
        deleteBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtn1ActionPerformed(evt);
            }
        });
        jPanel3.add(deleteBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 230, 120, 40));

        courseClear.setBackground(new java.awt.Color(255, 51, 0));
        courseClear.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        courseClear.setForeground(new java.awt.Color(0, 0, 0));
        courseClear.setText("Clear");
        courseClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseClearActionPerformed(evt);
            }
        });
        jPanel3.add(courseClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 230, 120, 40));

        jButton2.setBackground(new java.awt.Color(153, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 230, 120, 40));

        courseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Code", "Name", "Type", "Credit", "lec_name", "Year", "Semester", "Department", "Theory hours", "Practical hours"
            }
        ));
        courseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                courseTableMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(courseTable);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 1200, 340));

        courseSearchBox.setBackground(new java.awt.Color(255, 255, 255));
        courseSearchBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        courseSearchBox.setForeground(new java.awt.Color(0, 0, 0));
        courseSearchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseSearchBoxActionPerformed(evt);
            }
        });
        courseSearchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                courseSearchBoxKeyReleased(evt);
            }
        });
        jPanel3.add(courseSearchBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 30, 370, 40));

        jLabel27.setBackground(new java.awt.Color(0, 255, 255));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Search");
        jLabel27.setOpaque(true);
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 0, 140, 30));

        jLabel28.setBackground(new java.awt.Color(255, 204, 0));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Lectur name");
        jLabel28.setOpaque(true);
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 160, 40));

        lecNameBox.setBackground(new java.awt.Color(255, 51, 51));
        lecNameBox.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lecNameBox.setForeground(new java.awt.Color(0, 0, 0));
        lecNameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lecNameBoxActionPerformed(evt);
            }
        });
        jPanel3.add(lecNameBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 80, 220, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/admin/view/university-of-ruhuna.jpg"))); // NOI18N
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 629));

        jTabbedPane1.addTab("Course management", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Notice Management");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 380, 50));

        jLabel29.setBackground(new java.awt.Color(255, 204, 0));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Title");
        jLabel29.setOpaque(true);
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, 40));

        noticeTitle.setBackground(new java.awt.Color(255, 51, 51));
        noticeTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        noticeTitle.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(noticeTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 180, 40));

        jLabel30.setBackground(new java.awt.Color(255, 204, 0));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Content");
        jLabel30.setOpaque(true);
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 130, 120, 40));

        contentArea.setBackground(new java.awt.Color(255, 51, 51));
        contentArea.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jPanel4.add(contentArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 130, 350, 140));

        jLabel31.setBackground(new java.awt.Color(255, 204, 0));
        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Download Link");
        jLabel31.setOpaque(true);
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 190, 40));

        downloadLinkField.setBackground(new java.awt.Color(255, 51, 51));
        downloadLinkField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        downloadLinkField.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(downloadLinkField, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 180, 40));

        idLabel.setBackground(new java.awt.Color(255, 204, 0));
        idLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        idLabel.setForeground(new java.awt.Color(0, 0, 0));
        idLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idLabel.setText("Id");
        idLabel.setOpaque(true);
        idLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                idLabelMouseReleased(evt);
            }
        });
        jPanel4.add(idLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 90, 40));

        typeCombo.setBackground(new java.awt.Color(255, 51, 51));
        typeCombo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        typeCombo.setForeground(new java.awt.Color(0, 0, 0));
        typeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "General", "Exam", " " }));
        typeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboActionPerformed(evt);
            }
        });
        jPanel4.add(typeCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 180, 40));

        timeLabel.setBackground(new java.awt.Color(255, 204, 0));
        timeLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        timeLabel.setForeground(new java.awt.Color(0, 0, 0));
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("Time From");
        timeLabel.setOpaque(true);
        jPanel4.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 190, 40));

        courseIdField.setBackground(new java.awt.Color(255, 51, 51));
        courseIdField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        courseIdField.setForeground(new java.awt.Color(0, 0, 0));
        courseIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseIdFieldActionPerformed(evt);
            }
        });
        jPanel4.add(courseIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 180, 40));

        DateField.setBackground(new java.awt.Color(255, 0, 0));
        DateField.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(DateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 180, 40));

        courseidLabel.setBackground(new java.awt.Color(255, 204, 0));
        courseidLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        courseidLabel.setForeground(new java.awt.Color(0, 0, 0));
        courseidLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        courseidLabel.setText("Course Id");
        courseidLabel.setOpaque(true);
        jPanel4.add(courseidLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 90, 40));

        dateLabel.setBackground(new java.awt.Color(255, 204, 0));
        dateLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(0, 0, 0));
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLabel.setText("Date");
        dateLabel.setOpaque(true);
        jPanel4.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 90, 40));

        minuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        jPanel4.add(minuteSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(604, 130, 90, 40));

        hourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        jPanel4.add(hourSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 90, 40));

        saveBtn2.setBackground(new java.awt.Color(102, 255, 0));
        saveBtn2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        saveBtn2.setForeground(new java.awt.Color(0, 0, 0));
        saveBtn2.setText("Save");
        saveBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtn2ActionPerformed(evt);
            }
        });
        jPanel4.add(saveBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 120, 40));

        updateBtn2.setBackground(new java.awt.Color(255, 204, 0));
        updateBtn2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        updateBtn2.setForeground(new java.awt.Color(0, 0, 0));
        updateBtn2.setText("Update");
        updateBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtn2ActionPerformed(evt);
            }
        });
        jPanel4.add(updateBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 120, 40));

        deleteBtn2.setBackground(new java.awt.Color(255, 0, 0));
        deleteBtn2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        deleteBtn2.setForeground(new java.awt.Color(0, 0, 0));
        deleteBtn2.setText("Delete");
        deleteBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtn2ActionPerformed(evt);
            }
        });
        jPanel4.add(deleteBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 120, 40));

        courseClear1.setBackground(new java.awt.Color(255, 51, 0));
        courseClear1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        courseClear1.setForeground(new java.awt.Color(0, 0, 0));
        courseClear1.setText("Clear");
        courseClear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseClear1ActionPerformed(evt);
            }
        });
        jPanel4.add(courseClear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 120, 40));

        jButton3.setBackground(new java.awt.Color(153, 0, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 120, 40));

        examTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "noticeId", "Created", "Updated", "Type", "Title", "Content", "downloadLink", "courseId", "Date", "timeFrom", "timeTo"
            }
        ));
        examTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                examTableMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(examTable);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 1160, 340));

        generalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "noticeId", "Created", "Updated", "Type", "Title", "Content", "downloadLink"
            }
        ));
        generalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                generalTableMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(generalTable);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 1160, 330));

        noticeSearchBox.setBackground(new java.awt.Color(255, 255, 255));
        noticeSearchBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        noticeSearchBox.setForeground(new java.awt.Color(0, 0, 0));
        noticeSearchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noticeSearchBoxActionPerformed(evt);
            }
        });
        noticeSearchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                noticeSearchBoxKeyReleased(evt);
            }
        });
        jPanel4.add(noticeSearchBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 70, 370, 40));

        jLabel34.setBackground(new java.awt.Color(0, 255, 255));
        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Search");
        jLabel34.setOpaque(true);
        jPanel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 40, 140, 30));

        timeToLabel.setBackground(new java.awt.Color(255, 204, 0));
        timeToLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        timeToLabel.setForeground(new java.awt.Color(0, 0, 0));
        timeToLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeToLabel.setText("Time To");
        timeToLabel.setOpaque(true);
        jPanel4.add(timeToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 190, 40));

        hourToSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        jPanel4.add(hourToSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 90, 40));

        minuteToSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        jPanel4.add(minuteToSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(604, 180, 90, 40));

        jLabel35.setBackground(new java.awt.Color(255, 204, 0));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Type");
        jLabel35.setOpaque(true);
        jPanel4.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, 40));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/admin/view/university-of-ruhuna.jpg"))); // NOI18N
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("Notice management", jPanel4);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Timetable Management");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 420, 50));

        jLabel32.setBackground(new java.awt.Color(255, 204, 0));
        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Course code");
        jLabel32.setOpaque(true);
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 160, 40));

        TTCourseCodeField.setBackground(new java.awt.Color(255, 51, 51));
        TTCourseCodeField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TTCourseCodeField.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(TTCourseCodeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 220, 40));

        timeTableIdLabel.setBackground(new java.awt.Color(255, 204, 0));
        timeTableIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        timeTableIdLabel.setForeground(new java.awt.Color(0, 0, 0));
        timeTableIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeTableIdLabel.setText("Id");
        timeTableIdLabel.setOpaque(true);
        timeTableIdLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                timeTableIdLabelMouseReleased(evt);
            }
        });
        jPanel5.add(timeTableIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 160, 40));

        jLabel33.setBackground(new java.awt.Color(255, 204, 0));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Type");
        jLabel33.setOpaque(true);
        jPanel5.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 160, 40));

        TTTypeCombo.setBackground(new java.awt.Color(255, 51, 51));
        TTTypeCombo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TTTypeCombo.setForeground(new java.awt.Color(0, 0, 0));
        TTTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LECTURE", "EXAM" }));
        TTTypeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TTTypeComboActionPerformed(evt);
            }
        });
        jPanel5.add(TTTypeCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 220, 40));

        TTDateField.setBackground(new java.awt.Color(255, 0, 0));
        TTDateField.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(TTDateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 220, 40));

        dateLabel1.setBackground(new java.awt.Color(255, 204, 0));
        dateLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        dateLabel1.setForeground(new java.awt.Color(0, 0, 0));
        dateLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLabel1.setText("Date");
        dateLabel1.setOpaque(true);
        jPanel5.add(dateLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, 160, 40));

        TTDayCombo.setBackground(new java.awt.Color(255, 51, 51));
        TTDayCombo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TTDayCombo.setForeground(new java.awt.Color(0, 0, 0));
        TTDayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" }));
        TTDayCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TTDayComboActionPerformed(evt);
            }
        });
        jPanel5.add(TTDayCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 220, 40));

        jLabel36.setBackground(new java.awt.Color(255, 204, 0));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Day");
        jLabel36.setOpaque(true);
        jPanel5.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 160, 40));

        timeLabel1.setBackground(new java.awt.Color(255, 204, 0));
        timeLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        timeLabel1.setForeground(new java.awt.Color(0, 0, 0));
        timeLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel1.setText("Time From");
        timeLabel1.setOpaque(true);
        jPanel5.add(timeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 110, 160, 40));

        TTFromHoursSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        jPanel5.add(TTFromHoursSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 110, 80, 40));

        TTFromMinutesSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        jPanel5.add(TTFromMinutesSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 110, 80, 40));

        timeToLabel1.setBackground(new java.awt.Color(255, 204, 0));
        timeToLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        timeToLabel1.setForeground(new java.awt.Color(0, 0, 0));
        timeToLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeToLabel1.setText("Time To");
        timeToLabel1.setOpaque(true);
        jPanel5.add(timeToLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 160, 160, 40));

        TTToHoursSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        jPanel5.add(TTToHoursSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 160, 80, 40));

        TTToMinutesSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        jPanel5.add(TTToMinutesSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 160, 80, 40));

        saveBtn3.setBackground(new java.awt.Color(102, 255, 0));
        saveBtn3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        saveBtn3.setForeground(new java.awt.Color(0, 0, 0));
        saveBtn3.setText("Save");
        saveBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtn3ActionPerformed(evt);
            }
        });
        jPanel5.add(saveBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 120, 40));

        updateBtn3.setBackground(new java.awt.Color(255, 204, 0));
        updateBtn3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        updateBtn3.setForeground(new java.awt.Color(0, 0, 0));
        updateBtn3.setText("Update");
        updateBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtn3ActionPerformed(evt);
            }
        });
        jPanel5.add(updateBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, 120, 40));

        deleteBtn3.setBackground(new java.awt.Color(255, 0, 0));
        deleteBtn3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        deleteBtn3.setForeground(new java.awt.Color(0, 0, 0));
        deleteBtn3.setText("Delete");
        deleteBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtn3ActionPerformed(evt);
            }
        });
        jPanel5.add(deleteBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 210, 120, 40));

        jButton4.setBackground(new java.awt.Color(153, 0, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 210, 120, 40));

        jButton6.setBackground(new java.awt.Color(255, 51, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setText("Clear");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 210, 120, 40));

        timeTableSearchBox.setBackground(new java.awt.Color(255, 255, 255));
        timeTableSearchBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        timeTableSearchBox.setForeground(new java.awt.Color(0, 0, 0));
        timeTableSearchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeTableSearchBoxActionPerformed(evt);
            }
        });
        timeTableSearchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                timeTableSearchBoxKeyReleased(evt);
            }
        });
        jPanel5.add(timeTableSearchBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, 370, 40));

        jLabel37.setBackground(new java.awt.Color(0, 255, 255));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Search");
        jLabel37.setOpaque(true);
        jPanel5.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 30, 140, 30));

        timeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "courseCode", "type", "date", "day", "timeFrom", "timeTo"
            }
        ));
        timeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timeTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(timeTable);

        jPanel5.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 257, 1160, 350));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/admin/view/university-of-ruhuna.jpg"))); // NOI18N
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1200, 650));

        jTabbedPane1.addTab("Timetable management", jPanel5);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBoxActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if (idBox.getText().equals("") || nameBox.getText().equals("") || contactBox.getText().equals("") || emailBox.getText().equals("") || passwordBox.getText().equals("")) {
            System.out.println("All fields must fill !");
            JOptionPane.showMessageDialog(null, "All fields must fill !");
        } else if (!contactBox.getText().matches("^[0-9]{10,15}$")) {
            JOptionPane.showMessageDialog(null, "contact numbber must contain only 10 to 15 numbers !");
        } else if (!emailBox.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Please enter valid email! Example: tharupama826@gmail.com");

        } else {
            AccountModel accountModel = new AccountModel(idBox.getText(), nameBox.getText(),Long.parseLong(contactBox.getText()), emailBox.getText(), passwordBox.getText(), roleBox.getSelectedItem().toString(), deptBox.getSelectedItem().toString());
            try {
                
                    boolean affectedRows = AccountController.getInstance().saveAccount(accountModel);
                    if (affectedRows == true) {
                        tableLoad();
                        clearBox();
                        JOptionPane.showMessageDialog(rootPane, "Account saved sucessfully !");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Unexpected error");
                    }
                

                // TODO add your handling code here:
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }


    }//GEN-LAST:event_saveBtnActionPerformed

    private void idBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idBoxActionPerformed

    private void accountTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountTableMouseClicked
        tableToFields();// TODO add your handling code here:
    }//GEN-LAST:event_accountTableMouseClicked

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed

        try {
            if(idBox.getText().equals("")||nameBox.getText().equals("")||contactBox.getText().equals("")||emailBox.getText().equals("")){
            System.out.println("All fields must fill !");
            JOptionPane.showMessageDialog(null,"All fields must fill !");
        }else if(!contactBox.getText().matches("^[0-9]{10,15}$")){
            JOptionPane.showMessageDialog(null,"contact numbber must contain only numbers and 10 to 15 numbers !");
        }else if(!emailBox.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            JOptionPane.showMessageDialog(null,"Please enter valid email! Example: tharupama826@gmail.com");
        
        }else{       
            AccountModel accountModel = new AccountModel(idBox.getText(),nameBox.getText(), Long.parseLong(contactBox.getText()), emailBox.getText(), passwordBox.getText(),roleBox.getSelectedItem().toString(), deptBox.getSelectedItem().toString());
            boolean affectedRows = AccountController.getInstance().updateAccount(accountModel);
            if(affectedRows==true){
            tableLoad();
            JOptionPane.showMessageDialog(rootPane, "Account updated sucessfully !");
            }else{
            JOptionPane.showMessageDialog(rootPane, "update error !");
            }
        }
            
            // TODO add your handling code here:
        } catch (SQLException | ClassNotFoundException |AcountNotFoundException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        String id = idBox.getText();
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + id + " ?");

        if (response == 0) {
            try {
                boolean affectedRows = AccountController.getInstance().deleteAccount(id);
                if (affectedRows == true) {
                    tableLoad();
                    clearBox();

                    JOptionPane.showMessageDialog(rootPane, "Deleted sucessfully !");

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Delete error!");
                }

            } catch (ClassNotFoundException | SQLException | AcountNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }


    }//GEN-LAST:event_deleteBtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
     clearBox();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void searchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBoxKeyReleased
        DefaultTableModel dtm = (DefaultTableModel)accountTable.getModel();
        try {
            
            AccountController.getInstance().search(searchBox.getText(), dtm);        // TODO add your handling code here:
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchBoxKeyReleased

    private void roleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleComboActionPerformed
       
       String role = (String) roleCombo.getSelectedItem();
       String department = (String) departmentCombo.getSelectedItem();
       
       if(role.equals("All") && department.equals("All")){
           try {
               tableLoad();
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else if(department.equals("All")){         
           try {
               tableLoadRole(role);
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else if(role.equals("All")){
                try {
                tableLoadDept(department);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
           try {
               tableLoad(role,department);
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }//GEN-LAST:event_roleComboActionPerformed

    private void departmentComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departmentComboActionPerformed
        
        String department = (String) departmentCombo.getSelectedItem();
        String role = (String) roleCombo.getSelectedItem();
  
              if(department.equals("All")&& role.equals("All")){
           try {
               tableLoad();
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else if(role.equals("All")){
            try {
                tableLoadDept(department);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else if(department.equals("All")){
           try {
               tableLoadRole(role);
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }else{
            try {
               tableLoad(role,department);
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
        
    }//GEN-LAST:event_departmentComboActionPerformed

    private void typeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeBoxActionPerformed
        String Type = (String) typeBox.getSelectedItem();
        if(Type.equals("Theory")){
            practicalHoursLabel.setVisible(false);
        practicalHoursBox.setVisible(false);
            theoryHoursLabel.setVisible(true);
            theoryHoursBox.setVisible(true);
        }else if(Type.equals("Practical")){
            theoryHoursLabel.setVisible(false);
            theoryHoursBox.setVisible(false);
        practicalHoursLabel.setVisible(true);
        practicalHoursBox.setVisible(true);
        }else if(Type.equals("Both")){
            theoryHoursLabel.setVisible(true);
            theoryHoursBox.setVisible(true);
            practicalHoursLabel.setVisible(true);
            practicalHoursBox.setVisible(true);
        
        }else{
            practicalHoursLabel.setVisible(false);
        practicalHoursBox.setVisible(false);
          theoryHoursLabel.setVisible(false);
            theoryHoursBox.setVisible(false);
        }
    }//GEN-LAST:event_typeBoxActionPerformed

    private void saveBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtn1ActionPerformed
        String semester;
        int theoryHours = 0;
        int practicalHours = 0;
        int credit = 0;
        int year = 0;
        if (sem1.getActionCommand().equals("Semester 1")) {
            semester = "Semester 1";
        } else if (sem2.getActionCommand().equals("Semester 2")) {
            semester = "Semester 2";
        } else {
            semester = "unknown";
        }
        if (!theoryHoursBox.getText().equals("")) {
            try {
                theoryHours = Integer.parseInt(theoryHoursBox.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "you can only enter numbers as theory hours!");
                return;
            }

        }
        if (!practicalHoursBox.getText().equals("")) {
            try {
                practicalHours = Integer.parseInt(practicalHoursBox.getText());
            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(null, "you can only enter numbers as practical hours!");
                return;
            }

        }

        if (courseNameBox.getText().equals("") || courseCodeBox.getText().equals("") || courseCreditBox.getText().equals("") || lecNameBox.getText().equals("") || courseYearBox.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "All fields must fill!");
        } else if ((typeBox.getSelectedItem().toString().equals("Theory") && theoryHours == 0) || (typeBox.getSelectedItem().toString().equals("Practical") && practicalHours == 0) || (typeBox.getSelectedItem().toString().equals("Both") && (theoryHours == 0 || practicalHours == 0))) {
            JOptionPane.showMessageDialog(null, "you have to enter hours accourding to your selection type!");
        } else {

            try {
                credit = Integer.parseInt(courseCreditBox.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "credit can only contain numbers");
                return;
            }

            try {
                year = Integer.parseInt(courseYearBox.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "year can only contain numbers");
                return;
            }

            CourseModel courseModel = new CourseModel(courseCodeBox.getText(),
                    courseNameBox.getText(),
                    typeBox.getSelectedItem().toString(),
                    credit,
                    lecNameBox.getText(),
                    year,
                    semester,
                    courseDepartmentBox.getSelectedItem().toString(),
                    theoryHours,
                    practicalHours
            );
 
            boolean affected;
            try {
                affected = CourseController.getInstance().courseSave(courseModel);
                if (affected == true) {
                    courseTableLoad();
                    courseFieldClear();
                    JOptionPane.showMessageDialog(null, "saved sucessfully !");

                } else {
                    JOptionPane.showMessageDialog(null, "save error !");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }


    }//GEN-LAST:event_saveBtn1ActionPerformed

    private void updateBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtn1ActionPerformed

        int theoryHours = 0;
        int practicalHours = 0;
        String semester;
        int credit = 0;
        int year = 0;
        if (!theoryHoursBox.getText().equals("")) {
            try {
                theoryHours = Integer.parseInt(theoryHoursBox.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "you can only enter numbers as theory hours!");
                return;
            }

        }
        if (!practicalHoursBox.getText().equals("")) {
            try {
                practicalHours = Integer.parseInt(practicalHoursBox.getText());
            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(null, "you can only enter numbers as practical hours!");
                return;
            }

        }

        if (courseNameBox.getText().equals("") || courseCodeBox.getText().equals("") || courseCreditBox.getText().equals("") || lecNameBox.getText().equals("") || courseYearBox.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "All fields must fill!");
        } else if ((typeBox.getSelectedItem().toString().equals("Theory") && theoryHours == 0) || (typeBox.getSelectedItem().toString().equals("Practical") && practicalHours == 0) || (typeBox.getSelectedItem().toString().equals("Both") && (theoryHours == 0 || practicalHours == 0))) {
            JOptionPane.showMessageDialog(null, "you have to enter hours accourding to your selection type!");
        } else {
            
            try {
                credit = Integer.parseInt(courseCreditBox.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "credit can only contain numbers");
                return;
            }

            try {
                year = Integer.parseInt(courseYearBox.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "year can only contain numbers");
                return;
            }
            
           
            
            if (sem1.getActionCommand().equals("Semester 1")) {
                semester = "Semester 1";
            } else if (sem2.getActionCommand().equals("Semester 2")) {
                semester = "Semester 2";
            } else {
                semester = "unknown";
            }

//            if (!theoryHoursBox.getText().equals("")) {
//                theoryHours = Integer.parseInt(theoryHoursBox.getText());
//            }
//            if (!practicalHoursBox.getText().equals("")) {
//                practicalHours = Integer.parseInt(practicalHoursBox.getText());
//            }
            CourseModel courseModel = new CourseModel(courseCodeBox.getText(),
                    courseNameBox.getText(),
                    typeBox.getSelectedItem().toString(),
                    credit,
                    lecNameBox.getText(),
                    year,
                    semester,
                    courseDepartmentBox.getSelectedItem().toString(),
                    theoryHours,
                    practicalHours
            );
            try {
                boolean affectedRows = CourseController.getInstance().updateCourse(courseModel);
                if (affectedRows == true) {
                    courseTableLoad();
                    JOptionPane.showMessageDialog(rootPane, "Course updated sucessfully !");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "update error !");
                }
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, ex);
            } catch(CourseCodeNotFoundException ex){
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }


    }//GEN-LAST:event_updateBtn1ActionPerformed

    private void deleteBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtn1ActionPerformed
        String id = courseCodeBox.getText();
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + id + " ?");

        if (response == 0) {
            boolean deleted;
            try {
                deleted = CourseController.getInstance().delete(id);
                if (deleted == true) {
                    courseTableLoad();
                    courseFieldClear();
                    noticeTableLoad();
                    timeTableLoad();
                    JOptionPane.showMessageDialog(departmentCombo, "deleted sucessfully");

                } else {
                    JOptionPane.showMessageDialog(departmentCombo, "delete error");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CourseCodeNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }


    }//GEN-LAST:event_deleteBtn1ActionPerformed

    private void courseClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseClearActionPerformed
            courseFieldClear();
    }//GEN-LAST:event_courseClearActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void sem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sem2ActionPerformed
        sem1.setActionCommand("");
        sem2.setActionCommand("Semester 2");
    }//GEN-LAST:event_sem2ActionPerformed

    private void sem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sem1ActionPerformed
        sem2.setActionCommand("");
        sem1.setActionCommand("Semester 1");
    }//GEN-LAST:event_sem1ActionPerformed

    private void courseSearchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseSearchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseSearchBoxActionPerformed

    private void courseSearchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_courseSearchBoxKeyReleased
           DefaultTableModel dtm = (DefaultTableModel)courseTable.getModel();
        try {
             
            CourseController.getInstance().search(courseSearchBox.getText(), dtm);        // TODO add your handling code here:
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_courseSearchBoxKeyReleased

    private void accountTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountTableMouseReleased
        
    }//GEN-LAST:event_accountTableMouseReleased

    private void lecNameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lecNameBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lecNameBoxActionPerformed

    private void courseTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseTableMouseReleased
        courseTableToField();
    }//GEN-LAST:event_courseTableMouseReleased

    private void courseIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseIdFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseIdFieldActionPerformed

    private void typeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboActionPerformed
        String selectedItem = (String)typeCombo.getSelectedItem();
        if(selectedItem.equals("General")){
            courseidLabel.setVisible(false);
            courseIdField.setVisible(false);
            dateLabel.setVisible(false);
            DateField.setVisible(false);
            timeLabel.setVisible(false);
            hourSpinner.setVisible(false);
            minuteSpinner.setVisible(false);

            generalTable.setVisible(true);
            jScrollPane3.setVisible(true);
            examTable.setVisible(false);
            jScrollPane4.setVisible(false);
            timeToLabel.setVisible(false);
            hourToSpinner.setVisible(false);
            minuteToSpinner.setVisible(false);

        }else{
            courseidLabel.setVisible(true);
            courseIdField.setVisible(true);
            dateLabel.setVisible(true);
            DateField.setVisible(true);
            timeLabel.setVisible(true);
            hourSpinner.setVisible(true);
            minuteSpinner.setVisible(true);

            generalTable.setVisible(false);
            jScrollPane3.setVisible(false);
            examTable.setVisible(true);
            jScrollPane4.setVisible(true);
            timeToLabel.setVisible(true);
            hourToSpinner.setVisible(true);
            minuteToSpinner.setVisible(true);

        }
    }//GEN-LAST:event_typeComboActionPerformed

    private void saveBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtn2ActionPerformed
        String type = (String)typeCombo.getSelectedItem();
        
        
        if(type.equals("General")){
            if (noticeTitle.getText().equals("") || downloadLinkField.getText().equals("") || contentArea.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "All fields must fill accourding to your selection type!");
            } else {
                //NoticeController noticeController = new NoticeController();
                NoticeModel noticeModel = new NoticeModel(type, noticeTitle.getText(), downloadLinkField.getText(), contentArea.getText());
                System.out.println(noticeModel.getType());
                System.out.println(noticeModel.getTitle());
                System.out.println(noticeModel.getDownloadLink());
                System.out.println(noticeModel.getContent());
                try {
                    boolean affectedRows = NoticeController.getInstance().saveNotice(noticeModel);
                    if (affectedRows == true) {
                        noticeTableLoad();
                        noticeFieldClear();
                        JOptionPane.showMessageDialog(null, "notise saved sucessfully !");

                    } else {
                        JOptionPane.showMessageDialog(null, "notise save error !");
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
           
        }else{
            if (noticeTitle.getText().equals("") || downloadLinkField.getText().equals("") || contentArea.getText().equals("")||courseIdField.getText().equals("")||DateField.getDate()==null) {
                JOptionPane.showMessageDialog(null, "All fields must fill accourding to your selection type!");
            }else{
                //NoticeController noticeController = new ExamNoticeController();
            LocalTime timeFrom = LocalTime.of((int)hourSpinner.getValue(),(int)minuteSpinner.getValue());
            LocalTime timeTo = LocalTime.of((int)hourToSpinner.getValue(),(int)minuteToSpinner.getValue()) ;
            //System.out.println(timeFrom);
            NoticeModel noticeModel = new NoticeModel(type, noticeTitle.getText(), downloadLinkField.getText(), contentArea.getText(), courseIdField.getText(), DateField.getDate(), timeFrom, timeTo);
            try {
                boolean affectedRows = ExamNoticeController.getInstance().saveNotice(noticeModel);
                if(affectedRows==true){
                    noticeTableLoad();
                    noticeFieldClear();
                    JOptionPane.showMessageDialog(null, "notise saved sucessfully !");
                    
                }else{
                    JOptionPane.showMessageDialog(null, "notise save error !");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            }
             
        }
    }//GEN-LAST:event_saveBtn2ActionPerformed

    private void updateBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtn2ActionPerformed
        String type = typeCombo.getSelectedItem().toString();
        if (type.equals("General")) {
            if (noticeTitle.getText().equals("") || downloadLinkField.getText().equals("") || contentArea.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "All fields must fill accourding to your selection type!");
            } else {
                //NoticeController noticeController = new NoticeController();
                NoticeModel noticeModel = new NoticeModel(type, noticeTitle.getText(), downloadLinkField.getText(), contentArea.getText(), Integer.parseInt(idLabel.getText()));

                try {
                    boolean affectedRows = NoticeController.getInstance().updateNotice(noticeModel);
                    if (affectedRows == true) {
                        noticeTableLoad();
                        noticeFieldClear();
                        JOptionPane.showMessageDialog(null, "notise updated sucessfully !");

                    } else {
                        JOptionPane.showMessageDialog(null, "notise update error !");
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        } else {
            if (noticeTitle.getText().equals("") || downloadLinkField.getText().equals("") || contentArea.getText().equals("") || courseIdField.getText().equals("") || DateField.getDate()==null) {
                JOptionPane.showMessageDialog(null, "All fields must fill accourding to your selection type!");
            } else {
                //NoticeController noticeController = new ExamNoticeController();
                LocalTime timeFrom = LocalTime.of((int) hourSpinner.getValue(), (int) minuteSpinner.getValue());
                LocalTime timeTo = LocalTime.of((int) hourToSpinner.getValue(), (int) minuteToSpinner.getValue());

                NoticeModel noticeModel = new NoticeModel(type, noticeTitle.getText(), downloadLinkField.getText(), contentArea.getText(), courseIdField.getText(), DateField.getDate(), timeFrom, timeTo, Integer.parseInt(idLabel.getText()));
                try {
                    boolean affectedRows = ExamNoticeController.getInstance().updateNotice(noticeModel);
                    if (affectedRows == true) {
                        noticeTableLoad();
                        noticeFieldClear();
                        JOptionPane.showMessageDialog(null, "notise updated sucessfully !");

                    } else {
                        JOptionPane.showMessageDialog(null, "notise update error !");
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        }
    }//GEN-LAST:event_updateBtn2ActionPerformed

    private void deleteBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtn2ActionPerformed
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + idLabel.getText() + " ?");
        if (response == 0) {
            try {
                boolean affectedRows = NoticeController.getInstance().noticeDelete(idLabel.getText());
                if (affectedRows == true) {
                    noticeTableLoad();
                    noticeFieldClear();
                    JOptionPane.showMessageDialog(null, "Deleted sucessfully !");
                } else {
                    JOptionPane.showMessageDialog(null, "Delete error!");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

    }//GEN-LAST:event_deleteBtn2ActionPerformed

    private void courseClear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseClear1ActionPerformed
        noticeFieldClear();
    }//GEN-LAST:event_courseClear1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void noticeSearchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noticeSearchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noticeSearchBoxActionPerformed

    private void noticeSearchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noticeSearchBoxKeyReleased
        String type = typeCombo.getSelectedItem().toString();
        if(type.equals("General")){
            DefaultTableModel dtm = (DefaultTableModel)generalTable.getModel();
            try {
                NoticeController.getInstance().search(dtm,noticeSearchBox.getText());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            DefaultTableModel dtm = (DefaultTableModel)examTable.getModel();
            try {
                ExamNoticeController.getInstance().search(dtm,noticeSearchBox.getText());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_noticeSearchBoxKeyReleased

    private void examTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_examTableMouseReleased
        
        try {
            examNoticeTableToField();
        } catch (ParseException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_examTableMouseReleased

    private void generalTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalTableMouseReleased
        noticeTableToField();
    }//GEN-LAST:event_generalTableMouseReleased

    private void idLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_idLabelMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_idLabelMouseReleased

    private void timeTableIdLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeTableIdLabelMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_timeTableIdLabelMouseReleased

    private void TTTypeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TTTypeComboActionPerformed

        
    }//GEN-LAST:event_TTTypeComboActionPerformed

    private void TTDayComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TTDayComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TTDayComboActionPerformed

    private void saveBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtn3ActionPerformed
        if (TTCourseCodeField.getText().equals("") || TTDateField.getDate()==null) {
            JOptionPane.showMessageDialog(null, "All fields must fill");
        } else {
            TimeTableModel timeTableModel = new TimeTableModel(TTCourseCodeField.getText(), TTTypeCombo.getSelectedItem().toString(), TTDateField.getDate(), Day.valueOf(TTDayCombo.getSelectedItem().toString()), LocalTime.of((int) TTFromHoursSpinner.getValue(), (int) TTFromMinutesSpinner.getValue()), LocalTime.of((int) TTToHoursSpinner.getValue(), (int) TTToMinutesSpinner.getValue()));
            boolean rowsAffected;
            try {
                rowsAffected = TimeTableController.getInstance().save(timeTableModel);
                if (rowsAffected == true) {
                    timeTableLoad();
                    timeTableFieldClear();
                    JOptionPane.showMessageDialog(null, "Saved Sucessfully !");
                } else {
                    JOptionPane.showMessageDialog(null, "save error !");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }


    }//GEN-LAST:event_saveBtn3ActionPerformed

    private void updateBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtn3ActionPerformed
        if (TTCourseCodeField.getText().equals("") || TTDateField.getDate()==null) {
            JOptionPane.showMessageDialog(null, "All fields must fill");
        } else {
        TimeTableModel timeTableModel = new TimeTableModel(TTCourseCodeField.getText(),TTTypeCombo.getSelectedItem().toString(),TTDateField.getDate(),Day.valueOf(TTDayCombo.getSelectedItem().toString()),LocalTime.of((int)TTFromHoursSpinner.getValue(),(int)TTFromMinutesSpinner.getValue()),LocalTime.of((int)TTToHoursSpinner.getValue(), (int)TTToMinutesSpinner.getValue()));
        boolean rowsAffected;
        try {
            rowsAffected = TimeTableController.getInstance().update(timeTableModel,Integer.parseInt(timeTableIdLabel.getText()));
            if(rowsAffected==true){
                timeTableLoad();
            JOptionPane.showMessageDialog(null, "Updated Sucessfully !");
        }else{
            JOptionPane.showMessageDialog(null, "Update error !");
        }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        }
        
    }//GEN-LAST:event_updateBtn3ActionPerformed

    private void deleteBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtn3ActionPerformed
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + timeTableIdLabel.getText() + " ?");
        if (response == 0) {
            try {
                boolean affectedRows = TimeTableController.getInstance().delete(Integer.parseInt(timeTableIdLabel.getText()));
                if (affectedRows == true) {
                    timeTableFieldClear();
                    timeTableLoad();
                    JOptionPane.showMessageDialog(null, "Deleted sucessfully !");
                } else {
                    JOptionPane.showMessageDialog(null, "Delete error!");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_deleteBtn3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        timeTableFieldClear();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void timeTableSearchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeTableSearchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeTableSearchBoxActionPerformed

    private void timeTableSearchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_timeTableSearchBoxKeyReleased
         String text =  timeTableSearchBox.getText();
         DefaultTableModel dtm = (DefaultTableModel)timeTable.getModel();
        try {
            TimeTableController.getInstance().search(dtm,text);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_timeTableSearchBoxKeyReleased

    private void timeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeTableMouseClicked
        try {
            timeTableToField();
        } catch (ParseException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_timeTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AdminPanel().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateField;
    private javax.swing.JTextField TTCourseCodeField;
    private com.toedter.calendar.JDateChooser TTDateField;
    private javax.swing.JComboBox<String> TTDayCombo;
    private javax.swing.JSpinner TTFromHoursSpinner;
    private javax.swing.JSpinner TTFromMinutesSpinner;
    private javax.swing.JSpinner TTToHoursSpinner;
    private javax.swing.JSpinner TTToMinutesSpinner;
    private javax.swing.JComboBox<String> TTTypeCombo;
    private javax.swing.JTable accountTable;
    private javax.swing.JTextField contactBox;
    private java.awt.TextArea contentArea;
    private javax.swing.JButton courseClear;
    private javax.swing.JButton courseClear1;
    private javax.swing.JTextField courseCodeBox;
    private javax.swing.JTextField courseCreditBox;
    private javax.swing.JComboBox<String> courseDepartmentBox;
    private javax.swing.JTextField courseIdField;
    private javax.swing.JTextField courseNameBox;
    private javax.swing.JTextField courseSearchBox;
    private javax.swing.JTable courseTable;
    private javax.swing.JTextField courseYearBox;
    private javax.swing.JLabel courseidLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton deleteBtn1;
    private javax.swing.JButton deleteBtn2;
    private javax.swing.JButton deleteBtn3;
    private javax.swing.JComboBox<String> departmentCombo;
    private javax.swing.JComboBox<String> deptBox;
    private javax.swing.JTextField downloadLinkField;
    private javax.swing.JTextField emailBox;
    private javax.swing.JTable examTable;
    private javax.swing.JTable generalTable;
    private javax.swing.JSpinner hourSpinner;
    private javax.swing.JSpinner hourToSpinner;
    private javax.swing.JTextField idBox;
    private javax.swing.JLabel idLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lecNameBox;
    private javax.swing.JSpinner minuteSpinner;
    private javax.swing.JSpinner minuteToSpinner;
    private javax.swing.JTextField nameBox;
    private javax.swing.JTextField noticeSearchBox;
    private javax.swing.JTextField noticeTitle;
    private javax.swing.JTextField passwordBox;
    private javax.swing.JTextField practicalHoursBox;
    private javax.swing.JLabel practicalHoursLabel;
    private javax.swing.JComboBox<String> roleBox;
    private javax.swing.JComboBox<String> roleCombo;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton saveBtn1;
    private javax.swing.JButton saveBtn2;
    private javax.swing.JButton saveBtn3;
    private javax.swing.JTextField searchBox;
    private javax.swing.JRadioButton sem1;
    private javax.swing.JRadioButton sem2;
    private javax.swing.ButtonGroup semesterButtonGroup;
    private javax.swing.JTextField theoryHoursBox;
    private javax.swing.JLabel theoryHoursLabel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel timeLabel1;
    private javax.swing.JTable timeTable;
    private javax.swing.JLabel timeTableIdLabel;
    private javax.swing.JTextField timeTableSearchBox;
    private javax.swing.JLabel timeToLabel;
    private javax.swing.JLabel timeToLabel1;
    private javax.swing.JComboBox<String> typeBox;
    private javax.swing.JComboBox<String> typeCombo;
    private javax.swing.JButton updateBtn;
    private javax.swing.JButton updateBtn1;
    private javax.swing.JButton updateBtn2;
    private javax.swing.JButton updateBtn3;
    // End of variables declaration//GEN-END:variables

    private void eamNoticeTableToField() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}
