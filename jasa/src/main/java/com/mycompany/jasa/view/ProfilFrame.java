package com.mycompany.jasa.view;



public class ProfilFrame extends javax.swing.JFrame {


    public ProfilFrame(com.mycompany.jasa.model.User user, Runnable onProfileUpdated) {
        initComponents();
        this.user = user;
        this.onProfileUpdated = onProfileUpdated;
        getContentPane().setBackground(new java.awt.Color(244, 247, 250));
        loadProfile();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        namaLabel = new javax.swing.JLabel();
        namaField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        phoneLabel = new javax.swing.JLabel();
        teleponField = new javax.swing.JTextField();
        currentPasswordLabel = new javax.swing.JLabel();
        currentPasswordField = new javax.swing.JPasswordField();
        newPasswordLabel = new javax.swing.JLabel();
        newPasswordField = new javax.swing.JPasswordField();
        confirmPasswordLabel = new javax.swing.JLabel();
        confirmPasswordField = new javax.swing.JPasswordField();
        saveProfileButton = new javax.swing.JButton();
        changePasswordButton = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profil Saya - JSBJ");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        titleLabel.setFont(new java.awt.Font("Georgia", java.awt.Font.BOLD, 25));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setOpaque(true);
        titleLabel.setText("Profil Saya");
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setOpaque(true);
        subtitleLabel.setText("Perbarui informasi akun dan keamanan password.");
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        namaLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        namaLabel.setForeground(new java.awt.Color(39, 50, 61));
        namaLabel.setText("Nama");
        namaField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        namaField.setForeground(new java.awt.Color(39, 50, 61));
        namaField.setBackground(new java.awt.Color(255, 255, 255));
        emailLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        emailLabel.setForeground(new java.awt.Color(39, 50, 61));
        emailLabel.setText("Email");
        emailField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        emailField.setForeground(new java.awt.Color(39, 50, 61));
        emailField.setBackground(new java.awt.Color(255, 255, 255));
        phoneLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        phoneLabel.setForeground(new java.awt.Color(39, 50, 61));
        phoneLabel.setText("No. telepon / WhatsApp");
        teleponField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        teleponField.setForeground(new java.awt.Color(39, 50, 61));
        teleponField.setBackground(new java.awt.Color(255, 255, 255));
        currentPasswordLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        currentPasswordLabel.setForeground(new java.awt.Color(39, 50, 61));
        currentPasswordLabel.setText("Password saat ini");
        currentPasswordField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        currentPasswordField.setForeground(new java.awt.Color(39, 50, 61));
        currentPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        newPasswordLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        newPasswordLabel.setForeground(new java.awt.Color(39, 50, 61));
        newPasswordLabel.setText("Password baru (minimal 8 karakter)");
        newPasswordField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        newPasswordField.setForeground(new java.awt.Color(39, 50, 61));
        newPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        confirmPasswordLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        confirmPasswordLabel.setForeground(new java.awt.Color(39, 50, 61));
        confirmPasswordLabel.setText("Ulangi password baru");
        confirmPasswordField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        confirmPasswordField.setForeground(new java.awt.Color(39, 50, 61));
        confirmPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        saveProfileButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        saveProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        saveProfileButton.setBackground(new java.awt.Color(54, 132, 142));
        saveProfileButton.setOpaque(true);
        saveProfileButton.setText("Simpan Profil");
        changePasswordButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        changePasswordButton.setForeground(new java.awt.Color(39, 61, 83));
        changePasswordButton.setBackground(new java.awt.Color(232, 238, 243));
        changePasswordButton.setOpaque(true);
        changePasswordButton.setText("Ganti Password");
        saveProfileButton.addActionListener(this::saveProfileButtonActionPerformed);
        changePasswordButton.addActionListener(this::changePasswordButtonActionPerformed);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 64));
        getContentPane().add(subtitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 64, 560, 34));
        getContentPane().add(namaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 117, 400, 24));
        getContentPane().add(namaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 141, 470, 36));
        getContentPane().add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 185, 400, 24));
        getContentPane().add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 209, 470, 36));
        getContentPane().add(phoneLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 253, 400, 24));
        getContentPane().add(teleponField, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 277, 470, 36));
        getContentPane().add(currentPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 321, 400, 24));
        getContentPane().add(currentPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 345, 470, 36));
        getContentPane().add(newPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 389, 400, 24));
        getContentPane().add(newPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 413, 470, 36));
        getContentPane().add(confirmPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 457, 400, 24));
        getContentPane().add(confirmPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 481, 470, 36));
        getContentPane().add(saveProfileButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 543, 225, 42));
        getContentPane().add(changePasswordButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 543, 225, 42));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final com.mycompany.jasa.controller.UserController userController = new com.mycompany.jasa.controller.UserController();
    private final com.mycompany.jasa.model.User user;
    private final Runnable onProfileUpdated;

    private void saveProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            userController.updateProfile(user, namaField.getText(), emailField.getText(),
                    teleponField.getText());
            if (onProfileUpdated != null) onProfileUpdated.run();
            javax.swing.JOptionPane.showMessageDialog(this, "Profil berhasil diperbarui.");
        } catch (com.mycompany.jasa.controller.ControllerException exception) {
            showError("Profil gagal diperbarui", exception);
        }
    }

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        char[] current = currentPasswordField.getPassword();
        char[] next = newPasswordField.getPassword();
        char[] confirm = confirmPasswordField.getPassword();
        try {
            userController.changePassword(user, current, next, confirm);
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            javax.swing.JOptionPane.showMessageDialog(this, "Password berhasil diganti.");
        } catch (com.mycompany.jasa.controller.ControllerException exception) {
            showError("Password gagal diganti", exception);
        } finally {
            java.util.Arrays.fill(current, '\0');
            java.util.Arrays.fill(next, '\0');
            java.util.Arrays.fill(confirm, '\0');
        }
    }

    private void loadProfile() {
        try {
            com.mycompany.jasa.model.User fresh = userController.getProfile(user);
            namaField.setText(fresh.getNama());
            emailField.setText(fresh.getEmail());
            teleponField.setText(fresh.getNoTelepon() == null ? "" : fresh.getNoTelepon());
        } catch (com.mycompany.jasa.controller.ControllerException exception) {
            showError("Tidak dapat memuat profil", exception);
        }
    }

    private void showError(String title, com.mycompany.jasa.controller.ControllerException exception) {
        javax.swing.JOptionPane.showMessageDialog(this, title + ": " + exception.getMessage(),
                "Kesalahan", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JTextField namaField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField teleponField;
    private javax.swing.JLabel currentPasswordLabel;
    private javax.swing.JPasswordField currentPasswordField;
    private javax.swing.JLabel newPasswordLabel;
    private javax.swing.JPasswordField newPasswordField;
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JButton saveProfileButton;
    private javax.swing.JButton changePasswordButton;
    // End of variables declaration//GEN-END:variables
}
