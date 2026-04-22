package com.codecrew;

import com.codecrew.ui.LoginForm;

import javax.swing.*;

/**
 * ComCodecrew - Main entry point
 * FOT Student Management System
 * University of Ruhuna - Faculty of Technology
 * 
 * Project: com.codecrew (Group 05 - CodeCrew)
 * Database: student_management (Fot.sql)
 */
public class ComCodecrew {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            new LoginForm().setVisible(true);
        });
    }
}
