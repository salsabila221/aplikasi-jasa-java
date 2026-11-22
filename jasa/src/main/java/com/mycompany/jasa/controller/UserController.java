package com.mycompany.jasa.controller;

import com.mycompany.jasa.dao.UserDAO;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.User;
import com.mycompany.jasa.model.UserCredential;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/** Mengatur validasi profil dan hak akses CRUD pengguna untuk Admin. */
public class UserController {

    private final UserDAO userDAO;

    public UserController() {
        this(new UserDAO());
    }

    UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getProfile(User signedInUser) {
        requireSignedIn(signedInUser);
        try {
            User user = userDAO.findById(signedInUser.getIdUser());
            if (user == null) throw new ControllerException("Akun tidak ditemukan.");
            return user;
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Memuat profil", exception);
        }
    }

    public User updateProfile(User signedInUser, String nama, String email, String noTelepon) {
        requireSignedIn(signedInUser);
        User updated = getProfile(signedInUser);
        updated.setNama(ControllerSupport.validName(nama));
        updated.setEmail(ControllerSupport.validEmail(email));
        updated.setNoTelepon(ControllerSupport.validPhone(noTelepon));
        try {
            if (!userDAO.update(updated)) throw new ControllerException("Profil tidak berhasil diperbarui.");
            signedInUser.setNama(updated.getNama());
            signedInUser.setEmail(updated.getEmail());
            signedInUser.setNoTelepon(updated.getNoTelepon());
            return updated;
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Pembaruan profil", exception);
        }
    }

    public void changePassword(User signedInUser, char[] currentPassword,
            char[] newPassword, char[] confirmation) {
        requireSignedIn(signedInUser);
        ControllerSupport.validPassword(newPassword);
        if (!Arrays.equals(newPassword, confirmation)) {
            throw new ControllerException("Konfirmasi password baru belum cocok.");
        }
        try {
            UserCredential credential = userDAO.findCredentialById(signedInUser.getIdUser());
            if (credential == null) throw new ControllerException("Akun tidak ditemukan.");
            if (!ControllerSupport.passwordMatches(currentPassword, credential.getPasswordHash())) {
                throw new ControllerException("Password saat ini salah.");
            }
            if (!userDAO.updatePassword(signedInUser.getIdUser(),
                    ControllerSupport.hashPassword(newPassword))) {
                throw new ControllerException("Password tidak berhasil diperbarui.");
            }
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Penggantian password", exception);
        }
    }

    public List<User> listUsers(User actor) {
        requireAdmin(actor);
        try {
            return userDAO.findAll();
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Memuat daftar pengguna", exception);
        }
    }

    public User createManagedUser(User actor, String nama, String email, char[] password,
            String noTelepon, Role role) {
        requireAdmin(actor);
        Role validRole = ControllerSupport.requiredRole(role);
        if (validRole != Role.DRIVER && validRole != Role.ADMIN) {
            throw new ControllerException("Admin hanya dapat menambahkan Driver atau Admin.");
        }
        String validName = ControllerSupport.validName(nama);
        String validEmail = ControllerSupport.validEmail(email);
        ControllerSupport.validPassword(password);
        String validPhone = ControllerSupport.validPhone(noTelepon);
        User newUser = new User(0, validName, validEmail, validPhone, validRole);
        try {
            return userDAO.insert(newUser, ControllerSupport.hashPassword(password));
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Penambahan pengguna", exception);
        }
    }

    public User updateManagedUser(User actor, int userId, String nama, String email,
            String noTelepon, Role role) {
        requireAdmin(actor);
        Role validRole = ControllerSupport.requiredRole(role);
        User updated;
        try {
            updated = userDAO.findById(userId);
            if (updated == null) throw new ControllerException("User yang dipilih tidak ditemukan.");
            if (actor.getIdUser() == userId && validRole != Role.ADMIN) {
                throw new ControllerException("Role Admin yang sedang login tidak dapat diubah.");
            }
            updated.setNama(ControllerSupport.validName(nama));
            updated.setEmail(ControllerSupport.validEmail(email));
            updated.setNoTelepon(ControllerSupport.validPhone(noTelepon));
            updated.setRole(validRole);
            if (!userDAO.update(updated)) throw new ControllerException("Data user tidak berhasil diperbarui.");
            if (actor.getIdUser() == userId) {
                actor.setNama(updated.getNama());
                actor.setEmail(updated.getEmail());
                actor.setNoTelepon(updated.getNoTelepon());
            }
            return updated;
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Pembaruan user", exception);
        }
    }

    public void deleteManagedUser(User actor, int userId) {
        requireAdmin(actor);
        if (actor.getIdUser() == userId) {
            throw new ControllerException("Admin tidak dapat menghapus akunnya sendiri.");
        }
        try {
            if (!userDAO.delete(userId)) throw new ControllerException("User tidak ditemukan.");
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Penghapusan user", exception);
        }
    }

    private void requireSignedIn(User actor) {
        if (actor == null || actor.getIdUser() <= 0) {
            throw new ControllerException("Sesi pengguna tidak valid. Silakan login kembali.");
        }
    }

    private void requireAdmin(User actor) {
        requireSignedIn(actor);
        try {
            User currentActor = userDAO.findById(actor.getIdUser());
            if (currentActor == null || currentActor.getRole() != Role.ADMIN) {
                throw new ControllerException("Fitur ini hanya dapat digunakan oleh Admin.");
            }
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Verifikasi hak akses", exception);
        }
    }
}
