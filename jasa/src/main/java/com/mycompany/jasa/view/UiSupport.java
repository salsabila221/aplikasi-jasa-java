package com.mycompany.jasa.view;

import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/** Komponen dan tema bersama untuk Dashboard Pelanggan dan Driver JSBJ. */
final class UiSupport {
    static final Color NAVY = new Color(38, 62, 85);       // mengikuti header form registrasi
    static final Color TEAL = new Color(58, 135, 146);    // mengikuti tombol form registrasi
    static final Color BACKGROUND = new Color(244, 247, 250);
    static final Color BORDER = new Color(216, 225, 232);
    static final Color TEXT = new Color(38, 54, 69);
    static final Color MUTED = new Color(103, 119, 133);
    static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 13);
    static final Font BOLD = new Font("SansSerif", Font.BOLD, 13);
    static final Font TITLE = new Font("Serif", Font.BOLD, 28);
    private static final DecimalFormat MONEY;
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", new Locale("id", "ID"));

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        MONEY = new DecimalFormat("'Rp' #,##0", symbols);
    }

    private UiSupport() { }

    static void installDefaults() {
        UIManager.put("TabbedPane.font", BOLD);
        UIManager.put("Label.font", NORMAL);
        UIManager.put("Button.font", BOLD);
        UIManager.put("TextField.font", NORMAL);
        UIManager.put("TextArea.font", NORMAL);
        UIManager.put("ComboBox.font", NORMAL);
        UIManager.put("Table.font", NORMAL);
        UIManager.put("Table.rowHeight", 29);
        UIManager.put("OptionPane.messageFont", NORMAL);
        UIManager.put("OptionPane.buttonFont", BOLD);
        UIManager.put("Panel.background", BACKGROUND);
    }

    static JPanel header(String pageTitle, String subtitle, User user, Runnable profile, Runnable logout) {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setBackground(NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 22));

        JPanel titleBox = new JPanel(new BorderLayout(0, 5));
        titleBox.setOpaque(false);
        JLabel brand = new JLabel("JSBJ  |  Jasa Serba Bisa Jatinangor");
        brand.setForeground(new Color(191, 224, 225));
        brand.setFont(new Font("SansSerif", Font.BOLD, 12));
        JLabel title = new JLabel(pageTitle);
        title.setForeground(Color.WHITE);
        title.setFont(TITLE);
        JLabel desc = new JLabel(subtitle);
        desc.setForeground(new Color(225, 234, 240));
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPanel lower = new JPanel(new BorderLayout(0, 3));
        lower.setOpaque(false);
        lower.add(title, BorderLayout.NORTH);
        lower.add(desc, BorderLayout.SOUTH);
        titleBox.add(brand, BorderLayout.NORTH);
        titleBox.add(lower, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        actions.setOpaque(false);
        JLabel greeting = new JLabel("Halo, " + (user == null ? "Pengguna" : user.getNama()));
        greeting.setForeground(Color.WHITE);
        greeting.setFont(BOLD);
        actions.add(greeting);
        JButton profileButton = button("Profil", false);
        profileButton.addActionListener(e -> profile.run());
        JButton logoutButton = button("Keluar", false);
        logoutButton.setBackground(new Color(74, 94, 112));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout.run());
        actions.add(profileButton);
        actions.add(logoutButton);

        header.add(titleBox, BorderLayout.CENTER);
        header.add(actions, BorderLayout.EAST);
        return header;
    }

    static JButton button(String caption, boolean primary) {
        JButton button = new JButton(caption);
        button.setFont(BOLD);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(primary ? TEAL : Color.WHITE);
        button.setForeground(primary ? Color.WHITE : NAVY);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primary ? TEAL : BORDER),
                BorderFactory.createEmptyBorder(9, 15, 9, 15)));
        return button;
    }

    static void styleTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(215, 235, 237));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(229, 235, 240));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new java.awt.Dimension(0, 1));
        table.setFont(NORMAL);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader header = table.getTableHeader();
        header.setFont(BOLD);
        header.setBackground(new Color(231, 239, 244));
        header.setForeground(NAVY);
        header.setReorderingAllowed(false);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setCellRenderer(center);
        }
    }

    static JScrollPane scroll(JTable table) {
        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createLineBorder(BORDER));
        pane.getViewport().setBackground(Color.WHITE);
        return pane;
    }

    static JPanel section(Component content, String headingText) {
        JPanel section = new JPanel(new BorderLayout(0, 10));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), BorderFactory.createEmptyBorder(13, 15, 13, 15)));
        JLabel heading = new JLabel(headingText);
        heading.setFont(BOLD);
        heading.setForeground(NAVY);
        section.add(heading, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);
        return section;
    }

    static String money(double value) { return MONEY.format(value); }
    static String date(LocalDateTime value) { return value == null ? "-" : DATE.format(value); }
}
