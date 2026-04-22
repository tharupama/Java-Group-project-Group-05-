package com.codecrew.view.forms;

import com.codecrew.util.UIHelper;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * BaseForm - Reusable form template with consistent styling
 * Easy to customize colors, fonts, and layout
 */
public class BaseForm extends JPanel {

    // ─── CONFIGURABLE COLORS - Change these to customize ─────────────────────
    public static Color FORM_BG = UIHelper.BG;           // Form background
    public static Color CARD_BG = Color.WHITE;          // Card/Panel background
    public static Color PRIMARY_COLOR = UIHelper.PRIMARY;  // Primary accent
    public static Color SECONDARY_COLOR = UIHelper.PRIMARY_LIGHT; // Secondary
    public static Color TEXT_COLOR = UIHelper.TEXT_DARK;    // Main text
    public static Color LABEL_COLOR = UIHelper.TEXT_MED;    // Label text
    public static Color BORDER_COLOR = UIHelper.BORDER;     // Border color

    // ─── CONFIGURABLE FONTS ─────────────────────────────────────────────────
    public static Font TITLE_FONT = UIHelper.FONT_TITLE;
    public static Font HEADER_FONT = UIHelper.FONT_HEADER;
    public static Font BODY_FONT = UIHelper.FONT_BODY;
    public static Font LABEL_FONT = UIHelper.FONT_SMALL;

    // ─── CONFIGURABLE SIZES ─────────────────────────────────────────────────
    public static int CARD_PADDING = 16;
    public static int FIELD_HEIGHT = 36;
    public static int BUTTON_WIDTH = 160;
    public static int BUTTON_HEIGHT = 38;

    protected JLabel lblTitle;
    protected JPanel cardPanel;

    public BaseForm(String title) {
        setLayout(new BorderLayout(0, 16));
        setBackground(FORM_BG);
        setBorder(new EmptyBorder(20, 24, 20, 24));

        if (title != null && !title.isEmpty()) {
            lblTitle = new JLabel(title);
            lblTitle.setFont(TITLE_FONT);
            lblTitle.setForeground(PRIMARY_COLOR);
            add(lblTitle, BorderLayout.NORTH);
        }

        cardPanel = new JPanel(new BorderLayout(0, 12));
        cardPanel.setBackground(CARD_BG);
        cardPanel.setBorder(createCardBorder());
        add(cardPanel, BorderLayout.CENTER);
    }

    private Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(CARD_PADDING, CARD_PADDING, CARD_PADDING, CARD_PADDING)
        );
    }

    // ─── HELPER METHODS FOR CUSTOMIZATION ──────────────────────────────────

    /** Create a styled text field */
    protected JTextField createField() {
        JTextField field = new JTextField();
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(200, FIELD_HEIGHT));
        return field;
    }

    /** Create a styled password field */
    protected JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(200, FIELD_HEIGHT));
        return field;
    }

    /** Create a styled button */
    protected JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(HEADER_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return btn;
    }

    /** Create primary button */
    protected JButton createPrimaryButton(String text) {
        return createButton(text, PRIMARY_COLOR);
    }

    /** Create success button */
    protected JButton createSuccessButton(String text) {
        return createButton(text, UIHelper.ACCENT);
    }

    /** Create danger button */
    protected JButton createDangerButton(String text) {
        return createButton(text, UIHelper.DANGER);
    }

    /** Create a form label */
    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        return label;
    }

    /** Create a form field with label */
    protected JPanel createFormField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);
        panel.add(createLabel(labelText), BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    /** Create a row of buttons */
    protected JPanel createButtonRow(JButton... buttons) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setOpaque(false);
        for (JButton btn : buttons) {
            row.add(btn);
        }
        return row;
    }

    /** Create a grid of form fields */
    protected JPanel createFormGrid(JPanel... fields) {
        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 12));
        grid.setOpaque(false);
        for (JPanel field : fields) {
            grid.add(field);
        }
        return grid;
    }

    /** Add content to the card */
    protected void setCardContent(JComponent content) {
        cardPanel.add(content, BorderLayout.CENTER);
    }

    /** Add content to the card with position */
    protected void setCardContent(JComponent content, String position) {
        cardPanel.add(content, position);
    }
}