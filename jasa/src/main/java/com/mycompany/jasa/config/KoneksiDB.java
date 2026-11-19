package com.mycompany.jasa.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Membuat koneksi baru ke database JSBJ untuk setiap operasi DAO. */
public final class KoneksiDB {

    private static final String URL = System.getenv().getOrDefault(
            "JSBJ_DB_URL", "jdbc:mysql://localhost:3306/db_jasa_jatinangor");
    private static final String USER = System.getenv().getOrDefault("JSBJ_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("JSBJ_DB_PASSWORD", "");

    private KoneksiDB() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
