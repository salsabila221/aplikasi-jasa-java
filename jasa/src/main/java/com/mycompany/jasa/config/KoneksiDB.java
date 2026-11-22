package com.mycompany.jasa.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

/** Membuat koneksi baru dari konfigurasi rahasia di db.properties. */
public final class KoneksiDB {

    private KoneksiDB() {
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = loadProperties();
        String url = requireProperty(properties, "db.url");
        String user = requireProperty(properties, "db.user");
        String password = requireProperty(properties, "db.password");
        return DriverManager.getConnection(url, user, password);
    }

    private static Properties loadProperties() throws SQLException {
        Path workingDirectory = Path.of("").toAbsolutePath();
        List<Path> candidates = List.of(
                workingDirectory.resolve("db.properties"),
                workingDirectory.resolve("jasa").resolve("db.properties"));

        for (Path path : candidates) {
            if (Files.isRegularFile(path)) {
                Properties properties = new Properties();
                try (InputStream input = Files.newInputStream(path)) {
                    properties.load(input);
                    return properties;
                } catch (IOException exception) {
                    throw new SQLException("Tidak dapat membaca " + path, exception);
                }
            }
        }
        throw new SQLException("db.properties tidak ditemukan. Salin db.properties.example "
                + "ke root project jasa dan isi kredensial database.");
    }

    private static String requireProperty(Properties properties, String name) throws SQLException {
        String value = properties.getProperty(name);
        if (value == null || value.isBlank()) {
            throw new SQLException("Properti " + name + " belum diisi di db.properties.");
        }
        return value.trim();
    }
}
