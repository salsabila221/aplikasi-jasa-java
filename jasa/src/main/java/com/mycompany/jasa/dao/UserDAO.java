package com.mycompany.jasa.dao;

import com.mycompany.jasa.config.KoneksiDB;
import com.mycompany.jasa.model.User;
import com.mycompany.jasa.model.User.Role;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Operasi database untuk registrasi, autentikasi, profil, dan akun driver. */
public class UserDAO {

    private static final int HASH_ITERATIONS = 210_000;
    private static final int HASH_BITS = 256;
    private static final int SALT_BYTES = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    /** Registrasi publik selalu membuat akun Pelanggan. */
    public User registerCustomer(String nama, String email, char[] password, String noTelepon)
            throws SQLException {
        return insertUser(nama, email, password, noTelepon, Role.PELANGGAN);
    }

    /** Mengembalikan null jika email/password tidak cocok. */
    public User login(String email, char[] password) throws SQLException {
        String sql = "SELECT id_user, nama, email, password, no_telepon, role "
                + "FROM users WHERE email = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, normalizeEmail(email));
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next() || !verifyPassword(password, rs.getString("password"))) {
                    return null;
                }
                return mapUser(rs);
            }
        }
    }

    public User findById(int idUser) throws SQLException {
        String sql = "SELECT id_user, nama, email, no_telepon, role FROM users WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    /** Mengubah nama, email, dan nomor telepon tanpa mengubah role/password. */
    public boolean updateProfile(int idUser, String nama, String email, String noTelepon)
            throws SQLException {
        String sql = "UPDATE users SET nama = ?, email = ?, no_telepon = ? WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, requireText(nama, "Nama"));
            statement.setString(2, normalizeEmail(email));
            statement.setString(3, blankToNull(noTelepon));
            statement.setInt(4, idUser);
            return statement.executeUpdate() == 1;
        }
    }

    /** Admin dapat membuat akun Driver atau Admin; Pelanggan mendaftar melalui registrasi publik. */
    public User createUserByAdmin(int adminId, String nama, String email, char[] password,
            String noTelepon, Role role) throws SQLException {
        requireAdmin(adminId);
        if (role != Role.DRIVER && role != Role.ADMIN) {
            throw new IllegalArgumentException("Admin hanya dapat menambahkan Driver atau Admin.");
        }
        return insertUser(nama, email, password, noTelepon, role);
    }

    /** Admin dapat memperbarui data dan role akun; perubahan role akun sendiri ditolak. */
    public boolean updateUserByAdmin(int adminId, int targetUserId, String nama, String email,
            String noTelepon, Role role) throws SQLException {
        requireAdmin(adminId);
        if (role == null) throw new IllegalArgumentException("Role wajib dipilih.");
        if (adminId == targetUserId) {
            User admin = findById(adminId);
            if (admin.getRole() != role) {
                throw new IllegalArgumentException("Role Admin yang sedang login tidak dapat diubah.");
            }
        }
        String sql = "UPDATE users SET nama = ?, email = ?, no_telepon = ?, role = ? WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, requireText(nama, "Nama"));
            statement.setString(2, normalizeEmail(email));
            statement.setString(3, blankToNull(noTelepon));
            statement.setString(4, role.getDatabaseValue());
            statement.setInt(5, targetUserId);
            return statement.executeUpdate() == 1;
        }
    }

    /** Mengganti password setelah memverifikasi password lama pengguna. */
    public boolean changePassword(int userId, char[] currentPassword, char[] newPassword)
            throws SQLException {
        String selectSql = "SELECT password FROM users WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement select = connection.prepareStatement(selectSql)) {
            select.setInt(1, userId);
            try (ResultSet rs = select.executeQuery()) {
                if (!rs.next() || !verifyPassword(currentPassword, rs.getString("password"))) {
                    return false;
                }
            }
            try (PreparedStatement update = connection.prepareStatement(
                    "UPDATE users SET password = ? WHERE id_user = ?")) {
                update.setString(1, hashPassword(newPassword));
                update.setInt(2, userId);
                return update.executeUpdate() == 1;
            }
        }
    }

    /** CRUD admin: ambil data pengguna tanpa pernah mengambil hash password. */
    public java.util.List<User> findAll(int adminId) throws SQLException {
        requireAdmin(adminId);
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT id_user, nama, email, no_telepon, role FROM users ORDER BY id_user";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) users.add(mapUser(rs));
        }
        return users;
    }

    public boolean deleteUser(int adminId, int targetUserId) throws SQLException {
        requireAdmin(adminId);
        if (adminId == targetUserId) {
            throw new IllegalArgumentException("Admin tidak dapat menghapus akunnya sendiri.");
        }
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM users WHERE id_user = ?")) {
            statement.setInt(1, targetUserId);
            return statement.executeUpdate() == 1;
        }
    }

    private User insertUser(String nama, String email, char[] password, String noTelepon, Role role)
            throws SQLException {
        String sql = "INSERT INTO users (nama, email, password, no_telepon, role) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, requireText(nama, "Nama"));
            statement.setString(2, normalizeEmail(email));
            statement.setString(3, hashPassword(password));
            statement.setString(4, blankToNull(noTelepon));
            statement.setString(5, role.getDatabaseValue());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("ID pengguna baru tidak ditemukan.");
                return new User(keys.getInt(1), nama.trim(), normalizeEmail(email),
                        blankToNull(noTelepon), role);
            }
        }
    }

    private void requireAdmin(int adminId) throws SQLException {
        User admin = findById(adminId);
        if (admin == null || admin.getRole() != Role.ADMIN) {
            throw new SecurityException("Aksi ini hanya dapat dilakukan oleh Admin.");
        }
    }

    private static User mapUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id_user"), rs.getString("nama"), rs.getString("email"),
                rs.getString("no_telepon"), Role.fromDatabase(rs.getString("role")));
    }

    private static String hashPassword(char[] password) throws SQLException {
        if (password == null || password.length < 8) {
            throw new IllegalArgumentException("Password minimal 8 karakter.");
        }
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        byte[] hash = derive(password, salt, HASH_ITERATIONS);
        return "pbkdf2-sha256$" + HASH_ITERATIONS + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    private static boolean verifyPassword(char[] password, String stored) throws SQLException {
        if (password == null || stored == null) return false;
        try {
            String[] parts = stored.split("\\$", -1);
            if (parts.length != 4 || !"pbkdf2-sha256".equals(parts[0])) return false;
            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expected = Base64.getDecoder().decode(parts[3]);
            return MessageDigest.isEqual(expected, derive(password, salt, iterations));
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private static byte[] derive(char[] password, byte[] salt, int iterations) throws SQLException {
        try {
            KeySpec spec = new PBEKeySpec(password, salt, iterations, HASH_BITS);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException ex) {
            throw new SQLException("Tidak dapat memproses password.", ex);
        }
    }

    private static String normalizeEmail(String email) {
        return requireText(email, "Email").toLowerCase(java.util.Locale.ROOT);
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " wajib diisi.");
        return value.trim();
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
