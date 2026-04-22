package com.codecrew.view.forms;

import com.codecrew.util.UIHelper;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * TabForm - Reusable tabbed form
 * Easy to add/remove tabs and customize each tab's content
 */
public class TabForm extends BaseForm {

    private JTabbedPane tabs;

    /**
     * Create a tabbed form
     * @param title Form title
     */
    public TabForm(String title) {
        super(title);
        buildTabs();
    }

    private void buildTabs() {
        tabs = new JTabbedPane();
        tabs.setFont(UIHelper.FONT_BODY);
        tabs.setBorder(null);
        setCardContent(tabs);
    }

    // ─── TAB METHODS ─────────────────────────────────────────────────────────

    /**
     * Add a tab with a panel
     * @param title Tab title (use emoji for icon, e.g., "📋 Summary")
     * @param panel Content panel
     */
    public void addTab(String title, JPanel panel) {
        tabs.addTab(title, panel);
    }

    /**
     * Add a tab with component
     * @param title Tab title
     * @param component Content component
     */
    public void addTab(String title, Component component) {
        tabs.addTab(title, component);
    }

    /**
     * Add a tab with scroll pane
     * @param title Tab title
     * @param panel Panel to wrap in scroll pane
     */
    public void addScrollTab(String title, JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        tabs.addTab(title, scroll);
    }

    /** Remove a tab by index */
    public void removeTab(int index) {
        tabs.remove(index);
    }

    /** Remove a tab by title */
    public void removeTab(String title) {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (tabs.getTitleAt(i).equals(title)) {
                tabs.remove(i);
                break;
            }
        }
    }

    /** Get the tabbed pane */
    public JTabbedPane getTabs() {
        return tabs;
    }

    /** Get selected tab index */
    public int getSelectedIndex() {
        return tabs.getSelectedIndex();
    }

    /** Set selected tab */
    public void setSelectedIndex(int index) {
        tabs.setSelectedIndex(index);
    }

    /** Get tab count */
    public int getTabCount() {
        return tabs.getTabCount();
    }

    // ─── STATIC FACTORY ──────────────────────────────────────────────────────

    /** Create a simple tab form */
    public static TabForm create(String title) {
        return new TabForm(title);
    }
}