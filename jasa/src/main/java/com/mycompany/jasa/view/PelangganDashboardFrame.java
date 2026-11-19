package com.mycompany.jasa.view;

import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Dashboard sementara Pelanggan sampai modul dashboard utama siap. */
public class PelangganDashboardFrame extends JFrame {

    private final User user;
    private final JLabel welcomeLabel = new JLabel();

    public PelangganDashboardFrame(User user) {
        super("Dashboard Pelanggan - JSBJ");
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        refreshWelcome();
        add(welcomeLabel, BorderLayout.CENTER);

        JButton profileButton = new JButton("Buka Profil");
        JButton logoutButton = new JButton("Logout");
        profileButton.addActionListener(event -> new ProfilFrame(user, this::refreshWelcome).setVisible(true));
        logoutButton.addActionListener(event -> logout());
        JPanel actions = new JPanel(new FlowLayout());
        actions.add(profileButton);
        actions.add(logoutButton);
        add(actions, BorderLayout.SOUTH);
        setSize(460, 180);
        setLocationRelativeTo(null);
    }

    private void refreshWelcome() {
        welcomeLabel.setText("Selamat datang, " + user.getNama() + " (Pelanggan)");
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
