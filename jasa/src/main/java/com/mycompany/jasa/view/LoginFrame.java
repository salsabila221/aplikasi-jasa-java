package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.AuthController;
import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginFrame extends JFrame {

    private final AuthController authController = new AuthController();
    private final JTextField emailField = new JTextField(22);
    private final JPasswordField passwordField = new JPasswordField(22);

    public LoginFrame() {
        super("Login - JSBJ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(24, 28, 16, 28));
        form.add(new JLabel("Email"));
        form.add(emailField);
        form.add(new JLabel("Password"));
        form.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Daftar sebagai Pelanggan");
        loginButton.addActionListener(event -> login());
        registerButton.addActionListener(event -> {
            dispose();
            new RegisterFrame().setVisible(true);
        });

        JPanel actions = new JPanel();
        actions.add(loginButton);
        actions.add(registerButton);
        add(new JLabel("Jasa Serba Bisa Jatinangor", JLabel.CENTER), BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(loginButton);
        pack();
        setLocationRelativeTo(null);
    }

    private void login() {
        char[] password = passwordField.getPassword();
        try {
            User user = authController.login(emailField.getText(), password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Email atau password tidak cocok.",
                        "Login gagal", JOptionPane.WARNING_MESSAGE);
                passwordField.setText("");
                return;
            }
            dispose();
            switch (authController.destinationFor(user)) {
                case ADMIN -> new AdminDashboardFrame(user).setVisible(true);
                case PELANGGAN -> new PelangganDashboardFrame(user).setVisible(true);
                case DRIVER -> new DriverDashboardFrame(user).setVisible(true);
            }
        } catch (ControllerException exception) {
            JOptionPane.showMessageDialog(this, "Login gagal: " + exception.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
