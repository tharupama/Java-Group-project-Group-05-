package com.codecrew.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * UIHelper - Consistent styling across all forms
 * FOT Student Management System
 */
public class UIHelper {

    // Color Palette
    public static final Color PRIMARY      = new Color(30, 58, 138);   // Deep Blue
    public static final Color PRIMARY_LIGHT= new Color(59, 130, 246);  // Blue
    public static final Color ACCENT       = new Color(16, 185, 129);  // Emerald
    public static final Color DANGER       = new Color(239, 68, 68);   // Red
    public static final Color WARNING      = new Color(245, 158, 11);  // Amber
    public static final Color BG           = new Color(241, 245, 249); // Light Gray
    public static final Color CARD         = Color.WHITE;
    public static final Color TEXT_DARK    = new Color(15, 23, 42);
    public static final Color TEXT_MED     = new Color(71, 85, 105);
    public static final Color TABLE_HDR    = new Color(30, 58, 138);
    public static final Color TABLE_ALT    = new Color(248, 250, 252);
    public static final Color BORDER       = new Color(203, 213, 225);

    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_MONO   = new Font("Consolas", Font.PLAIN, 13);

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFont(FONT_HEADER);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(160, 38));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(PRIMARY_LIGHT); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setBackground(PRIMARY); }
        });
        return b;
    }

    public static JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(DANGER);
        b.setForeground(Color.WHITE);
        b.setFont(FONT_HEADER);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 38));
        return b;
    }

    public static JButton successButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFont(FONT_HEADER);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 38));
        return b;
    }

    public static JTextField styledField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BODY);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        tf.setPreferredSize(new Dimension(200, 36));
        return tf;
    }

    public static JPasswordField styledPasswordField() {
        JPasswordField pf = new JPasswordField();
        pf.setFont(FONT_BODY);
        pf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        pf.setPreferredSize(new Dimension(200, 36));
        return pf;
    }

    public static JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY);
        cb.setBackground(Color.WHITE);
        cb.setPreferredSize(new Dimension(200, 36));
        return cb;
    }

    public static JLabel headerLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(PRIMARY);
        return l;
    }

    public static JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_MED);
        return l;
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT_DARK);
        table.setBackground(CARD);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HDR);
        header.setForeground(Color.WHITE);
        header.setFont(FONT_HEADER);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        // Alternate row coloring
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                if (!sel) setBackground(row % 2 == 0 ? CARD : TABLE_ALT);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    public static JPanel card(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));
        if (title != null && !title.isEmpty()) {
            JLabel lbl = new JLabel(title);
            lbl.setFont(FONT_HEADER);
            lbl.setForeground(PRIMARY);
            lbl.setBorder(new EmptyBorder(0, 0, 12, 0));
            p.add(lbl, BorderLayout.NORTH);
        }
        return p;
    }

    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    /** Show a success toast-like message */
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /** Show an error message */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /** Confirm dialog */
    public static boolean confirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
