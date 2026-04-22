package com.codecrew.view.forms;

import com.codecrew.util.UIHelper;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * CardForm - Reusable card/stat display form
 * Easy to customize colors, icons, and values
 */
public class CardForm extends BaseForm {

    private JPanel cardsContainer;
    private int cardCount = 0;

    /**
     * Create a card form
     * @param title Form title
     */
    public CardForm(String title) {
        super(title);
        cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.X_AXIS));
        cardsContainer.setOpaque(false);
        setCardContent(cardsContainer);
    }

    // ─── CARD METHODS ────────────────────────────────────────────────────────

    /**
     * Add a stat card
     * @param icon Emoji or icon text
     * @param label Card label
     * @param value Display value
     * @param color Accent color
     */
    public void addStatCard(String icon, String label, String value, Color color) {
        JPanel card = createStatCard(icon, label, value, color);
        cardsContainer.add(card);
        cardCount++;
    }

    /**
     * Add a simple info card
     * @param title Card title
     * @param content Card content
     */
    public void addInfoCard(String title, String content) {
        JPanel card = createInfoCard(title, content);
        cardsContainer.add(card);
        cardCount++;
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
        card.setPreferredSize(new Dimension(180, 100));
        card.setMaximumSize(new Dimension(200, 100));

        // Icon
        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        ico.setAlignmentX(LEFT_ALIGNMENT);

        // Value
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        val.setForeground(color);
        val.setAlignmentX(LEFT_ALIGNMENT);

        // Label
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

    private JPanel createInfoCard(String title, String content) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIHelper.BORDER, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));
        card.setPreferredSize(new Dimension(200, 100));

        JLabel lbl = new JLabel(title);
        lbl.setFont(UIHelper.FONT_HEADER);
        lbl.setForeground(UIHelper.PRIMARY);

        JLabel val = new JLabel(content);
        val.setFont(UIHelper.FONT_BODY);
        val.setForeground(UIHelper.TEXT_DARK);

        card.add(lbl, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);

        return card;
    }

    /** Set gap between cards */
    public void setCardGap(int gap) {
        cardsContainer.setLayout(new GridLayout(1, cardCount, gap, 0));
    }

    /** Clear all cards */
    public void clearCards() {
        cardsContainer.removeAll();
        cardCount = 0;
    }
}