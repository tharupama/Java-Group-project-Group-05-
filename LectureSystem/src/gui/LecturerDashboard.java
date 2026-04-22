/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Lecturer;
import service.LecturerProfileService;
import service.LecturerSession;

/**
 *
 * @author HP
 */
public class LecturerDashboard extends javax.swing.JFrame {

    private String loggedUserId;
    private String loggedUserName;
    private String loggedUserEmail;
    private String loggedUserDepartment;
    private JLabel lblLoggedUser;
    private javax.swing.JButton btnUpdateProfile;
    private javax.swing.JButton btnLogout;

    /**
     * Creates new form LecturerDashboard
     */
    public LecturerDashboard() {
        loadSessionData();
        initComponents();
        setupLoggedUserSection();
    }

    public LecturerDashboard(Lecturer lecturer) {
        this(
            lecturer.getId(),
            lecturer.getName(),
            lecturer.getEmail(),
            lecturer.getDepartment()
        );
    }

    public LecturerDashboard(String userId, String userName, String email, String department) {
        this.loggedUserId = userId;
        this.loggedUserName = userName;
        this.loggedUserEmail = email;
        this.loggedUserDepartment = department;
        LecturerSession.setSession(userId, userName, email, department);
        initComponents();
        setupLoggedUserSection();
    }

    private void loadSessionData() {
        if (LecturerSession.hasSession()) {
            loggedUserId = LecturerSession.getUserId();
            loggedUserName = LecturerSession.getUserName();
            loggedUserEmail = LecturerSession.getEmail();
            loggedUserDepartment = LecturerSession.getDepartment();
        }
    }

    private void setupLoggedUserSection() {
        lblLoggedUser = new JLabel();
        lblLoggedUser.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lblLoggedUser.setForeground(new java.awt.Color(255, 255, 255));
        lblLoggedUser.setOpaque(true);
        lblLoggedUser.setBackground(new java.awt.Color(0, 0, 0, 160));
        refreshLoggedUserLabel();
        getContentPane().add(lblLoggedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 530, 300, 40));

        btnUpdateProfile = new javax.swing.JButton("UPDATE PROFILE");
        btnUpdateProfile.setBackground(new java.awt.Color(249, 122, 0));
        btnUpdateProfile.setFont(new java.awt.Font("Segoe UI", 1, 16));
        btnUpdateProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateProfile.setBorderPainted(false);
        btnUpdateProfile.addActionListener((java.awt.event.ActionEvent evt) -> {
            btnUpdateProfileActionPerformed(evt);
        });
        getContentPane().add(btnUpdateProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 580, 300, 45));

        btnLogout = new javax.swing.JButton("LOGOUT");
        btnLogout.setBackground(new java.awt.Color(254, 209, 106));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnLogout.setForeground(new java.awt.Color(51, 51, 51));
        btnLogout.addActionListener((java.awt.event.ActionEvent evt) -> {
            btnLogoutActionPerformed(evt);
        });
        getContentPane().add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 110, 35));

        // Keep dynamic components above background label in AbsoluteLayout.
        getContentPane().setComponentZOrder(lblLoggedUser, 0);
        getContentPane().setComponentZOrder(btnUpdateProfile, 0);
        getContentPane().setComponentZOrder(btnLogout, 0);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        LecturerSession.clear();
        codecrew.view.login loginForm = new codecrew.view.login();
        loginForm.setVisible(true);
        this.dispose();
    }

    private void refreshLoggedUserLabel() {
        if (loggedUserName == null || loggedUserName.trim().isEmpty()) {
            lblLoggedUser.setText("Logged User: Lecturer");
        } else {
            lblLoggedUser.setText("Logged User: " + loggedUserName);
        }
    }

    private void btnUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {
        if (loggedUserId == null || loggedUserId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Logged user id not found.");
            return;
        }

        JTextField txtName = new JTextField(loggedUserName == null ? "" : loggedUserName);
        JTextField txtEmail = new JTextField(loggedUserEmail == null ? "" : loggedUserEmail);
        JTextField txtDepartment = new JTextField(loggedUserDepartment == null ? "" : loggedUserDepartment);

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Name"));
        panel.add(txtName);
        panel.add(new JLabel("Email"));
        panel.add(txtEmail);
        panel.add(new JLabel("Department"));
        panel.add(txtDepartment);

        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Update My Profile",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String newName = txtName.getText().trim();
        String newEmail = txtEmail.getText().trim();
        String newDepartment = txtDepartment.getText().trim();

        if (newName.isEmpty() || newEmail.isEmpty() || newDepartment.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        LecturerProfileService profileService = new LecturerProfileService();
        boolean updated = profileService.updateLoggedLecturer(loggedUserId, newName, newEmail, newDepartment);

        if (updated) {
            loggedUserName = newName;
            loggedUserEmail = newEmail;
            loggedUserDepartment = newDepartment;
            LecturerSession.setSession(loggedUserId, loggedUserName, loggedUserEmail, loggedUserDepartment);
            refreshLoggedUserLabel();
            JOptionPane.showMessageDialog(this, "Profile updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Profile update failed.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnAddMarks = new javax.swing.JButton();
        btnViewMarks = new javax.swing.JButton();
        btnViewMarks1 = new javax.swing.JButton();
        Notice = new javax.swing.JButton();
        Notice1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(196, 74, 58));
        jLabel1.setText("LECTURER DASHBOARD");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 560, 101));

        btnAddMarks.setBackground(new java.awt.Color(249, 122, 0));
        btnAddMarks.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAddMarks.setForeground(new java.awt.Color(255, 255, 255));
        btnAddMarks.setText("ADD MARKS");
        btnAddMarks.setBorderPainted(false);
        btnAddMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMarksActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddMarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 254, 215, 75));

        btnViewMarks.setBackground(new java.awt.Color(249, 122, 0));
        btnViewMarks.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnViewMarks.setForeground(new java.awt.Color(255, 255, 255));
        btnViewMarks.setText("VIEW MARKS");
        btnViewMarks.setBorderPainted(false);
        btnViewMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewMarksActionPerformed(evt);
            }
        });
        getContentPane().add(btnViewMarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(282, 254, 215, 75));

        btnViewMarks1.setBackground(new java.awt.Color(249, 122, 0));
        btnViewMarks1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnViewMarks1.setForeground(new java.awt.Color(255, 255, 255));
        btnViewMarks1.setText("VIEW GPA");
        btnViewMarks1.setBorderPainted(false);
        btnViewMarks1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewMarks1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnViewMarks1, new org.netbeans.lib.awtextra.AbsoluteConstraints(551, 254, 215, 75));

        Notice.setBackground(new java.awt.Color(249, 122, 0));
        Notice.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Notice.setForeground(new java.awt.Color(255, 255, 255));
        Notice.setText("NOTICE ");
        Notice.setBorderPainted(false);
        Notice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoticeActionPerformed(evt);
            }
        });
        getContentPane().add(Notice, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 254, 215, 75));

        Notice1.setBackground(new java.awt.Color(249, 122, 0));
        Notice1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Notice1.setForeground(new java.awt.Color(255, 255, 255));
        Notice1.setText("VIEW TIMETABLE");
        Notice1.setBorderPainted(false);
        Notice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Notice1ActionPerformed(evt);
            }
        });
        getContentPane().add(Notice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 240, 75));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/fot-2.jpg"))); // NOI18N
        jLabel2.setToolTipText("");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddMarksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMarksActionPerformed
        UploadMarksForm upload = new UploadMarksForm();
        upload.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAddMarksActionPerformed

    private void btnViewMarksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewMarksActionPerformed
        ViewMarksForm view = new ViewMarksForm();
        view.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnViewMarksActionPerformed

    private void btnViewMarks1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewMarks1ActionPerformed
        ViewGPAForm gpa=new ViewGPAForm();
        gpa.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnViewMarks1ActionPerformed

    private void NoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoticeActionPerformed
        NoticeForm notice=new NoticeForm();
        notice.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_NoticeActionPerformed

    private void Notice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Notice1ActionPerformed
    TimetableForm tt = new TimetableForm();
    tt.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_Notice1ActionPerformed

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
            java.util.logging.Logger.getLogger(LecturerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LecturerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LecturerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LecturerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LecturerDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Notice;
    private javax.swing.JButton Notice1;
    private javax.swing.JButton btnAddMarks;
    private javax.swing.JButton btnViewMarks;
    private javax.swing.JButton btnViewMarks1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
