package com.codecrew.view.forms;

import com.codecrew.util.UIHelper;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;

/**
 * TableForm - Reusable table form template
 * Easy to customize columns, data, and styling
 */
public class TableForm extends BaseForm {

    private JTable table;
    private DefaultTableModel tableModel;
    private String[] columnNames;
    private JScrollPane scrollPane;

    /**
     * Create a table form
     * @param title Form title
     * @param columns Column names for the table
     */
    public TableForm(String title, String[] columns) {
        super(title);
        this.columnNames = columns;
        buildTable();
    }

    private void buildTable() {
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // Make table read-only
            }
        };

        table = new JTable(tableModel);
        styleTable();

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        setCardContent(scrollPane);
    }

    private void styleTable() {
        table.setFont(UIHelper.FONT_BODY);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(UIHelper.TEXT_DARK);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);

        // Table header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(UIHelper.TABLE_HDR);
        header.setForeground(Color.WHITE);
        header.setFont(UIHelper.FONT_HEADER);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        // Alternate row coloring
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? Color.WHITE : UIHelper.TABLE_ALT);
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    // ─── DATA METHODS ────────────────────────────────────────────────────────

    /** Add a row of data */
    public void addRow(Object... values) {
        tableModel.addRow(values);
    }

    /** Clear all rows */
    public void clearRows() {
        tableModel.setRowCount(0);
    }

    /** Get the table model */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /** Get the table */
    public JTable getTable() {
        return table;
    }

    /** Get selected row index */
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    /** Get value at specific row and column */
    public Object getValueAt(int row, int col) {
        return tableModel.getValueAt(row, col);
    }

    /** Set column renderer for specific column */
    public void setColumnRenderer(int columnIndex, TableCellRenderer renderer) {
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
    }

    /** Set column width */
    public void setColumnWidth(int columnIndex, int width) {
        table.getColumnModel().getColumn(columnIndex).setPreferredWidth(width);
    }

    /** Hide a column */
    public void hideColumn(int columnIndex) {
        table.getColumnModel().getColumn(columnIndex).setMinWidth(0);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
    }

    // ─── STATIC FACTORY METHODS ──────────────────────────────────────────────

    /** Create a simple table form */
    public static TableForm create(String title, String... columns) {
        return new TableForm(title, columns);
    }

    /** Create a table form with data */
    public static TableForm createWithData(String title, String[] columns, Object[][] data) {
        TableForm form = new TableForm(title, columns);
        for (Object[] row : data) {
            form.addRow(row);
        }
        return form;
    }
}