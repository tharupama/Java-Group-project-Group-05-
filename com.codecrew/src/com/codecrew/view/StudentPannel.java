/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.codecrew.view;

import com.codecrew.DBConnection;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author USER
 */
public class StudentPannel extends javax.swing.JFrame {
    private String studentId;
    private String studentName;
    private long contact;
    private String email;
    private byte[] profileImg;
    String fileName = null;
    private String department;
    

    /**
     * Creates new form StudentPannel
     */
    
    public StudentPannel(String studentId, String studentName, long contact, String email, byte[] profileImg, String department) throws ClassNotFoundException, SQLException {
        this.studentId = studentId;
        this.studentName = studentName;
        this.contact = contact;
        this.email = email;
        this.profileImg = profileImg;
        this.department = department;
        initComponents();
         IdLabel.setText(this.studentId);
         nameLabel.setText(this.studentName);
         contactBox.setText(String.valueOf(this.contact));
         emailBox.setText(this.email);
         
         byte[] poto = getImageById(this.studentId);

        if (poto != null && poto.length > 0) {
            ImageIcon icon = new ImageIcon(poto);

            // Safely handle case where label isn't laid out yet (getWidth/Height = 0)
            int w = imageLabel.getWidth() > 0 ? imageLabel.getWidth() : icon.getIconWidth();
            int h = imageLabel.getHeight() > 0 ? imageLabel.getHeight() : icon.getIconHeight();

            Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setIcon(null); // or set a default placeholder: new ImageIcon("default_avatar.png")
        }
        
        attendanceTableLoad();
        medicalTableLoad();
        courseTableLoad();
        gradeAndGpaTableLoad();
        timeTableLoad();
        generalNoticeTableLoad();
        examTimeTableLoad();
         
    }
    
    private void examTimeTableLoad() throws SQLException{
        String sql = "SELECT created_at,updated_at,title,download_link,content,course_id,exam_date,time_from,time_to from notice WHERE type = ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, "Exam");
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel examNTable = (DefaultTableModel)examNoticeTable.getModel();
        examNTable.setRowCount(0);
        while (rs.next()) {

            examNTable.addRow(new Object[]{rs.getString("created_at"),rs.getString("updated_at"),rs.getString("title"),rs.getString("download_link"),rs.getString("content"),rs.getString("course_id"),String.valueOf(rs.getDate("exam_date")),String.valueOf(rs.getTime("time_from")),String.valueOf(rs.getTime("time_to"))});
        }
        conn.close();
        pst.close();
    }
    
    private void generalNoticeTableLoad() throws SQLException{
        String sql = "SELECT created_at,updated_at,title,download_link,content from notice WHERE type = ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, "General");
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel gNTable = (DefaultTableModel)generalNoticeTable.getModel();
        gNTable.setRowCount(0);
        while (rs.next()) {

            gNTable.addRow(new Object[]{rs.getString("created_at"),rs.getString("updated_at"),rs.getString("title"),rs.getString("download_link"),rs.getString("content")});
        }
        conn.close();
        pst.close();
    }
    
    private void timeTableLoad() throws SQLException{
        String sql = "SELECT t.Course_code,t.Type,t.date, t.Day, t.time_from, t.time_to, t.venue FROM time_table t INNER JOIN course_unit c ON t.Course_code=c.Course_code WHERE c.Department_Offering = ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, department);
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel tTable = (DefaultTableModel)timeTable.getModel();
        tTable.setRowCount(0);
        while (rs.next()) {

            tTable.addRow(new Object[]{rs.getString("Course_code"),rs.getString("Type"),String.valueOf(rs.getDate("date")),rs.getString("Day"), String.valueOf(rs.getTime("time_from")),String.valueOf(rs.getTime("time_to")),rs.getString("venue")});
        }
        conn.close();
        pst.close();
    }
    
    private void gradeAndGpaTableLoad() throws SQLException{
        String sql = "SELECT course_code,Semester,CA_marks, Final_Marks,Final_Grade,Result_Status,SGPA,CGPA FROM student_result WHERE student_id = ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.studentId);
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel gTable = (DefaultTableModel)gradeTable.getModel();
        gTable.setRowCount(0);
        while (rs.next()) {

            gTable.addRow(new Object[]{rs.getString("course_code"),rs.getString("Semester"),String.valueOf(rs.getDouble("CA_marks")),String.valueOf(rs.getDouble("Final_Marks")),rs.getString("Final_Grade"),rs.getString("Result_Status"),String.valueOf(rs.getDouble("SGPA")),String.valueOf(rs.getDouble("CGPA"))});
        }
        conn.close();
        pst.close();
    }
    
    private void courseTableLoad() throws SQLException{
        String sql = "SELECT Course_code,Enrolled_Date,Official_Confirmation, Semester FROM course_enrollment WHERE ST_Id = ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.studentId);
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel cTable = (DefaultTableModel)courseTable.getModel();
        cTable.setRowCount(0);
        while (rs.next()) {

            cTable.addRow(new Object[]{rs.getString("Course_code"),String.valueOf(rs.getDate("Enrolled_Date")),rs.getString("Official_Confirmation"),rs.getString("Semester")});
        }
        conn.close();
        pst.close();
    }
    
    private void medicalTableLoad() throws SQLException{
        String sql = "SELECT Medical_Id, Course_code, Date_Submit, Valid_From, Valid_To,Approve,Approved_By FROM medical_record WHERE ST_Id = ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.studentId);
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel medTable = (DefaultTableModel)medicalTable.getModel();
        medTable.setRowCount(0);
        while (rs.next()) {

            int approve = rs.getInt("Approve"); // or rs.getByte("Approve")
            String approveStatus;

            switch (approve) {
                case 0:
                    approveStatus = "Pending";
                    break;
                case 1:
                    approveStatus = "Approved";
                    break;
                case 2:
                    approveStatus = "Rejected";
                    break; // if you use more states
                default:
                    approveStatus = "Unknown";
            }

            medTable.addRow(new Object[]{rs.getString("Medical_Id"), rs.getString("Course_code"), String.valueOf(rs.getDate("Date_Submit")), String.valueOf(rs.getDate("Valid_From")), String.valueOf(rs.getDate("Valid_To")), approveStatus, rs.getString("Approved_By")});
        }
        conn.close();
        pst.close();
    }
    //polimopisom without parameeter
    private void attendanceTableLoad() throws SQLException {
        
        String sql = "SELECT Attendance_Id, Course_code, Session_Date, Week_Number, Session_Type,Status FROM attendance WHERE ST_Id = ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.studentId);
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel attTable = (DefaultTableModel)attendanceTable.getModel();
        attTable.setRowCount(0);
        while(rs.next()){
            attTable.addRow(new Object[]{rs.getString("Attendance_Id"),rs.getString("Course_code"),String.valueOf(rs.getDate("Session_Date")),String.valueOf(rs.getInt("Week_Number")), rs.getString("Session_Type"), rs.getString("Status")});
        }
        conn.close();
        pst.close();
   }
    //polimopisom with parameeter
    private void attendanceTableLoad(String text) throws SQLException {
        
        String sql = "SELECT Attendance_Id, Course_code, Session_Date, Week_Number, Session_Type,Status FROM attendance WHERE ST_Id = ? AND Course_code LIKE ?";
        DBConnection con = new DBConnection();
        Connection conn = con.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.studentId);
        pst.setString(2,"%"+text+"%");
        
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel attTable = (DefaultTableModel)attendanceTable.getModel();
        attTable.setRowCount(0);
        while(rs.next()){
            attTable.addRow(new Object[]{rs.getString("Attendance_Id"),rs.getString("Course_code"),String.valueOf(rs.getDate("Session_Date")),String.valueOf(rs.getInt("Week_Number")), rs.getString("Session_Type"), rs.getString("Status")});
        }
        conn.close();
        pst.close();
   }
    
       public byte[] getImageById(String id) throws ClassNotFoundException, SQLException {
    Connection conn = DbConnection.getInstance().getConn();
    String sql = "SELECT image_data FROM user WHERE U_Id = ?";
    
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setString(1, id); // 🔑 1. Bind the ID parameter to the "?"
        
        try (ResultSet rst = pst.executeQuery()) {
            if (rst.next()) { // 🔑 2. Move cursor to the first result row
                return rst.getBytes("image_data"); // ✅ Returns byte[] or null if column is NULL
            }
        }
    }
    
    
    return null; // No matching record or image column is empty
}

    public StudentPannel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        IdLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        contactBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        emailBox = new javax.swing.JTextField();
        imageLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        attendanceTable = new javax.swing.JTable();
        attSearch = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        medicalTable = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        courseTable = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        gradeTable = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        timeTable = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        generalNoticeTable = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        examNoticeTable = new javax.swing.JTable();
        noticeCombo = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        IdLabel.setBackground(new java.awt.Color(255, 0, 0));
        IdLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        IdLabel.setForeground(new java.awt.Color(0, 0, 0));
        IdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IdLabel.setOpaque(true);
        jPanel1.add(IdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 270, 40));

        jLabel4.setBackground(new java.awt.Color(255, 255, 51));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Id");
        jLabel4.setOpaque(true);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 230, 40));

        jLabel5.setBackground(new java.awt.Color(255, 255, 51));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Name");
        jLabel5.setOpaque(true);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 230, 40));

        nameLabel.setBackground(new java.awt.Color(255, 0, 0));
        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(0, 0, 0));
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLabel.setOpaque(true);
        jPanel1.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 270, 40));

        jLabel7.setBackground(new java.awt.Color(255, 255, 51));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Contact");
        jLabel7.setOpaque(true);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 230, 40));

        contactBox.setBackground(new java.awt.Color(255, 0, 51));
        contactBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        contactBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactBoxActionPerformed(evt);
            }
        });
        jPanel1.add(contactBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, 270, 40));

        jLabel8.setBackground(new java.awt.Color(255, 255, 51));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Email");
        jLabel8.setOpaque(true);
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 230, 40));

        emailBox.setBackground(new java.awt.Color(255, 0, 51));
        emailBox.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        emailBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailBoxActionPerformed(evt);
            }
        });
        jPanel1.add(emailBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 270, 40));

        imageLabel.setBackground(new java.awt.Color(255, 0, 51));
        imageLabel.setForeground(new java.awt.Color(0, 0, 0));
        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setText("Image");
        imageLabel.setOpaque(true);
        jPanel1.add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 90, 250, 350));

        jButton1.setBackground(new java.awt.Color(0, 153, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Change profile Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 380, 300, 60));

        jButton2.setBackground(new java.awt.Color(102, 255, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 560, 370, 80));

        jLabel13.setBackground(new java.awt.Color(204, 204, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Profile Details");
        jLabel13.setOpaque(true);
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/view/studentsImage.jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 670));

        jTabbedPane1.addTab("Profile Details", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        attendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "attendanceId", "courseId", "date", "weekNo", "type", "status"
            }
        ));
        jScrollPane1.setViewportView(attendanceTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1300, 560));

        attSearch.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        attSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                attSearchKeyReleased(evt);
            }
        });
        jPanel2.add(attSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 50, 350, 40));

        jLabel12.setBackground(new java.awt.Color(255, 0, 51));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Search");
        jLabel12.setOpaque(true);
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1203, 20, 90, 30));

        jLabel14.setBackground(new java.awt.Color(204, 204, 0));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Attendance");
        jLabel14.setOpaque(true);
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/view/studentsImage.jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 670));

        jTabbedPane1.addTab("Attendance", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        medicalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "medicalId", "courseId", "dateSubmit", "validFrom", "validTo", "approve", "approvedBy"
            }
        ));
        jScrollPane2.setViewportView(medicalTable);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1300, 570));

        jLabel15.setBackground(new java.awt.Color(204, 204, 0));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Mecical Details");
        jLabel15.setOpaque(true);
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/view/studentsImage.jpg"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 660));

        jTabbedPane1.addTab("Medical", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        courseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "courseCode", "enrollerDate", "confirmation", "semester"
            }
        ));
        jScrollPane3.setViewportView(courseTable);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1300, 580));

        jLabel16.setBackground(new java.awt.Color(204, 204, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Course Details");
        jLabel16.setOpaque(true);
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, 50));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/view/studentsImage.jpg"))); // NOI18N
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 660));

        jTabbedPane1.addTab("Course Details", jPanel4);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gradeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "courseCode", "semester", "caMarks", "finalMarks", "finalGrade", "rseultStatus", "sgpa", "cgpa"
            }
        ));
        jScrollPane4.setViewportView(gradeTable);

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1300, 580));

        jLabel17.setBackground(new java.awt.Color(204, 204, 0));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Grades And Gpa");
        jLabel17.setOpaque(true);
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, 50));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/view/studentsImage.jpg"))); // NOI18N
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 670));

        jTabbedPane1.addTab("Grades and Gpa", jPanel5);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        timeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "courseCode", "type", "date", "day", "timeFrom", "timeTo", "venue"
            }
        ));
        jScrollPane5.setViewportView(timeTable);

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1300, 580));

        jLabel18.setBackground(new java.awt.Color(204, 204, 0));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Time Table");
        jLabel18.setOpaque(true);
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, 50));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/view/studentsImage.jpg"))); // NOI18N
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 660));

        jTabbedPane1.addTab("Time Tables", jPanel6);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        generalNoticeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "createdAt", "updatedAt", "title", "downloadLink", "content"
            }
        ));
        jScrollPane6.setViewportView(generalNoticeTable);

        jPanel7.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1300, 580));

        examNoticeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "createdAt", "updateAt", "title", "downloadLink", "content", "courseId", "examDate", "timeFrom", "timeTo"
            }
        ));
        jScrollPane7.setViewportView(examNoticeTable);

        jPanel7.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1300, 580));

        noticeCombo.setBackground(new java.awt.Color(255, 0, 0));
        noticeCombo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        noticeCombo.setForeground(new java.awt.Color(0, 0, 0));
        noticeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "General", "Exam" }));
        noticeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noticeComboActionPerformed(evt);
            }
        });
        jPanel7.add(noticeCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1022, 12, 260, 40));

        jLabel19.setBackground(new java.awt.Color(204, 204, 0));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Notice");
        jLabel19.setOpaque(true);
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, 50));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/view/studentsImage.jpg"))); // NOI18N
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 670));

        jTabbedPane1.addTab("Notice", jPanel7);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void contactBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactBoxActionPerformed

    private void emailBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        fileName = f.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH));
        imageLabel.setIcon(imageIcon);
        
        try{
            File image = new File(fileName);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum;(readNum=fis.read(buf))!=-1;){
                bos.write(buf,0,readNum);
            }
            profileImg = bos.toByteArray();
             
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        StudentProfileController spc = new StudentProfileController();
        try {
            // this is abstraction geting into controller 
            StudentProfileEncapsulated studentProfileEncapsulated = new StudentProfileEncapsulated(contactBox.getText(),emailBox.getText(),profileImg,IdLabel.getText());
            //spc.updateStudent(contactBox.getText(),emailBox.getText(),profileImg,IdLabel.getText());
            spc.updateStudent(studentProfileEncapsulated);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentPannel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentPannel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void noticeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noticeComboActionPerformed
        if(noticeCombo.getSelectedIndex()==0){
            jScrollPane6.setVisible(true);
            jScrollPane7.setVisible(false);
        }else{
           jScrollPane6.setVisible(false);
           jScrollPane7.setVisible(true);

        }
    }//GEN-LAST:event_noticeComboActionPerformed

    private void attSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_attSearchKeyReleased
        String text = attSearch.getText();
        try {
            attendanceTableLoad(text);
        } catch (SQLException ex) {
            Logger.getLogger(StudentPannel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_attSearchKeyReleased

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
            java.util.logging.Logger.getLogger(StudentPannel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentPannel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentPannel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentPannel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentPannel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IdLabel;
    private javax.swing.JTextField attSearch;
    private javax.swing.JTable attendanceTable;
    private javax.swing.JTextField contactBox;
    private javax.swing.JTable courseTable;
    private javax.swing.JTextField emailBox;
    private javax.swing.JTable examNoticeTable;
    private javax.swing.JTable generalNoticeTable;
    private javax.swing.JTable gradeTable;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable medicalTable;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JComboBox<String> noticeCombo;
    private javax.swing.JTable timeTable;
    // End of variables declaration//GEN-END:variables

    
}
