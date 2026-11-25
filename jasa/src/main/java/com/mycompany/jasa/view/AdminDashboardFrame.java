package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.UserController;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.User;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminDashboardFrame extends javax.swing.JFrame {

    private final User admin;
    private final UserController userController = new UserController();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][]{}, new String[]{"ID", "Nama", "Email", "No. Telepon", "Role"}) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };

    public AdminDashboardFrame(User admin) {
        this.admin = admin;
        initComponents();
        usersTable.setModel(tableModel);
        setLocationRelativeTo(null);
        refreshUsers();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        umkmButton = new javax.swing.JButton();
        transactionsButton = new javax.swing.JButton();
        profileButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        tableScrollPane = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard Admin - JSBJ");
        setResizable(false);

        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setFont(new java.awt.Font("Georgia", 1, 25)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Dashboard Admin");
        titleLabel.setOpaque(true);

        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setText("Kelola akun dan akses pengguna JSBJ.");
        subtitleLabel.setOpaque(true);

        addButton.setBackground(new java.awt.Color(54, 132, 142));
        addButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setText("Tambah Driver / Admin");
        addButton.setOpaque(true);
        addButton.addActionListener(this::addButtonActionPerformed);

        editButton.setBackground(new java.awt.Color(232, 238, 243));
        editButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        editButton.setForeground(new java.awt.Color(39, 61, 83));
        editButton.setText("Edit User");
        editButton.setOpaque(true);
        editButton.addActionListener(this::editButtonActionPerformed);

        deleteButton.setBackground(new java.awt.Color(232, 238, 243));
        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(39, 61, 83));
        deleteButton.setText("Hapus User");
        deleteButton.setOpaque(true);
        deleteButton.addActionListener(this::deleteButtonActionPerformed);

        refreshButton.setBackground(new java.awt.Color(232, 238, 243));
        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(39, 61, 83));
        refreshButton.setText("Muat Ulang");
        refreshButton.setOpaque(true);
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        umkmButton.setBackground(new java.awt.Color(232, 238, 243));
        umkmButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        umkmButton.setForeground(new java.awt.Color(39, 61, 83));
        umkmButton.setText("Kelola UMKM");
        umkmButton.setOpaque(true);
        umkmButton.addActionListener(this::umkmButtonActionPerformed);

        transactionsButton.setBackground(new java.awt.Color(232, 238, 243));
        transactionsButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        transactionsButton.setForeground(new java.awt.Color(39, 61, 83));
        transactionsButton.setText("Monitoring Transaksi");
        transactionsButton.setOpaque(true);
        transactionsButton.addActionListener(this::transactionsButtonActionPerformed);

        profileButton.setBackground(new java.awt.Color(232, 238, 243));
        profileButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        profileButton.setForeground(new java.awt.Color(39, 61, 83));
        profileButton.setText("Profil");
        profileButton.setOpaque(true);
        profileButton.addActionListener(this::profileButtonActionPerformed);

        logoutButton.setBackground(new java.awt.Color(232, 238, 243));
        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(39, 61, 83));
        logoutButton.setText("Logout");
        logoutButton.setOpaque(true);
        logoutButton.addActionListener(this::logoutButtonActionPerformed);

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Email", "No. Telepon", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableScrollPane.setViewportView(usersTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(subtitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tableScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(umkmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(transactionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(umkmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transactionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stylePrimaryButton(javax.swing.JButton button, String text) {
        styleButton(button, text);
        button.setForeground(java.awt.Color.WHITE);
        button.setBackground(new java.awt.Color(54, 132, 142));
    }

    private void styleButton(javax.swing.JButton button, String text) {
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        button.setForeground(new java.awt.Color(39, 61, 83));
        button.setBackground(new java.awt.Color(232, 238, 243));
        button.setOpaque(true);
        button.setText(text);
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
        } catch (ControllerException ex) {
            showError("Gagal menambahkan akun", ex);
        } finally {
            java.util.Arrays.fill(rawPassword, '\0');
        }
    }

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = usersTable.getSelectedRow();
        if (row < 0) { showMessage("Pilih user yang ingin diedit terlebih dahulu."); return; }
        int id = ((Number) tableModel.getValueAt(row, 0)).intValue();
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
        } catch (ControllerException ex) { showError("Gagal mengubah user", ex); }
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = usersTable.getSelectedRow();
        if (row < 0) { showMessage("Pilih user yang ingin dihapus terlebih dahulu."); return; }
        int id = ((Number) tableModel.getValueAt(row, 0)).intValue();
        int answer = JOptionPane.showConfirmDialog(this, "Hapus akun ini?", "Konfirmasi hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) return;
        try { userController.deleteManagedUser(admin, id); refreshUsers(); }
        catch (ControllerException ex) { showError("Gagal menghapus user", ex); }
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) { refreshUsers(); }
    private void umkmButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new KelolaLayananFrame(admin).setVisible(true);
    }
    private void transactionsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new LaporanAdminFrame(admin).setVisible(true);
    }
    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new ProfilFrame(admin, this::refreshUsers).setVisible(true);
    }
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new LoginFrame().setVisible(true);
    }

    private void refreshUsers() {
        try {
            List<User> users = userController.listUsers(admin);
            tableModel.setRowCount(0);
            for (User user : users) tableModel.addRow(new Object[]{user.getIdUser(), user.getNama(),
                user.getEmail(), user.getNoTelepon(), user.getRole().getDatabaseValue()});
        } catch (ControllerException ex) { showError("Gagal memuat daftar user", ex); }
    }

    private void showMessage(String message) { JOptionPane.showMessageDialog(this, message); }
    private void showError(String title, Exception ex) {
        JOptionPane.showMessageDialog(this, title + ": " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton profileButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton transactionsButton;
    private javax.swing.JButton umkmButton;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables
}
