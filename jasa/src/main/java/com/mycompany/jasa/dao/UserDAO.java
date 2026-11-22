package com.mycompany.jasa.dao;

import com.mycompany.jasa.config.KoneksiDB;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.User;
import com.mycompany.jasa.model.UserCredential;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** DAO yang menangani query dan pemetaan data tabel users. */
public class UserDAO {

    private static final String USER_COLUMNS =
            "id_user, nama, email, no_telepon, role";
    private static final String CREDENTIAL_COLUMNS =
            "id_user, nama, email, password, no_telepon, role";

    public UserCredential findCredentialByEmail(String email) throws SQLException {
        String sql = "SELECT " + CREDENTIAL_COLUMNS + " FROM users WHERE email = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? mapCredential(rs) : null;
            }
        }
    }

    public UserCredential findCredentialById(int idUser) throws SQLException {
        String sql = "SELECT " + CREDENTIAL_COLUMNS + " FROM users WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? mapCredential(rs) : null;
            }
        }
    }

    public User findById(int idUser) throws SQLException {
        String sql = "SELECT " + USER_COLUMNS + " FROM users WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    public User insert(User user, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (nama, email, password, no_telepon, role) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            bindUser(statement, user);
            statement.setString(3, passwordHash);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("ID user baru tidak ditemukan.");
                return new User(keys.getInt(1), user.getNama(), user.getEmail(),
                        user.getNoTelepon(), user.getRole());
            }
        }
    }

    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET nama = ?, email = ?, no_telepon = ?, role = ? "
                + "WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getNama());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getNoTelepon());
            statement.setString(4, user.getRole().getDatabaseValue());
            statement.setInt(5, user.getIdUser());
            return statement.executeUpdate() == 1;
        }
    }

    public boolean updatePassword(int idUser, String passwordHash) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, passwordHash);
            statement.setInt(2, idUser);
            return statement.executeUpdate() == 1;
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT " + USER_COLUMNS + " FROM users ORDER BY id_user";
        List<User> users = new ArrayList<>();
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) users.add(mapUser(rs));
        }
        return users;
    }

    public boolean delete(int idUser) throws SQLException {
        String sql = "DELETE FROM users WHERE id_user = ?";
        try (Connection connection = KoneksiDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            return statement.executeUpdate() == 1;
        }
    }

    private static void bindUser(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getNama());
        statement.setString(2, user.getEmail());
        statement.setString(4, user.getNoTelepon());
        statement.setString(5, user.getRole().getDatabaseValue());
    }

    private static User mapUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id_user"), rs.getString("nama"), rs.getString("email"),
                rs.getString("no_telepon"), Role.fromDatabase(rs.getString("role")));
    }

    private static UserCredential mapCredential(ResultSet rs) throws SQLException {
        return new UserCredential(mapUser(rs), rs.getString("password"));
    }
}
