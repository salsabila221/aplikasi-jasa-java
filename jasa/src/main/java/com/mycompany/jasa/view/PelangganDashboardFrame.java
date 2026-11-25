package com.mycompany.jasa.view;



public class PelangganDashboardFrame extends javax.swing.JFrame {


    public PelangganDashboardFrame(com.mycompany.jasa.model.User user) {
        initComponents();
        this.user = user;
        getContentPane().setBackground(new java.awt.Color(244, 247, 250));
        refreshWelcome();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        welcomeLabel = new javax.swing.JLabel();
        profileButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard Pelanggan - JSBJ");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        titleLabel.setFont(new java.awt.Font("Georgia", java.awt.Font.BOLD, 25));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setOpaque(true);
        titleLabel.setText("Ruang Pelanggan");
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setOpaque(true);
        subtitleLabel.setText("Temukan layanan lokal yang praktis di Jatinangor.");
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        welcomeLabel.setForeground(new java.awt.Color(39, 61, 83));
        welcomeLabel.setText("Selamat datang");
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profileButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        profileButton.setForeground(new java.awt.Color(255, 255, 255));
        profileButton.setBackground(new java.awt.Color(54, 132, 142));
        profileButton.setOpaque(true);
        profileButton.setText("Buka Profil");
        logoutButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        logoutButton.setForeground(new java.awt.Color(39, 61, 83));
        logoutButton.setBackground(new java.awt.Color(232, 238, 243));
        logoutButton.setOpaque(true);
        logoutButton.setText("Logout");
        profileButton.addActionListener(this::profileButtonActionPerformed);
        logoutButton.addActionListener(this::logoutButtonActionPerformed);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 66));
        getContentPane().add(subtitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 66, 520, 36));
        getContentPane().add(welcomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 150, 440, 52));
        getContentPane().add(profileButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 225, 210, 42));
        getContentPane().add(logoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 225, 210, 42));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final com.mycompany.jasa.model.User user;

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new ProfilFrame(user, () -> refreshWelcome()).setVisible(true);
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new LoginFrame().setVisible(true);
    }

    private void refreshWelcome() {
        welcomeLabel.setText("Selamat datang, " + user.getNama() + " (Pelanggan)");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JButton profileButton;
    private javax.swing.JButton logoutButton;
    // End of variables declaration//GEN-END:variables
}
