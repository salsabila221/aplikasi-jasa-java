package com.mycompany.jasa.view;



public class RegisterFrame extends javax.swing.JFrame {


    public RegisterFrame() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(244, 247, 250));
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
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        phoneLabel = new javax.swing.JLabel();
        teleponField = new javax.swing.JTextField();
        registerButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Daftar Pelanggan - JSBJ");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        titleLabel.setFont(new java.awt.Font("Georgia", java.awt.Font.BOLD, 25));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setOpaque(true);
        titleLabel.setText("Daftar Pelanggan");
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setOpaque(true);
        subtitleLabel.setText("Buat akun untuk mulai memakai layanan JSBJ.");
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
        passwordLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        passwordLabel.setForeground(new java.awt.Color(39, 50, 61));
        passwordLabel.setText("Password (minimal 8 karakter)");
        passwordField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        passwordField.setForeground(new java.awt.Color(39, 50, 61));
        passwordField.setBackground(new java.awt.Color(255, 255, 255));
        phoneLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        phoneLabel.setForeground(new java.awt.Color(39, 50, 61));
        phoneLabel.setText("No. telepon / WhatsApp");
        teleponField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        teleponField.setForeground(new java.awt.Color(39, 50, 61));
        teleponField.setBackground(new java.awt.Color(255, 255, 255));
        registerButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        registerButton.setForeground(new java.awt.Color(255, 255, 255));
        registerButton.setBackground(new java.awt.Color(54, 132, 142));
        registerButton.setOpaque(true);
        registerButton.setText("Daftar");
        backButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        backButton.setForeground(new java.awt.Color(39, 61, 83));
        backButton.setBackground(new java.awt.Color(232, 238, 243));
        backButton.setOpaque(true);
        backButton.setText("Kembali ke Login");
        registerButton.addActionListener(this::registerButtonActionPerformed);
        backButton.addActionListener(this::backButtonActionPerformed);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 68));
        getContentPane().add(subtitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 68, 520, 36));
        getContentPane().add(namaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 125, 400, 24));
        getContentPane().add(namaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 150, 420, 36));
        getContentPane().add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 196, 400, 24));
        getContentPane().add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 221, 420, 36));
        getContentPane().add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 267, 400, 24));
        getContentPane().add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 292, 420, 36));
        getContentPane().add(phoneLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 338, 400, 24));
        getContentPane().add(teleponField, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 363, 420, 36));
        getContentPane().add(registerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 425, 420, 42));
        getContentPane().add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 479, 420, 38));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final com.mycompany.jasa.controller.AuthController authController = new com.mycompany.jasa.controller.AuthController();

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        char[] rawPassword = passwordField.getPassword();
        try {
            authController.registerCustomer(namaField.getText(), emailField.getText(),
                    rawPassword, teleponField.getText());
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Akun Pelanggan berhasil dibuat. Silakan login.");
            backToLogin();
        } catch (com.mycompany.jasa.controller.ControllerException exception) {
            javax.swing.JOptionPane.showMessageDialog(this, exception.getMessage(),
                    "Registrasi gagal", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            java.util.Arrays.fill(rawPassword, '\0');
        }
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        backToLogin();
    }

    private void backToLogin() {
        dispose();
        new LoginFrame().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JTextField namaField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField teleponField;
    private javax.swing.JButton registerButton;
    private javax.swing.JButton backButton;
    // End of variables declaration//GEN-END:variables
}
