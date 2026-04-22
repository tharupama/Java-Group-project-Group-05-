package com.codecrew.ui;

import com.codecrew.dao.CourseDAO;
import com.codecrew.model.CourseUnit;
import com.codecrew.model.User;
import com.codecrew.util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * CoursePanel - Students view their enrolled courses
 */
public class CoursePanel extends JPanel {

    private final User currentUser;
    private final CourseDAO dao = new CourseDAO();
    private JTable table;
    private DefaultTableModel model;

    private static final String[] COLS =
        {"Code", "Name", "Type", "Credits", "Lecturer", "Year", "Semester", "Department"};

    public CoursePanel(User user) {
        this.currentUser = user;
        setLayout(new BorderLayout(0, 16));
        setBackground(UIHelper.BG);
        build();
        loadTable(dao.getEnrolledCourses(currentUser.getuId()));
    }

    private void build() {
        JLabel header = UIHelper.headerLabel("📚  My Courses");
        header.setBorder(new EmptyBorder(0, 0, 4, 0));
        add(header, BorderLayout.NORTH);

        JPanel p = UIHelper.card("");
        p.setLayout(new BorderLayout(0, 10));

        model = new DefaultTableModel(COLS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        UIHelper.styleTable(table);

        p.add(new JScrollPane(table), BorderLayout.CENTER);
        add(p, BorderLayout.CENTER);
    }

    private void loadTable(List<CourseUnit> list) {
        model.setRowCount(0);
        for (CourseUnit c : list)
            model.addRow(new Object[]{c.getCourseCode(), c.getName(), c.getType(),
                c.getCredit(), c.getLecName(), c.getYear(), c.getSemester(), c.getDepartmentOffering()});
    }
}
