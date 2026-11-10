package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    // Sesuaikan nama database, username, dan password MySQL di laptop masing-masing
    private static final String URL = "jdbc:mysql://localhost:3306/db_jasa_jatinangor";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Isikan password MySQL jika ada
    
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Register Driver JDBC MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi ke Database Berhasil!");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC tidak ditemukan: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Gagal terkoneksi ke Database: " + e.getMessage());
            }
        }
        return connection;
    }
}