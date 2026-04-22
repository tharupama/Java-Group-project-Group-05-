package com.codecrew.ui;

import com.codecrew.dao.UserDAO;
import com.codecrew.model.User;
import com.codecrew.util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginForm - Entry point UI
 * FOT Student Management System
 */
public class LoginForm extends JFrame {

    private JTextField txtUserId;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblError;

    public LoginForm() {
        UIHelper.setLookAndFeel();
        initComponents();
    }

    private void initComponents() {
        setTitle("FOT Student Management System - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIHelper.BG);

        // ─── Left Panel (Blue banner) ───────────────────────────────────
        JPanel leftPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, UIHelper.PRIMARY,
                        0, getHeight(), new Color(37, 99, 235));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setPreferredSize(new Dimension(360, 0));
        leftPanel.setLayout(new GridBagLayout());

        JPanel leftContent = new JPanel();
        leftContent.setOpaque(false);
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🎓");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel uni = new JLabel("University of Ruhuna");
        uni.setFont(new Font("Segoe UI", Font.BOLD, 18));
        uni.setForeground(Color.WHITE);
        uni.setAlignmentX(CENTER_ALIGNMENT);

        JLabel fot = new JLabel("Faculty of Technology");
        fot.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fot.setForeground(new Color(187, 213, 255));
        fot.setAlignmentX(CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(200, 1));
        sep.setForeground(new Color(147, 197, 253));

        JLabel sys = new JLabel("Student Management System");
        sys.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sys.setForeground(new Color(219, 234, 254));
        sys.setAlignmentX(CENTER_ALIGNMENT);

        leftContent.add(logo);
        leftContent.add(Box.createVerticalStrut(16));
        leftContent.add(uni);
        leftContent.add(Box.createVerticalStrut(4));
        leftContent.add(fot);
        leftContent.add(Box.createVerticalStrut(20));
        leftContent.add(sep);
        leftContent.add(Box.createVerticalStrut(16));
        leftContent.add(sys);

        leftPanel.add(leftContent);

        // ─── Right Panel (Login Form) ───────────────────────────────────
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UIHelper.TEXT_DARK);
        title.setAlignmentX(LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Sign in to your account");
        sub.setFont(UIHelper.FONT_BODY);
        sub.setForeground(UIHelper.TEXT_MED);
        sub.setAlignmentX(LEFT_ALIGNMENT);

        // User ID
        JLabel lblId = UIHelper.fieldLabel("User ID");
        lblId.setAlignmentX(LEFT_ALIGNMENT);
        txtUserId = UIHelper.styledField();
        txtUserId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUserId.setAlignmentX(LEFT_ALIGNMENT);

        // Password
        JLabel lblPwd = UIHelper.fieldLabel("Password");
        lblPwd.setAlignmentX(LEFT_ALIGNMENT);
        txtPassword = UIHelper.styledPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPassword.setAlignmentX(LEFT_ALIGNMENT);

        // Error label
        lblError = new JLabel(" ");
        lblError.setForeground(UIHelper.DANGER);
        lblError.setFont(UIHelper.FONT_SMALL);
        lblError.setAlignmentX(LEFT_ALIGNMENT);

        // Login button
        btnLogin = UIHelper.primaryButton("Sign In");
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnLogin.setAlignmentX(LEFT_ALIGNMENT);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Demo credentials hint
        JLabel hint = new JLabel("<html><font color='#94a3b8'>Use password: <b>1234</b> for all users</font></html>");
        hint.setFont(UIHelper.FONT_SMALL);
        hint.setAlignmentX(LEFT_ALIGNMENT);

        form.add(title);
        form.add(Box.createVerticalStrut(4));
        form.add(sub);
        form.add(Box.createVerticalStrut(30));
        form.add(lblId);
        form.add(Box.createVerticalStrut(6));
        form.add(txtUserId);
        form.add(Box.createVerticalStrut(16));
        form.add(lblPwd);
        form.add(Box.createVerticalStrut(6));
        form.add(txtPassword);
        form.add(Box.createVerticalStrut(8));
        form.add(lblError);
        form.add(Box.createVerticalStrut(20));
        form.add(btnLogin);
        form.add(Box.createVerticalStrut(20));
        form.add(hint);

        rightPanel.add(form);

        root.add(leftPanel, BorderLayout.WEST);
        root.add(rightPanel, BorderLayout.CENTER);
        setContentPane(root);

        // ─── Events ────────────────────────────────────────────────────
        btnLogin.addActionListener(e -> doLogin());
        txtPassword.addActionListener(e -> doLogin());
        txtUserId.addActionListener(e -> txtPassword.requestFocus());
    }

    private void doLogin() {
        String userId = txtUserId.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (userId.isEmpty() || password.isEmpty()) {
            lblError.setText("Please enter User ID and Password.");
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.authenticate(userId, password);

        if (user != null) {
            // Only allow Student role
            if (!user.getRole().equals("Student")) {
                lblError.setText("Only Student login is allowed.");
                txtPassword.setText("");
                return;
            }
            lblError.setText(" ");
            dispose();
            SwingUtilities.invokeLater(() -> new MainDashboard(user).setVisible(true));
        } else {
            lblError.setText("Invalid User ID or Password.");
            txtPassword.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
