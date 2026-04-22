package com.codecrew.view;

import com.codecrew.model.User;
import com.codecrew.util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * StudentDashboardUI - Modern student dashboard interface
 * Part of the view layer for FOT Student Management System
 */
public class StudentDashboardUI extends JFrame {

    private User currentUser;
    private JPanel contentPanel;
    private JLabel lblPageTitle;
    private String currentPage = "Home";

    public StudentDashboardUI(User user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("FOT Student Portal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main background
        getContentPane().setBackground(UIHelper.BG);

        // Top navigation bar
        add(buildTopBar(), BorderLayout.NORTH);

        // Left sidebar
        add(buildSidebar(), BorderLayout.WEST);

        // Main content area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIHelper.BG);
        contentPanel.setBorder(new EmptyBorder(20, 24, 20, 24));
        add(contentPanel, BorderLayout.CENTER);

        // Show home by default
        showHome();
    }

    // ─── Top Bar ─────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout(0, 0));
        bar.setBackground(Color.WHITE);
        bar.setPreferredSize(new Dimension(0, 64));
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIHelper.BORDER, 1),
            new EmptyBorder(0, 24, 0, 24)
        ));

        // Logo/Title section
        JPanel leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        leftSection.setOpaque(false);

        JLabel logo = new JLabel("🎓");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JLabel title = new JLabel("FOT Student Portal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(UIHelper.PRIMARY);

        leftSection.add(logo);
        leftSection.add(title);

        // User section (right)
        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        rightSection.setOpaque(false);

        // User avatar circle
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIHelper.PRIMARY);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatarPanel.setPreferredSize(new Dimension(40, 40));
        avatarPanel.setLayout(new BorderLayout());
        avatarPanel.setOpaque(false);

        JLabel initials = new JLabel(getInitials());
        initials.setFont(new Font("Segoe UI", Font.BOLD, 14));
        initials.setForeground(Color.WHITE);
        initials.setHorizontalAlignment(SwingConstants.CENTER);
        avatarPanel.add(initials, BorderLayout.CENTER);

        // User name and role
        JPanel userInfo = new JPanel();
        userInfo.setOpaque(false);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        userInfo.setAlignmentY(CENTER_ALIGNMENT);

        JLabel userName = new JLabel(currentUser.getFullName());
        userName.setFont(UIHelper.FONT_HEADER);
        userName.setForeground(UIHelper.TEXT_DARK);

        JLabel userRole = new JLabel("Student");
        userRole.setFont(UIHelper.FONT_SMALL);
        userRole.setForeground(UIHelper.TEXT_MED);

        userInfo.add(userName);
        userInfo.add(userRole);

        // Logout button
        JButton btnLogout = createNavButton("Logout", new Color(239, 68, 68));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new com.codecrew.ui.LoginForm().setVisible(true);
            }
        });

        rightSection.add(avatarPanel);
        rightSection.add(userInfo);
        rightSection.add(Box.createHorizontalStrut(10));
        rightSection.add(btnLogout);

        bar.add(leftSection, BorderLayout.WEST);
        bar.add(rightSection, BorderLayout.EAST);

        return bar;
    }

    // ─── Sidebar ─────────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIHelper.BORDER, 1),
            new EmptyBorder(20, 0, 20, 0)
        ));

        // User card at top
        JPanel userCard = createUserCard();
        sidebar.add(userCard);
        sidebar.add(Box.createVerticalStrut(20));

        // Navigation items
        JPanel navSection = new JPanel();
        navSection.setLayout(new BoxLayout(navSection, BoxLayout.Y_AXIS));
        navSection.setOpaque(false);

        addNavItem(navSection, "🏠", "Home", "Dashboard overview", () -> showHome());
        addNavItem(navSection, "📊", "My Results", "View grades and GPA", () -> showResults());
        addNavItem(navSection, "📅", "Attendance", "Track attendance", () -> showAttendance());
        addNavItem(navSection, "📚", "My Courses", "Enrolled courses", () -> showCourses());
        addNavItem(navSection, "👤", "Profile", "My profile info", () -> showProfile());

        sidebar.add(navSection);
        sidebar.add(Box.createVerticalGlue());

        // Version info
        JLabel version = new JLabel("v1.0 • CodeCrew Team");
        version.setFont(UIHelper.FONT_SMALL);
        version.setForeground(UIHelper.TEXT_MED);
        version.setAlignmentX(CENTER_ALIGNMENT);
        sidebar.add(version);
        sidebar.add(Box.createVerticalStrut(10));

        return sidebar;
    }

    private JPanel createUserCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setAlignmentX(CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(220, 100));

        // Avatar
        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIHelper.PRIMARY_LIGHT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
                FontMetrics fm = g2.getFontMetrics();
                String text = getInitials();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(text, x, y);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(64, 64));
        avatar.setMaximumSize(new Dimension(64, 64));
        avatar.setAlignmentX(CENTER_ALIGNMENT);

        // Name
        JLabel name = new JLabel(currentUser.getFname());
        name.setFont(UIHelper.FONT_HEADER);
        name.setForeground(UIHelper.TEXT_DARK);
        name.setAlignmentX(CENTER_ALIGNMENT);

        // ID
        JLabel id = new JLabel(currentUser.getuId());
        id.setFont(UIHelper.FONT_SMALL);
        id.setForeground(UIHelper.TEXT_MED);
        id.setAlignmentX(CENTER_ALIGNMENT);

        card.add(avatar);
        card.add(Box.createVerticalStrut(10));
        card.add(name);
        card.add(Box.createVerticalStrut(2));
        card.add(id);

        return card;
    }

    private void addNavItem(JPanel parent, String icon, String title, String desc, Runnable action) {
        JPanel item = new JPanel(new BorderLayout(10, 0));
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(220, 50));
        item.setAlignmentX(CENTER_ALIGNMENT);
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        ico.setBorder(new EmptyBorder(0, 8, 0, 0));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIHelper.FONT_BODY);
        lblTitle.setForeground(UIHelper.TEXT_DARK);

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(UIHelper.FONT_SMALL);
        lblDesc.setForeground(UIHelper.TEXT_MED);

        textPanel.add(lblTitle);
        textPanel.add(lblDesc);

        item.add(ico, BorderLayout.WEST);
        item.add(textPanel, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            private Color defaultBg = Color.WHITE;
            private Color hoverBg = new Color(241, 245, 249);

            public void mouseEntered(MouseEvent e) {
                item.setBackground(hoverBg);
            }
            public void mouseExited(MouseEvent e) {
                item.setBackground(defaultBg);
            }
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });

        parent.add(item);
        parent.add(Box.createVerticalStrut(4));
    }

    private JButton createNavButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(UIHelper.FONT_SMALL);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(80, 32));
        return btn;
    }

    // ─── Page Content ─────────────────────────────────────────────────────────
    private void showHome() {
        currentPage = "Home";
        contentPanel.removeAll();

        JPanel home = new JPanel(new BorderLayout(0, 20));
        home.setBackground(UIHelper.BG);

        // Welcome header
        JLabel header = new JLabel("Welcome back, " + currentUser.getFname() + "!");
        header.setFont(UIHelper.FONT_TITLE);
        header.setForeground(UIHelper.TEXT_DARK);
        home.add(header, BorderLayout.NORTH);

        // Stats cards
        JPanel stats = new JPanel(new GridLayout(1, 4, 16, 0));
        stats.setOpaque(false);

        stats.add(createStatCard("📊", "SGPA", "3.75", UIHelper.PRIMARY));
        stats.add(createStatCard("🎯", "CGPA", "3.82", UIHelper.ACCENT));
        stats.add(createStatCard("📅", "Attendance", "92%", UIHelper.WARNING));
        stats.add(createStatCard("📚", "Courses", "5", new Color(139, 92, 246)));

        home.add(stats, BorderLayout.CENTER);

        contentPanel.add(home, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createStatCard(String icon, String label, String value, Color color) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
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
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setPreferredSize(new Dimension(0, 100));

        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        ico.setAlignmentX(LEFT_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        val.setForeground(color);
        val.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(UIHelper.FONT_BODY);
        lbl.setForeground(UIHelper.TEXT_MED);
        lbl.setAlignmentX(LEFT_ALIGNMENT);

        card.add(ico);
        card.add(Box.createVerticalStrut(8));
        card.add(val);
        card.add(lbl);

        return card;
    }

    private void showResults() {
        currentPage = "Results";
        contentPanel.removeAll();
        contentPanel.add(new com.codecrew.ui.StudentResultPanel(currentUser), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAttendance() {
        currentPage = "Attendance";
        contentPanel.removeAll();
        contentPanel.add(new com.codecrew.ui.AttendancePanel(currentUser), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCourses() {
        currentPage = "Courses";
        contentPanel.removeAll();
        contentPanel.add(new com.codecrew.ui.CoursePanel(currentUser), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showProfile() {
        currentPage = "Profile";
        contentPanel.removeAll();

        JPanel profile = new JPanel(new BorderLayout(0, 20));
        profile.setBackground(UIHelper.BG);

        JLabel header = new JLabel("👤 My Profile");
        header.setFont(UIHelper.FONT_TITLE);
        header.setForeground(UIHelper.TEXT_DARK);
        profile.add(header, BorderLayout.NORTH);

        JPanel card = UIHelper.card("");
        card.setLayout(new GridLayout(0, 2, 20, 16));

        card.add(createInfoField("Student ID", currentUser.getuId()));
        card.add(createInfoField("Full Name", currentUser.getFullName()));
        card.add(createInfoField("First Name", currentUser.getFname()));
        card.add(createInfoField("Role", currentUser.getRole()));
        card.add(createInfoField("Email", currentUser.getEmail() != null ? currentUser.getEmail() : "N/A"));

        profile.add(card, BorderLayout.CENTER);
        contentPanel.add(profile, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createInfoField(String label, String value) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(UIHelper.FONT_SMALL);
        lbl.setForeground(UIHelper.TEXT_MED);

        JLabel val = new JLabel(value != null ? value : "N/A");
        val.setFont(UIHelper.FONT_HEADER);
        val.setForeground(UIHelper.TEXT_DARK);

        p.add(lbl, BorderLayout.NORTH);
        p.add(val, BorderLayout.CENTER);

        return p;
    }

    private String getInitials() {
        String fname = currentUser.getFname();
        if (fname != null && fname.length() > 0) {
            return fname.substring(0, 1).toUpperCase();
        }
        return "S";
    }

    // ─── Main ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        UIHelper.setLookAndFeel();
        // For testing - create dummy user
        User testUser = new User("ST001", "John", "Doe", "john@edu", 
            "Address", "Male", "2000-01-01", "Student", "Computing");
        SwingUtilities.invokeLater(() -> new StudentDashboardUI(testUser).setVisible(true));
    }
}