package com.mycompany.jasa.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/** Shared presentation helpers only; business rules remain in controllers. */
final class SwingViewSupport {
    static final Color NAVY = new Color(39, 61, 83);
    static final Color TEAL = new Color(54, 132, 142);
    static final Color BACKGROUND = new Color(244, 247, 250);
    static final Color PALE = new Color(232, 238, 243);
    private SwingViewSupport() { }

    static JPanel page() {
        JPanel panel = new JPanel(new java.awt.BorderLayout(12, 12));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(18, 20, 20, 20));
        return panel;
    }
    static JPanel header(String title, String subtitle) {
        JPanel panel = new JPanel(new java.awt.GridLayout(2, 1, 0, 2));
        panel.setBackground(NAVY);
        panel.setBorder(new EmptyBorder(10, 16, 10, 16));
        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Georgia", Font.BOLD, 23));
        heading.setForeground(Color.WHITE);
        JLabel detail = new JLabel(subtitle);
        detail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        detail.setForeground(Color.WHITE);
        panel.add(heading); panel.add(detail);
        return panel;
    }
    static JButton button(String label, boolean primary) {
        JButton button = new JButton(label);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(primary ? Color.WHITE : NAVY);
        button.setBackground(primary ? TEAL : PALE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        return button;
    }
    static JTable table(String... columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        return table;
    }
    static DefaultTableModel model(JTable table) { return (DefaultTableModel) table.getModel(); }
    static String money(double amount) { return String.format(java.util.Locale.forLanguageTag("id-ID"), "Rp %,.0f", amount); }
}
