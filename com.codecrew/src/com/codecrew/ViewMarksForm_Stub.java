package com.codecrew;

import javax.swing.JOptionPane;

public class ViewMarksForm_Stub extends javax.swing.JFrame {
    public ViewMarksForm_Stub() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(null, "View Marks Feature\n\nThis feature displays student marks.");
        new LecturerDashboard().setVisible(true);
        this.dispose();
    }
}
