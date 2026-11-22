package com.mycompany.jasa.controller;

import com.mycompany.jasa.dao.UserDAO;
import com.mycompany.jasa.model.User;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.UserCredential;
import java.sql.SQLException;

/** Mengatur validasi dan alur autentikasi serta registrasi pelanggan. */
public class AuthController {

    public enum Destination { ADMIN, PELANGGAN, DRIVER }

    private final UserDAO userDAO;

    public AuthController() {
        this(new UserDAO());
    }

    AuthController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /** Mengembalikan null jika kredensial tidak cocok. */
    public User login(String email, char[] password) {
        String normalizedEmail = ControllerSupport.validEmail(email);
        if (password == null || password.length == 0) {
            throw new ControllerException("Password wajib diisi.");
        }
        try {
            UserCredential credential = userDAO.findCredentialByEmail(normalizedEmail);
            if (credential == null
                    || !ControllerSupport.passwordMatches(password, credential.getPasswordHash())) {
                return null;
            }
            return credential.getUser();
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Login", exception);
        }
    }

    /** Resolves the post-login application flow without depending on Swing. */
    public Destination destinationFor(User user) {
        if (user == null || user.getRole() == null) {
            throw new ControllerException("Pengguna login tidak memiliki role yang valid.");
        }
        switch (user.getRole()) {
            case ADMIN: return Destination.ADMIN;
            case PELANGGAN: return Destination.PELANGGAN;
            case DRIVER: return Destination.DRIVER;
            default: throw new ControllerException("Role pengguna tidak didukung.");
        }
    }

    public User registerCustomer(String nama, String email, char[] password, String noTelepon) {
        String validName = ControllerSupport.validName(nama);
        String validEmail = ControllerSupport.validEmail(email);
        ControllerSupport.validPassword(password);
        String validPhone = ControllerSupport.validPhone(noTelepon);
        User customer = new User(0, validName, validEmail, validPhone, Role.PELANGGAN);
        String passwordHash = ControllerSupport.hashPassword(password);
        try {
            return userDAO.insert(customer, passwordHash);
        } catch (SQLException exception) {
            throw ControllerSupport.databaseError("Registrasi", exception);
        }
    }
}
