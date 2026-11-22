package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.AuthController;
import com.mycompany.jasa.controller.ControllerException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterFrame extends JFrame {

    private final AuthController authController = new AuthController();
    private final JTextField namaField = new JTextField(22);
    private final JTextField emailField = new JTextField(22);
    private final JPasswordField passwordField = new JPasswordField(22);
    private final JTextField teleponField = new JTextField(22);

    public RegisterFrame() {
        super("Daftar Pelanggan - JSBJ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel form = new JPanel(new GridLayout(0, 1, 7, 7));
        form.setBorder(BorderFactory.createEmptyBorder(22, 28, 16, 28));
        form.add(new JLabel("Nama")); form.add(namaField);
        form.add(new JLabel("Email")); form.add(emailField);
        form.add(new JLabel("Password (minimal 8 karakter)")); form.add(passwordField);
        form.add(new JLabel("No. telepon / WhatsApp")); form.add(teleponField);

        JButton registerButton = new JButton("Daftar");
        JButton backButton = new JButton("Kembali ke Login");
        registerButton.addActionListener(event -> register());
        backButton.addActionListener(event -> backToLogin());
        JPanel actions = new JPanel();
        actions.add(registerButton);
        actions.add(backButton);

        add(new JLabel("Registrasi Pelanggan", JLabel.CENTER), BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(registerButton);
        pack();
        setLocationRelativeTo(null);
    }

    private void register() {
        char[] password = passwordField.getPassword();
        try {
            authController.registerCustomer(namaField.getText(), emailField.getText(), password,
                    teleponField.getText());
            JOptionPane.showMessageDialog(this, "Akun Pelanggan berhasil dibuat. Silakan login.");
            backToLogin();
        } catch (ControllerException exception) {
            JOptionPane.showMessageDialog(this, "Registrasi gagal: " + exception.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private void backToLogin() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
