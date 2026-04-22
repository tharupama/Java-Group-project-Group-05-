package com.codecrew.ui;

import com.codecrew.dao.ResultDAO;
import com.codecrew.model.User;
import com.codecrew.util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * StudentResultPanel - Students view their own results and GPA
 */
public class StudentResultPanel extends JPanel {

    private final User currentUser;
    private final ResultDAO resultDao = new ResultDAO();

    private JTable table;
    private DefaultTableModel model;

    private static final String[] COLS =
        {"Course Code", "Course Name", "Credits", "CA Marks", "Final Marks", "Grade", "Status"};

    public StudentResultPanel(User user) {
        this.currentUser = user;
        setLayout(new BorderLayout(0, 16));
        setBackground(UIHelper.BG);
        build();
        loadMyResults();
    }

    private void build() {
        JLabel header = UIHelper.headerLabel("📊  My Academic Results");
        header.setBorder(new EmptyBorder(0, 0, 4, 0));
        add(header, BorderLayout.NORTH);

        JPanel card = UIHelper.card("");
        card.setLayout(new BorderLayout(0, 12));

        // GPA cards row
        double sgpa = resultDao.getSGPA(currentUser.getuId());
        double cgpa = resultDao.getCGPA(currentUser.getuId());

        JPanel gpaRow = new JPanel(new GridLayout(1, 3, 16, 0));
        gpaRow.setOpaque(false);
        gpaRow.add(gpaCard("SGPA", String.format("%.2f", sgpa), new Color(37, 99, 235)));
        gpaRow.add(gpaCard("CGPA", String.format("%.2f", cgpa), new Color(5, 150, 105)));
        gpaRow.add(gpaCard("Semester", "2 / 2024-25", new Color(139, 92, 246)));

        // Table
        model = new DefaultTableModel(COLS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        UIHelper.styleTable(table);

        // Grade coloring
        table.getColumnModel().getColumn(5).setCellRenderer((t, val, sel, foc, row, col) -> {
            JLabel l = new JLabel(val != null ? val.toString() : "");
            l.setOpaque(true);
            l.setFont(new Font("Segoe UI", Font.BOLD, 13));
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setBorder(new EmptyBorder(0, 4, 0, 4));
            l.setBackground(sel ? new Color(219,234,254) : (row%2==0?Color.WHITE:new Color(248,250,252)));
            String g = val!=null?val.toString():"";
            l.setForeground(g.startsWith("A")?new Color(5,150,105):
                            g.startsWith("B")?new Color(37,99,235):
                            g.startsWith("C")?UIHelper.WARNING:UIHelper.DANGER);
            return l;
        });

        table.getColumnModel().getColumn(6).setCellRenderer((t, val, sel, foc, row, col) -> {
            JLabel l = new JLabel(val!=null?val.toString():"");
            l.setOpaque(true);
            l.setFont(new Font("Segoe UI",Font.BOLD,11));
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setBorder(new EmptyBorder(0,4,0,4));
            l.setBackground(sel?new Color(219,234,254):(row%2==0?Color.WHITE:new Color(248,250,252)));
            l.setForeground("PASS".equals(val)?new Color(5,150,105):UIHelper.DANGER);
            return l;
        });

        card.add(gpaRow, BorderLayout.NORTH);
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
    }

    private JPanel gpaCard(String label, String value, Color color) {
        JPanel c = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                g2.setColor(color);
                g2.fillRoundRect(0,0,6,getHeight(),6,6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        c.setOpaque(false);
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setBorder(new EmptyBorder(14, 20, 14, 16));
        c.setPreferredSize(new Dimension(0, 90));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        val.setForeground(color);

        JLabel lbl = new JLabel(label);
        lbl.setFont(UIHelper.FONT_BODY);
        lbl.setForeground(UIHelper.TEXT_MED);

        c.add(val);
        c.add(lbl);
        return c;
    }

    private void loadMyResults() {
        model.setRowCount(0);
        try (ResultSet rs = resultDao.getResultsTableData(currentUser.getuId())) {
            if (rs == null) return;
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("course_code"),
                    rs.getString("Name"),
                    rs.getInt("Credit"),
                    String.format("%.2f", rs.getDouble("CA_marks")),
                    String.format("%.2f", rs.getDouble("Final_Marks")),
                    rs.getString("Final_Grade"),
                    rs.getString("Result_Status")
                });
            }
        } catch (SQLException e) {
            UIHelper.showError(this, "Error: " + e.getMessage());
        }
    }
}
