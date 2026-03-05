

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

    // Input fields
    private JTextField weightField;
    private JTextField heightField;
    private JLabel resultLabel;
    private JLabel categoryLabel;
    private JRadioButton metricButton;
    private JRadioButton englishButton;
    private JLabel weightUnitLabel;
    private JLabel heightUnitLabel;

    public Main() {
        setTitle("BMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }
    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // ---- Title ----
        JLabel titleLabel = new JLabel("BMI Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(33, 87, 160));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ---- Center Panel ----
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Unit selection
        JLabel unitLabel = new JLabel("Unit System:");
        unitLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        metricButton = new JRadioButton("Metric (kg / m)");
        englishButton = new JRadioButton("English (lbs / inches)");
        metricButton.setBackground(new Color(245, 248, 255));
        englishButton.setBackground(new Color(245, 248, 255));
        metricButton.setSelected(true);

        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(metricButton);
        unitGroup.add(englishButton);

        ActionListener unitToggle = e -> updateUnitLabels();
        metricButton.addActionListener(unitToggle);
        englishButton.addActionListener(unitToggle);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        centerPanel.add(unitLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        centerPanel.add(metricButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        centerPanel.add(englishButton, gbc);

        // Separator
        JSeparator sep = new JSeparator();
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 8, 4, 8);
        centerPanel.add(sep, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Weight input
        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        weightField = new JTextField(10);
        weightUnitLabel = new JLabel("kg");
        weightUnitLabel.setForeground(Color.GRAY);

        gbc.gridx = 0; gbc.gridy = 3;
        centerPanel.add(weightLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        centerPanel.add(weightField, gbc);
        gbc.gridx = 2; gbc.gridy = 3;
        centerPanel.add(weightUnitLabel, gbc);

        // Height input
        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        heightField = new JTextField(10);
        heightUnitLabel = new JLabel("m");
        heightUnitLabel.setForeground(Color.GRAY);

        gbc.gridx = 0; gbc.gridy = 4;
        centerPanel.add(heightLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        centerPanel.add(heightField, gbc);
        gbc.gridx = 2; gbc.gridy = 4;
        centerPanel.add(heightUnitLabel, gbc);

        // Calculate button
        JButton calcButton = new JButton("Calculate BMI");
        calcButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        calcButton.setBackground(new Color(33, 87, 160));
        calcButton.setForeground(Color.WHITE);
        calcButton.setFocusPainted(false);
        calcButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calcButton.addActionListener(e -> calculateBMI());

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        gbc.insets = new Insets(14, 8, 8, 8);
        centerPanel.add(calcButton, gbc);

        // Result area
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(2, 1, 4, 4));
        resultPanel.setBackground(new Color(225, 235, 255));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(33, 87, 160), 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        resultLabel = new JLabel("BMI: --", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        resultLabel.setForeground(new Color(33, 87, 160));

        categoryLabel = new JLabel("Category: --", SwingConstants.CENTER);
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        resultPanel.add(resultLabel);
        resultPanel.add(categoryLabel);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
        gbc.insets = new Insets(8, 8, 8, 8);
        centerPanel.add(resultPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ---- BMI Reference Table ----
        JPanel refPanel = new JPanel();
        refPanel.setLayout(new GridLayout(5, 2, 5, 2));
        refPanel.setBackground(new Color(255, 253, 235));
        refPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 0)),
                "BMI Reference (NIH/HHS)",
                0, 0,
                new Font("SansSerif", Font.BOLD, 11),
                new Color(130, 100, 0)
        ));

        String[][] bmiRef = {
                {"Underweight", "< 18.5"},
                {"Normal",      "18.5 – 24.9"},
                {"Overweight",  "25 – 29.9"},
                {"Obese",       "≥ 30"}
        };

        JLabel hdr1 = new JLabel("  Category", SwingConstants.LEFT);
        JLabel hdr2 = new JLabel("  BMI Value", SwingConstants.LEFT);
        hdr1.setFont(new Font("SansSerif", Font.BOLD, 11));
        hdr2.setFont(new Font("SansSerif", Font.BOLD, 11));
        refPanel.add(hdr1);
        refPanel.add(hdr2);

        for (String[] row : bmiRef) {
            JLabel lbl1 = new JLabel("  " + row[0]);
            JLabel lbl2 = new JLabel("  " + row[1]);
            lbl1.setFont(new Font("SansSerif", Font.PLAIN, 11));
            lbl2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            refPanel.add(lbl1);
            refPanel.add(lbl2);
        }

        mainPanel.add(refPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
    public static void main(String[] args) {

    }
    
}
