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

        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setFont(new java.awt.Font("Georgia", 1, 25)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Profil Saya");
        titleLabel.setOpaque(true);

        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setText("Perbarui informasi akun dan keamanan password.");
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

        phoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        phoneLabel.setForeground(new java.awt.Color(39, 50, 61));
        phoneLabel.setText("No. telepon / WhatsApp");

        teleponField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        teleponField.setForeground(new java.awt.Color(39, 50, 61));

        currentPasswordLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        currentPasswordLabel.setForeground(new java.awt.Color(39, 50, 61));
        currentPasswordLabel.setText("Password saat ini");

        currentPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        currentPasswordField.setForeground(new java.awt.Color(39, 50, 61));

        newPasswordLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        newPasswordLabel.setForeground(new java.awt.Color(39, 50, 61));
        newPasswordLabel.setText("Password baru (minimal 8 karakter)");

        newPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newPasswordField.setForeground(new java.awt.Color(39, 50, 61));

        confirmPasswordLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        confirmPasswordLabel.setForeground(new java.awt.Color(39, 50, 61));
        confirmPasswordLabel.setText("Ulangi password baru");

        confirmPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        confirmPasswordField.setForeground(new java.awt.Color(39, 50, 61));

        saveProfileButton.setBackground(new java.awt.Color(54, 132, 142));
        saveProfileButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        saveProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        saveProfileButton.setText("Simpan Profil");
        saveProfileButton.setOpaque(true);
        saveProfileButton.addActionListener(this::saveProfileButtonActionPerformed);

        changePasswordButton.setBackground(new java.awt.Color(232, 238, 243));
        changePasswordButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        changePasswordButton.setForeground(new java.awt.Color(39, 61, 83));
        changePasswordButton.setText("Ganti Password");
        changePasswordButton.setOpaque(true);
        changePasswordButton.addActionListener(this::changePasswordButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teleponField, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(changePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(namaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(phoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(teleponField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(currentPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(currentPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(newPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(confirmPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

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
    private javax.swing.JButton changePasswordButton;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JPasswordField currentPasswordField;
    private javax.swing.JLabel currentPasswordLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField namaField;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JPasswordField newPasswordField;
    private javax.swing.JLabel newPasswordLabel;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JButton saveProfileButton;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JTextField teleponField;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
