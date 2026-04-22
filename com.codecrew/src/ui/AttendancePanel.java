package com.codecrew.ui;

import com.codecrew.dao.AttendanceDAO;
import com.codecrew.dao.CourseDAO;
import com.codecrew.model.CourseUnit;
import com.codecrew.model.User;
import com.codecrew.util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * AttendancePanel - Students view their own attendance records
 */
public class AttendancePanel extends JPanel {

    private final User currentUser;
    private final AttendanceDAO attDao = new AttendanceDAO();
    private final CourseDAO courseDao = new CourseDAO();

    private JComboBox<String> cbCourse;
    private JTable tblSummary, tblDetail;
    private DefaultTableModel summaryModel, detailModel;

    private static final String[] SUMMARY_COLS =
        {"Course", "Total", "Present", "Absent", "Medical", "Percentage", "Eligibility"};
    private static final String[] DETAIL_COLS =
        {"Date", "Week", "Type", "Status"};

    public AttendancePanel(User user) {
        this.currentUser = user;
        setLayout(new BorderLayout(0, 16));
        setBackground(UIHelper.BG);
        build();
        loadMyAttendance();
    }

    private void build() {
        JLabel header = UIHelper.headerLabel("📅  My Attendance");
        header.setBorder(new EmptyBorder(0, 0, 4, 0));
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(UIHelper.FONT_BODY);
        tabs.addTab("📋 Course Summary",    buildSummaryTab());
        tabs.addTab("🔍 Detailed View",    buildDetailTab());

        add(tabs, BorderLayout.CENTER);
    }

    // ─── Summary Tab ──────────────────────────────────────────────────────────
    private JPanel buildSummaryTab() {
        JPanel p = UIHelper.card("");
        p.setLayout(new BorderLayout(0, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        top.setOpaque(false);

        // Get student's enrolled courses
        List<CourseUnit> courses = courseDao.getEnrolledCourses(currentUser.getuId());
        String[] courseArr = new String[courses.size() + 1];
        courseArr[0] = "-- Select Course --";
        for (int i = 0; i < courses.size(); i++)
            courseArr[i+1] = courses.get(i).getCourseCode() + " - " + courses.get(i).getName();

        cbCourse = UIHelper.styledCombo(courseArr);
        cbCourse.setPreferredSize(new Dimension(300, 36));
        JButton btnLoad = UIHelper.primaryButton("Load Summary");
        btnLoad.setPreferredSize(new Dimension(140, 36));

        top.add(UIHelper.fieldLabel("Course:"));
        top.add(cbCourse);
        top.add(btnLoad);

        summaryModel = new DefaultTableModel(SUMMARY_COLS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblSummary = new JTable(summaryModel);
        UIHelper.styleTable(tblSummary);

        // Color eligibility column
        tblSummary.getColumnModel().getColumn(6).setCellRenderer((t, val, sel, foc, row, col) -> {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setOpaque(true);
            lbl.setFont(UIHelper.FONT_BODY);
            lbl.setBorder(new EmptyBorder(0, 8, 0, 8));
            lbl.setBackground(sel ? new Color(219, 234, 254) : (row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252)));
            lbl.setForeground("Eligible".equals(val) ? new Color(5, 150, 105) : UIHelper.DANGER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            return lbl;
        });

        btnLoad.addActionListener(e -> loadSummary());

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(tblSummary), BorderLayout.CENTER);
        return p;
    }

    private void loadSummary() {
        summaryModel.setRowCount(0);
        int sel = cbCourse.getSelectedIndex();
        if (sel == 0) { UIHelper.showError(this, "Please select a course."); return; }
        String code = cbCourse.getSelectedItem().toString().split(" - ")[0];
        try (ResultSet rs = attDao.getStudentCourseSummary(currentUser.getuId(), code)) {
            if (rs == null) return;
            while (rs.next()) {
                summaryModel.addRow(new Object[]{
                    rs.getString("Course_code"),
                    rs.getInt("Total_Sessions"),
                    rs.getInt("Present"),
                    rs.getInt("Absent"),
                    rs.getInt("Medical"),
                    String.format("%.1f%%", rs.getDouble("Percentage")),
                    rs.getString("Eligibility")
                });
            }
        } catch (SQLException e) {
            UIHelper.showError(this, "Error loading summary: " + e.getMessage());
        }
    }

    // ─── Detail Tab ───────────────────────────────────────────────────────────
    private JPanel buildDetailTab() {
        JPanel p = UIHelper.card("");
        p.setLayout(new BorderLayout(0, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        top.setOpaque(false);

        // Get student's enrolled courses
        List<CourseUnit> courses = courseDao.getEnrolledCourses(currentUser.getuId());
        String[] cArr = new String[courses.size() + 1];
        cArr[0] = "-- Select Course --";
        for (int i = 0; i < courses.size(); i++)
            cArr[i+1] = courses.get(i).getCourseCode() + " - " + courses.get(i).getName();

        JComboBox<String> cbC = UIHelper.styledCombo(cArr);
        cbC.setPreferredSize(new Dimension(300, 36));
        JButton btnLoad = UIHelper.primaryButton("View");
        btnLoad.setPreferredSize(new Dimension(90, 36));

        top.add(UIHelper.fieldLabel("Course:"));  top.add(cbC);
        top.add(btnLoad);

        detailModel = new DefaultTableModel(DETAIL_COLS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblDetail = new JTable(detailModel);
        UIHelper.styleTable(tblDetail);

        tblDetail.getColumnModel().getColumn(3).setCellRenderer((t, val, sel, foc, row, col) -> {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setOpaque(true);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setBorder(new EmptyBorder(0, 8, 0, 8));
            lbl.setBackground(sel ? new Color(219, 234, 254) : (row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252)));
            String status = val != null ? val.toString() : "";
            lbl.setForeground(switch (status) {
                case "Present" -> new Color(5, 150, 105);
                case "Absent"  -> UIHelper.DANGER;
                case "Medical" -> UIHelper.WARNING;
                default -> UIHelper.TEXT_DARK;
            });
            return lbl;
        });

        btnLoad.addActionListener(e -> {
            detailModel.setRowCount(0);
            if (cbC.getSelectedIndex() == 0) return;
            String course = cbC.getSelectedItem().toString().split(" - ")[0];
            attDao.getAttendance(currentUser.getuId(), course).forEach(a ->
                detailModel.addRow(new Object[]{
                    a.getSessionDate(),
                    a.getWeekNumber(), a.getSessionType(), a.getStatus()
                })
            );
        });

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(tblDetail), BorderLayout.CENTER);
        return p;
    }

    // ─── Load all attendance on init ─────────────────────────────────────────
    private void loadMyAttendance() {
        // Load first course by default if available
        List<CourseUnit> courses = courseDao.getEnrolledCourses(currentUser.getuId());
        if (!courses.isEmpty()) {
            cbCourse.setSelectedIndex(1);
            loadSummary();
        }
    }
}
