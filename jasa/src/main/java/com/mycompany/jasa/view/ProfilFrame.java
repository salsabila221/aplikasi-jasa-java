package com.mycompany.jasa.view;

import com.mycompany.jasa.dao.UserDAO;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
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
    private final UserDAO userDAO = new UserDAO();
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
            User fresh = userDAO.findById(user.getIdUser());
            if (fresh == null) {
                JOptionPane.showMessageDialog(this, "Akun tidak ditemukan.", "Profil",
                        JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }
            user.setNama(fresh.getNama());
            user.setEmail(fresh.getEmail());
            user.setNoTelepon(fresh.getNoTelepon());
            namaField.setText(fresh.getNama());
            emailField.setText(fresh.getEmail());
            teleponField.setText(fresh.getNoTelepon() == null ? "" : fresh.getNoTelepon());
        } catch (SQLException exception) {
            showDatabaseError(exception);
        }
    }

    private void saveProfile() {
        try {
            if (userDAO.updateProfile(user.getIdUser(), namaField.getText(), emailField.getText(),
                    teleponField.getText())) {
                user.setNama(namaField.getText().trim());
                user.setEmail(emailField.getText().trim());
                user.setNoTelepon(teleponField.getText().isBlank() ? null : teleponField.getText().trim());
                if (onProfileUpdated != null) onProfileUpdated.run();
                JOptionPane.showMessageDialog(this, "Profil berhasil diperbarui.");
            }
        } catch (SQLException | IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, "Profil gagal diperbarui: " + exception.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changePassword() {
        char[] current = currentPasswordField.getPassword();
        char[] next = newPasswordField.getPassword();
        char[] confirm = confirmPasswordField.getPassword();
        try {
            if (!Arrays.equals(next, confirm)) {
                JOptionPane.showMessageDialog(this, "Konfirmasi password baru belum cocok.",
                        "Periksa password", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (userDAO.changePassword(user.getIdUser(), current, next)) {
                currentPasswordField.setText("");
                newPasswordField.setText("");
                confirmPasswordField.setText("");
                JOptionPane.showMessageDialog(this, "Password berhasil diganti.");
            } else {
                JOptionPane.showMessageDialog(this, "Password saat ini salah.", "Gagal",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException | IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, "Password gagal diganti: " + exception.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(current, '\0');
            Arrays.fill(next, '\0');
            Arrays.fill(confirm, '\0');
        }
    }

    private void showDatabaseError(SQLException exception) {
        JOptionPane.showMessageDialog(this, "Tidak dapat memuat profil: " + exception.getMessage(),
                "Kesalahan database", JOptionPane.ERROR_MESSAGE);
    }
}
