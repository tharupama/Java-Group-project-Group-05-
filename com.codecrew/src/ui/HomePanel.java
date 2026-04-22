package com.codecrew.ui;

import com.codecrew.model.User;
import com.codecrew.util.DBConnection;
import com.codecrew.util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

/**
 * HomePanel - Summary dashboard with stat cards
 */
public class HomePanel extends JPanel {

    private final User user;

    public HomePanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(0, 20));
        setBackground(UIHelper.BG);
        build();
    }

    private void build() {
        // Header
        JLabel header = UIHelper.headerLabel("Welcome, " + user.getFname() + " 👋");
        header.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Stats row
        JPanel stats = new JPanel(new GridLayout(1, 4, 16, 0));
        stats.setOpaque(false);

        int[] counts = fetchCounts();
        stats.add(statCard("Total Students", String.valueOf(counts[0]), "👩‍🎓", new Color(59, 130, 246)));
        stats.add(statCard("Courses",        String.valueOf(counts[1]), "📚", new Color(16, 185, 129)));
        stats.add(statCard("Users",          String.valueOf(counts[2]), "👥", new Color(245, 158, 11)));
        stats.add(statCard("Departments",    "3",                        "🏫", new Color(139, 92, 246)));

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);
        center.add(stats, BorderLayout.NORTH);

        // Info card
        JPanel info = buildInfoCard();
        center.add(info, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JPanel statCard(String label, String value, String emoji, Color color) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, 6, getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(0, 110));
        card.setBorder(new EmptyBorder(16, 20, 16, 16));

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        JLabel emojiLbl = new JLabel(emoji);
        emojiLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        valLbl.setForeground(color);

        JLabel nameLbl = new JLabel(label);
        nameLbl.setFont(UIHelper.FONT_BODY);
        nameLbl.setForeground(UIHelper.TEXT_MED);

        inner.add(emojiLbl);
        inner.add(Box.createVerticalStrut(4));
        inner.add(valLbl);
        inner.add(nameLbl);

        card.add(inner);
        return card;
    }

    private JPanel buildInfoCard() {
        JPanel card = UIHelper.card("System Information");
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIHelper.BORDER, 1, true),
            new EmptyBorder(16, 20, 16, 20)
        ));

        JPanel content = new JPanel(new GridLayout(0, 2, 10, 8));
        content.setOpaque(false);

        addRow(content, "Logged in as:",    user.getFullName());
        addRow(content, "User ID:",         user.getuId());
        addRow(content, "Role:",            user.getRole());
        addRow(content, "Department:",      user.getDepartment());
        addRow(content, "Email:",           user.getEmail() != null ? user.getEmail() : "—");
        addRow(content, "Academic Year:",   "2024/2025");
        addRow(content, "Semester:",        "Semester 2");
        addRow(content, "System Version:",  "1.0 (CodeCrew)");

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private void addRow(JPanel p, String label, String value) {
        JLabel l = UIHelper.fieldLabel(label);
        JLabel v = new JLabel(value);
        v.setFont(UIHelper.FONT_BODY);
        v.setForeground(UIHelper.TEXT_DARK);
        p.add(l);
        p.add(v);
    }

    private int[] fetchCounts() {
        int[] c = {0, 0, 0};
        String[] sqls = {
            "SELECT COUNT(*) FROM student",
            "SELECT COUNT(*) FROM course_unit",
            "SELECT COUNT(*) FROM user"
        };
        try (Connection con = DBConnection.getConnection()) {
            for (int i = 0; i < sqls.length; i++) {
                try (PreparedStatement ps = con.prepareStatement(sqls[i]);
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) c[i] = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("fetchCounts error: " + e.getMessage());
        }
        return c;
    }
}
