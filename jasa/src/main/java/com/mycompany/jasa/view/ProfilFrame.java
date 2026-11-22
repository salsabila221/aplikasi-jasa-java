package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.UserController;
import com.mycompany.jasa.model.User;
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

public class ProfilFrame extends JFrame {

    private final User user;
    private final Runnable onProfileUpdated;
    private final UserController userController = new UserController();
    private final JTextField namaField = new JTextField(24);
    private final JTextField emailField = new JTextField(24);
    private final JTextField teleponField = new JTextField(24);
    private final JPasswordField currentPasswordField = new JPasswordField(24);
    private final JPasswordField newPasswordField = new JPasswordField(24);
    private final JPasswordField confirmPasswordField = new JPasswordField(24);

    public ProfilFrame(User user, Runnable onProfileUpdated) {
        super("Profil Saya - JSBJ");
        this.user = user;
        this.onProfileUpdated = onProfileUpdated;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(18, 26, 12, 26));
        form.add(new JLabel("Nama")); form.add(namaField);
        form.add(new JLabel("Email")); form.add(emailField);
        form.add(new JLabel("No. telepon / WhatsApp")); form.add(teleponField);
        form.add(new JLabel("Password saat ini (untuk ganti password)")); form.add(currentPasswordField);
        form.add(new JLabel("Password baru (minimal 8 karakter)")); form.add(newPasswordField);
        form.add(new JLabel("Ulangi password baru")); form.add(confirmPasswordField);
        loadProfile();

        JButton saveProfileButton = new JButton("Simpan Profil");
        JButton changePasswordButton = new JButton("Ganti Password");
        saveProfileButton.addActionListener(event -> saveProfile());
        changePasswordButton.addActionListener(event -> changePassword());
        JPanel actions = new JPanel();
        actions.add(saveProfileButton);
        actions.add(changePasswordButton);
        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    private void loadProfile() {
        try {
            User fresh = userController.getProfile(user);
            namaField.setText(fresh.getNama());
            emailField.setText(fresh.getEmail());
            teleponField.setText(fresh.getNoTelepon() == null ? "" : fresh.getNoTelepon());
        } catch (ControllerException exception) {
            showError("Tidak dapat memuat profil", exception);
        }
    }

    private void saveProfile() {
        try {
            userController.updateProfile(user, namaField.getText(), emailField.getText(),
                    teleponField.getText());
            if (onProfileUpdated != null) onProfileUpdated.run();
            JOptionPane.showMessageDialog(this, "Profil berhasil diperbarui.");
        } catch (ControllerException exception) {
            showError("Profil gagal diperbarui", exception);
        }
    }

    private void changePassword() {
        char[] current = currentPasswordField.getPassword();
        char[] next = newPasswordField.getPassword();
        char[] confirm = confirmPasswordField.getPassword();
        try {
            userController.changePassword(user, current, next, confirm);
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            JOptionPane.showMessageDialog(this, "Password berhasil diganti.");
        } catch (ControllerException exception) {
            showError("Password gagal diganti", exception);
        } finally {
            Arrays.fill(current, '\0');
            Arrays.fill(next, '\0');
            Arrays.fill(confirm, '\0');
        }
    }

    private void showError(String title, ControllerException exception) {
        JOptionPane.showMessageDialog(this, title + ": " + exception.getMessage(),
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
