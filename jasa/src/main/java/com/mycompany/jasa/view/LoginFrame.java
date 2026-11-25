package com.mycompany.jasa.view;



public class LoginFrame extends javax.swing.JFrame {


    public LoginFrame() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(244, 247, 250));
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - JSBJ");
        setResizable(false);

        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setFont(new java.awt.Font("Georgia", 1, 30)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("JSBJ");
        titleLabel.setOpaque(true);

        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setText("Jasa Serba Bisa Jatinangor  ·  Masuk ke akunmu");
        subtitleLabel.setOpaque(true);

        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(39, 50, 61));
        emailLabel.setText("Email");

        emailField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailField.setForeground(new java.awt.Color(39, 50, 61));

        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(39, 50, 61));
        passwordLabel.setText("Password");

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordField.setForeground(new java.awt.Color(39, 50, 61));

        loginButton.setBackground(new java.awt.Color(54, 132, 142));
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Login");
        loginButton.setOpaque(true);
        loginButton.addActionListener(this::loginButtonActionPerformed);

        registerButton.setBackground(new java.awt.Color(232, 238, 243));
        registerButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        registerButton.setForeground(new java.awt.Color(39, 61, 83));
        registerButton.setText("Daftar sebagai Pelanggan");
        registerButton.setOpaque(true);
        registerButton.addActionListener(this::registerButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final com.mycompany.jasa.controller.AuthController authController = new com.mycompany.jasa.controller.AuthController();

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        char[] rawPassword = passwordField.getPassword();
        try {
            com.mycompany.jasa.model.User user = authController.login(emailField.getText(), rawPassword);
            if (user == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Email atau password tidak cocok.",
                        "Login gagal", javax.swing.JOptionPane.WARNING_MESSAGE);
                passwordField.setText("");
                return;
            }
            dispose();
            switch (authController.destinationFor(user)) {
                case ADMIN -> new AdminDashboardFrame(user).setVisible(true);
                case PELANGGAN -> new PelangganDashboardFrame(user).setVisible(true);
                case DRIVER -> new DriverDashboardFrame(user).setVisible(true);
            }
        } catch (com.mycompany.jasa.controller.ControllerException exception) {
            javax.swing.JOptionPane.showMessageDialog(this, exception.getMessage(),
                    "Login gagal", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            java.util.Arrays.fill(rawPassword, '\0');
        }
    }

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new RegisterFrame().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
