package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.UserController;
import com.mycompany.jasa.model.User;
import com.mycompany.jasa.model.Role;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminDashboardFrame extends JFrame {

    private final User admin;
    private final UserController userController = new UserController();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nama", "Email", "No. Telepon", "Role"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable usersTable = new JTable(tableModel);

    public AdminDashboardFrame(User admin) {
        super("Dashboard Admin - JSBJ");
        this.admin = admin;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addButton = new JButton("Tambah Driver / Admin");
        JButton editButton = new JButton("Edit User Terpilih");
        JButton deleteButton = new JButton("Hapus User Terpilih");
        JButton umkmButton = new JButton("Kelola UMKM (segera hadir)");
        JButton transactionsButton = new JButton("Monitoring Transaksi (segera hadir)");
        JButton refreshButton = new JButton("Muat Ulang");
        JButton logoutButton = new JButton("Logout");

        addButton.addActionListener(event -> addUser());
        editButton.addActionListener(event -> editSelectedUser());
        deleteButton.addActionListener(event -> deleteSelectedUser());
        umkmButton.addActionListener(event -> showPlaceholder("Kelola UMKM"));
        transactionsButton.addActionListener(event -> showPlaceholder("Monitoring Transaksi"));
        refreshButton.addActionListener(event -> refreshUsers());
        logoutButton.addActionListener(event -> logout());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(addButton);
        actions.add(editButton);
        actions.add(deleteButton);
        actions.add(refreshButton);
        JPanel modules = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        modules.add(umkmButton);
        modules.add(transactionsButton);
        modules.add(logoutButton);
        JPanel top = new JPanel(new BorderLayout());
        top.add(actions, BorderLayout.WEST);
        top.add(modules, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(usersTable), BorderLayout.CENTER);
        setSize(1050, 540);
        setLocationRelativeTo(null);
        refreshUsers();
    }

    private void refreshUsers() {
        try {
            List<User> users = userController.listUsers(admin);
            tableModel.setRowCount(0);
            for (User user : users) {
                tableModel.addRow(new Object[]{user.getIdUser(), user.getNama(), user.getEmail(),
                        user.getNoTelepon(), user.getRole().getDatabaseValue()});
            }
        } catch (ControllerException exception) {
            showError("Gagal memuat daftar user", exception);
        }
    }

    private void addUser() {
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        javax.swing.JPasswordField password = new javax.swing.JPasswordField();
        JComboBox<Role> role = new JComboBox<>(new Role[]{Role.DRIVER, Role.ADMIN});
        Object[] fields = {"Nama", name, "Email", email, "No. Telepon", phone,
                "Password (minimal 8 karakter)", password, "Role", role};
        if (JOptionPane.showConfirmDialog(this, fields, "Tambah akun", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;
        char[] rawPassword = password.getPassword();
        try {
            userController.createManagedUser(admin, name.getText(), email.getText(), rawPassword,
                    phone.getText(), (Role) role.getSelectedItem());
            refreshUsers();
            JOptionPane.showMessageDialog(this, "Akun berhasil ditambahkan.");
        } catch (ControllerException exception) {
            showError("Gagal menambahkan akun", exception);
        } finally {
            java.util.Arrays.fill(rawPassword, '\0');
            password.setText("");
        }
    }

    private void editSelectedUser() {
        int row = usersTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin diedit terlebih dahulu.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        JTextField name = new JTextField(String.valueOf(tableModel.getValueAt(row, 1)));
        JTextField email = new JTextField(String.valueOf(tableModel.getValueAt(row, 2)));
        Object phoneValue = tableModel.getValueAt(row, 3);
        JTextField phone = new JTextField(phoneValue == null ? "" : String.valueOf(phoneValue));
        JComboBox<Role> role = new JComboBox<>(Role.values());
        role.setSelectedItem(Role.fromDatabase(String.valueOf(tableModel.getValueAt(row, 4))));
        Object[] fields = {"Nama", name, "Email", email, "No. Telepon", phone, "Role", role};
        if (JOptionPane.showConfirmDialog(this, fields, "Edit user", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;
        try {
            userController.updateManagedUser(admin, id, name.getText(), email.getText(),
                    phone.getText(), (Role) role.getSelectedItem());
            refreshUsers();
        } catch (ControllerException exception) {
            showError("Gagal mengubah user", exception);
        }
    }

    private void deleteSelectedUser() {
        int row = usersTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus terlebih dahulu.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = String.valueOf(tableModel.getValueAt(row, 1));
        int answer = JOptionPane.showConfirmDialog(this,
                "Hapus akun " + name + "? Pesanan milik Pelanggan juga dapat ikut terhapus.",
                "Konfirmasi hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) return;
        try {
            userController.deleteManagedUser(admin, id);
            refreshUsers();
        } catch (ControllerException exception) {
            showError("Gagal menghapus user", exception);
        }
    }

    private void showPlaceholder(String feature) {
        JOptionPane.showMessageDialog(this, feature + " akan dihubungkan dengan modul Jilan.");
    }

    private void showError(String title, Exception exception) {
        JOptionPane.showMessageDialog(this, title + ": " + exception.getMessage(),
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
