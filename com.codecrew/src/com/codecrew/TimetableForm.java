/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.codecrew;

import com.codecrew.TimetableService;

public class TimetableForm extends javax.swing.JFrame {

    public TimetableForm() {
        initComponents();
        loadAll();
        btnLecture.addActionListener(evt -> btnLectureActionPerformed(evt));
        btnExam.addActionListener(evt -> btnExamActionPerformed(evt));
        btnShowAll.addActionListener(evt -> btnShowAllActionPerformed(evt));
    }

    private void loadAll() {
        TimetableService ts = new TimetableService();
        tblTimetable.setModel(ts.getAllTimetable());
    }

    private void btnShowAllActionPerformed(java.awt.event.ActionEvent evt) {
        TimetableService ts = new TimetableService();
        tblTimetable.setModel(ts.getAllTimetable());
    }

    private void btnLectureActionPerformed(java.awt.event.ActionEvent evt) {
        TimetableService ts = new TimetableService();
        tblTimetable.setModel(ts.getByType("LECTURE"));
    }

    private void btnExamActionPerformed(java.awt.event.ActionEvent evt) {
        TimetableService ts = new TimetableService();
        tblTimetable.setModel(ts.getByType("EXAM"));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnLecture = new javax.swing.JButton();
        btnExam = new javax.swing.JButton();
        btnShowAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTimetable = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48));
        jLabel1.setForeground(new java.awt.Color(209, 32, 82));
        jLabel1.setText("TIME TABLE");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 440, 33));

        btnLecture.setBackground(new java.awt.Color(249, 122, 0));
        btnLecture.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnLecture.setForeground(new java.awt.Color(255, 255, 255));
        btnLecture.setText("Show lectures");
        getContentPane().add(btnLecture, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 160, 50));

        btnExam.setBackground(new java.awt.Color(249, 122, 0));
        btnExam.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnExam.setForeground(new java.awt.Color(255, 255, 255));
        btnExam.setText("Show exams");
        getContentPane().add(btnExam, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 160, 50));

        btnShowAll.setBackground(new java.awt.Color(249, 122, 0));
        btnShowAll.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnShowAll.setForeground(new java.awt.Color(255, 255, 255));
        btnShowAll.setText("Show all");
        getContentPane().add(btnShowAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 190, 160, 50));

        tblTimetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTimetable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 880, 210));

        btnBack.setBackground(new java.awt.Color(254, 209, 106));
        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnBack.setForeground(new java.awt.Color(51, 51, 51));
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 510, 140, 50));

        try {
            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/codecrew/fot-2.jpg")));
        } catch (NullPointerException e) {
            jLabel2.setBackground(new java.awt.Color(56, 102, 65));
            jLabel2.setOpaque(true);
        }
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 720));

        pack();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        LecturerDashboard dash = new LecturerDashboard();
        dash.setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TimetableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimetableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimetableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimetableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimetableForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExam;
    private javax.swing.JButton btnLecture;
    private javax.swing.JButton btnShowAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTimetable;
    // End of variables declaration//GEN-END:variables
}
