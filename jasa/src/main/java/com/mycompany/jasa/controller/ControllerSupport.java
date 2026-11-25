package com.mycompany.jasa.controller;

import com.mycompany.jasa.model.Role;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

final class ControllerSupport {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final int HASH_ITERATIONS = 210_000;
    private static final int HASH_BITS = 256;
    private static final int SALT_BYTES = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    private ControllerSupport() { }

    static String validName(String name) {
        if (name == null || name.isBlank()) throw new ControllerException("Nama wajib diisi.");
        return name.trim();
    }

    static String validEmail(String email) {
        if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ControllerException("Masukkan alamat email yang valid.");
        }
        return email.trim().toLowerCase(Locale.ROOT);
    }

    static String validPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new ControllerException("Nomor telepon/WhatsApp wajib diisi.");
        }
        String normalized = phone.trim();
        if (normalized.length() > 15) {
            throw new ControllerException("Nomor telepon maksimal 15 karakter.");
        }
        return normalized;
    }

    static void validPassword(char[] password) {
        if (password == null || password.length < 8) {
            throw new ControllerException("Password minimal 8 karakter.");
        }
    }

    static String hashPassword(char[] password) {
        validPassword(password);
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        byte[] hash = derive(password, salt, HASH_ITERATIONS);
        return "pbkdf2-sha256$" + HASH_ITERATIONS + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    static boolean passwordMatches(char[] password, String storedHash) {
        if (password == null || storedHash == null) return false;
        try {
            String[] parts = storedHash.split("\\$", -1);
            if (parts.length != 4 || !"pbkdf2-sha256".equals(parts[0])) return false;
            int iterations = Integer.parseInt(parts[1]);
            if (iterations < 1 || iterations > 2_000_000) return false;
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expected = Base64.getDecoder().decode(parts[3]);
            return MessageDigest.isEqual(expected, derive(password, salt, iterations));
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    static Role requiredRole(Role role) {
        if (role == null) throw new ControllerException("Role wajib dipilih.");
        return role;
    }

    static ControllerException databaseError(String action, SQLException exception) {
        if ("23000".equals(exception.getSQLState()) || exception.getErrorCode() == 1062) {
            return new ControllerException("Email tersebut sudah digunakan.", exception);
        }
        return new ControllerException(action + " gagal karena masalah database.", exception);
    }

    private static byte[] derive(char[] password, byte[] salt, int iterations) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, HASH_BITS);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException exception) {
            throw new ControllerException("Password tidak dapat diproses.", exception);
        } finally {
            spec.clearPassword();
        }
    }
}
