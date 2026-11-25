package com.mycompany.jasa.view;



public class DriverDashboardFrame extends javax.swing.JFrame {


    public DriverDashboardFrame(com.mycompany.jasa.model.User user) {
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
        ordersButton = new javax.swing.JButton();
        profileButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard Driver - JSBJ");
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Georgia", 1, 25)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setBackground(new java.awt.Color(39, 61, 83));
        titleLabel.setOpaque(true);
        titleLabel.setText("Ruang Driver");
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        subtitleLabel.setBackground(new java.awt.Color(39, 61, 83));
        subtitleLabel.setOpaque(true);
        subtitleLabel.setText("Panel sederhana untuk akun pengemudi JSBJ.");
        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        welcomeLabel.setForeground(new java.awt.Color(39, 61, 83));
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setText("Selamat datang");

        ordersButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ordersButton.setForeground(new java.awt.Color(255, 255, 255));
        ordersButton.setBackground(new java.awt.Color(54, 132, 142));
        ordersButton.setOpaque(true);
        ordersButton.setText("Order Masuk, Progres Pengantaran & Penghasilan");
        ordersButton.addActionListener(this::ordersButtonActionPerformed);

        profileButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        profileButton.setForeground(new java.awt.Color(255, 255, 255));
        profileButton.setBackground(new java.awt.Color(54, 132, 142));
        profileButton.setOpaque(true);
        profileButton.setText("Buka Profil");
        profileButton.addActionListener(this::profileButtonActionPerformed);

        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(39, 61, 83));
        logoutButton.setBackground(new java.awt.Color(232, 238, 243));
        logoutButton.setOpaque(true);
        logoutButton.setText("Logout");
        logoutButton.addActionListener(this::logoutButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ordersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(ordersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final com.mycompany.jasa.model.User user;

    private void ordersButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new DriverOrdersFrame(user).setVisible(true);
    }

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new ProfilFrame(user, () -> refreshWelcome()).setVisible(true);
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new LoginFrame().setVisible(true);
    }

    private void refreshWelcome() {
        welcomeLabel.setText("Selamat datang, " + user.getNama() + " (Driver)");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton ordersButton;
    private javax.swing.JButton profileButton;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
