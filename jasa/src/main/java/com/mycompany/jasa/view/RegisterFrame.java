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

        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setFont(new java.awt.Font("Georgia", 1, 25)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Daftar Pelanggan");
        titleLabel.setOpaque(true);

        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setText("Buat akun untuk mulai memakai layanan JSBJ.");
        subtitleLabel.setOpaque(true);

        namaLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        namaLabel.setForeground(new java.awt.Color(39, 50, 61));
        namaLabel.setText("Nama");

        namaField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        namaField.setForeground(new java.awt.Color(39, 50, 61));

        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(39, 50, 61));
        emailLabel.setText("Email");

        emailField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailField.setForeground(new java.awt.Color(39, 50, 61));

        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(39, 50, 61));
        passwordLabel.setText("Password (minimal 8 karakter)");

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordField.setForeground(new java.awt.Color(39, 50, 61));

        phoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        phoneLabel.setForeground(new java.awt.Color(39, 50, 61));
        phoneLabel.setText("No. telepon / WhatsApp");

        teleponField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        teleponField.setForeground(new java.awt.Color(39, 50, 61));

        registerButton.setBackground(new java.awt.Color(54, 132, 142));
        registerButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        registerButton.setForeground(new java.awt.Color(255, 255, 255));
        registerButton.setText("Daftar");
        registerButton.setOpaque(true);
        registerButton.addActionListener(this::registerButtonActionPerformed);

        backButton.setBackground(new java.awt.Color(232, 238, 243));
        backButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        backButton.setForeground(new java.awt.Color(39, 61, 83));
        backButton.setText("Kembali ke Login");
        backButton.setOpaque(true);
        backButton.addActionListener(this::backButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teleponField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(namaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(phoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(teleponField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

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
    private javax.swing.JButton backButton;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField namaField;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JTextField teleponField;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
