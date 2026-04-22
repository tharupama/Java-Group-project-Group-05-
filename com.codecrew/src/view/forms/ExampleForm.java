package com.codecrew.view.forms;

import com.codecrew.model.User;
import com.codecrew.util.UIHelper;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * ExampleForm - Demonstrates how to use the form templates
 * Copy and modify this to create custom forms
 */
public class ExampleForm extends JFrame {

    private User currentUser;

    public ExampleForm(User user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Example Form - Customizable UI");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Add example form panel
        add(new ExampleStudentForm(currentUser), BorderLayout.CENTER);
    }

    /**
     * Example student form using the templates
     * Copy this class and modify as needed
     */
    static class ExampleStudentForm extends BaseForm {

        private User user;
        private JTextField txtName, txtEmail, txtPhone;
        private JComboBox<String> cbGender;
        private JTable table;
        private DefaultTableModel tableModel;

        public ExampleStudentForm(User user) {
            super("👤 Student Profile");
            this.user = user;
            buildForm();
            loadData();
        }

        private void buildForm() {
            // ─── SECTION 1: Personal Info ───────────────────────────────────
            JPanel infoSection = new JPanel(new BorderLayout(0, 12));
            infoSection.setOpaque(false);

            // Form fields
            txtName = createField();
            txtEmail = createField();
            txtPhone = createField();
            cbGender = createComboBox(new String[]{"Male", "Female", "Other"});

            // Create form grid
            JPanel fields = createFormGrid(
                createFormField("Full Name", txtName),
                createFormField("Email", txtEmail),
                createFormField("Phone", txtPhone),
                createFormField("Gender", cbGender)
            );

            infoSection.add(fields, BorderLayout.NORTH);

            // Buttons
            JButton btnSave = createPrimaryButton("Save");
            JButton btnCancel = createButton("Cancel", UIHelper.TEXT_MED);

            btnSave.addActionListener(e -> saveData());

            JPanel buttons = createButtonRow(btnSave, btnCancel);
            infoSection.add(buttons, BorderLayout.CENTER);

            // ─── SECTION 2: Data Table ───────────────────────────────────────
            TableForm tableForm = new TableForm("📋 Enrolled Courses", 
                new String[]{"Code", "Name", "Credits", "Grade", "Status"});
            table = tableForm.getTable();
            tableModel = tableForm.getTableModel();

            // ─── SECTION 3: Stats Cards ──────────────────────────────────────
            CardForm cardForm = new CardForm("📊 Quick Stats");
            cardForm.addStatCard("📊", "SGPA", "3.75", UIHelper.PRIMARY);
            cardForm.addStatCard("🎯", "CGPA", "3.82", UIHelper.ACCENT);
            cardForm.addStatCard("📅", "Attendance", "92%", UIHelper.WARNING);

            // ─── SECTION 4: Tabs ─────────────────────────────────────────────
            TabForm tabForm = new TabForm("📑 Details");

            // Tab 1: Info
            JPanel infoTab = new JPanel(new BorderLayout(0, 10));
            infoTab.setOpaque(false);
            infoTab.add(infoSection, BorderLayout.NORTH);
            tabForm.addTab("ℹ️ Info", infoTab);

            // Tab 2: Courses
            JPanel coursesTab = new JPanel(new BorderLayout(0, 10));
            coursesTab.setOpaque(false);
            coursesTab.add(tableForm, BorderLayout.CENTER);
            tabForm.addTab("📚 Courses", coursesTab);

            // Tab 3: Stats
            JPanel statsTab = new JPanel(new BorderLayout(0, 10));
            statsTab.setOpaque(false);
            statsTab.add(cardForm, BorderLayout.NORTH);
            tabForm.addTab("📈 Stats", statsTab);

            // Add to card
            setCardContent(tabForm);
        }

        private void loadData() {
            // Load user data
            txtName.setText(user.getFullName());
            txtEmail.setText(user.getEmail() != null ? user.getEmail() : "");

            // Sample course data
            tableModel.addRow(new Object[]{"CS101", "Intro to Programming", "3", "A", "Pass"});
            tableModel.addRow(new Object[]{"CS102", "Data Structures", "3", "A-", "Pass"});
            tableModel.addRow(new Object[]{"MATH101", "Calculus I", "4", "B+", "Pass"});
        }

        private void saveData() {
            JOptionPane.showMessageDialog(this, "Data saved successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        private JComboBox<String> createComboBox(String[] items) {
            JComboBox<String> cb = new JComboBox<>(items);
            cb.setFont(UIHelper.FONT_BODY);
            cb.setPreferredSize(new Dimension(200, FIELD_HEIGHT));
            return cb;
        }
    }

    // ─── MAIN FOR TESTING ───────────────────────────────────────────────────
    public static void main(String[] args) {
        UIHelper.setLookAndFeel();
        User testUser = new User("ST001", "John", "Doe", "john@edu", 
            "Address", "Male", "2000-01-01", "Student", "Computing");
        SwingUtilities.invokeLater(() -> new ExampleForm(testUser).setVisible(true));
    }
}