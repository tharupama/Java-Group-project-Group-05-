package com.codecrew.ui;

import com.codecrew.model.User;
import com.codecrew.util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * MainDashboard - Central navigation hub
 * Shows role-appropriate menu and content panel
 */
public class MainDashboard extends JFrame {

    private final User currentUser;
    private JPanel contentArea;
    private JLabel lblPageTitle;

    public MainDashboard(User user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("FOT Student Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(UIHelper.BG);

        add(buildTopBar(),  BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildContent(), BorderLayout.CENTER);

        showHome();
    }

    // ─── Top Bar ─────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(UIHelper.PRIMARY);
        bar.setPreferredSize(new Dimension(0, 56));
        bar.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("FOT Student Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(Color.WHITE);

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userInfo.setOpaque(false);

        JLabel role = new JLabel("[" + currentUser.getRole() + "]");
        role.setFont(UIHelper.FONT_SMALL);
        role.setForeground(new Color(187, 213, 255));

        JLabel name = new JLabel(currentUser.getFullName());
        name.setFont(UIHelper.FONT_BODY);
        name.setForeground(Color.WHITE);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(239, 68, 68));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(UIHelper.FONT_SMALL);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        userInfo.add(role);
        userInfo.add(name);
        userInfo.add(btnLogout);

        bar.add(title, BorderLayout.WEST);
        bar.add(userInfo, BorderLayout.EAST);
        return bar;
    }

    // ─── Sidebar ─────────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(15, 23, 42));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(16, 0, 16, 0));

        // User avatar block
        JPanel avatar = new JPanel();
        avatar.setOpaque(false);
        avatar.setLayout(new BoxLayout(avatar, BoxLayout.Y_AXIS));
        avatar.setAlignmentX(CENTER_ALIGNMENT);
        avatar.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel icon = new JLabel("👤");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel uName = new JLabel(currentUser.getFname());
        uName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        uName.setForeground(Color.WHITE);
        uName.setAlignmentX(CENTER_ALIGNMENT);

        JLabel uId = new JLabel(currentUser.getuId());
        uId.setFont(UIHelper.FONT_SMALL);
        uId.setForeground(new Color(148, 163, 184));
        uId.setAlignmentX(CENTER_ALIGNMENT);

        avatar.add(icon);
        avatar.add(Box.createVerticalStrut(6));
        avatar.add(uName);
        avatar.add(Box.createVerticalStrut(2));
        avatar.add(uId);
        sidebar.add(avatar);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(180, 1));
        sep.setForeground(new Color(51, 65, 85));
        sep.setAlignmentX(CENTER_ALIGNMENT);
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(12));

        // Menu items - Student only
        addNavItem(sidebar, "🏠  Home", () -> showHome());
        addNavItem(sidebar, "📊  My Results",  () -> showPanel(new StudentResultPanel(currentUser)));
        addNavItem(sidebar, "📅  Attendance",  () -> showPanel(new AttendancePanel(currentUser)));
        addNavItem(sidebar, "📚  My Courses",  () -> showPanel(new CoursePanel(currentUser)));

        sidebar.add(Box.createVerticalGlue());

        JLabel ver = new JLabel("v1.0  |  CodeCrew");
        ver.setFont(UIHelper.FONT_SMALL);
        ver.setForeground(new Color(100, 116, 139));
        ver.setAlignmentX(CENTER_ALIGNMENT);
        sidebar.add(ver);

        return sidebar;
    }

    private void addNavItem(JPanel sidebar, String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 44));
        btn.setPreferredSize(new Dimension(220, 44));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(203, 213, 225));
        btn.setBackground(new Color(15, 23, 42));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(0, 20, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(30, 41, 59));
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(15, 23, 42));
                btn.setForeground(new Color(203, 213, 225));
            }
        });

        btn.addActionListener(e -> action.run());
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(2));
    }

    // ─── Content Area ─────────────────────────────────────────────────────────
    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UIHelper.BG);
        wrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(UIHelper.BG);
        wrapper.add(contentArea, BorderLayout.CENTER);
        return wrapper;
    }

    void showPanel(JPanel panel) {
        contentArea.removeAll();
        contentArea.add(panel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    private void showHome() {
        showPanel(new HomePanel(currentUser));
    }
}
