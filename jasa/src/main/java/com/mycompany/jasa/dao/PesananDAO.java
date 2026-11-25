package com.mycompany.jasa.dao;

import com.mycompany.jasa.config.KoneksiDB;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.RingkasanPendapatan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** DAO untuk transaksi pelanggan dan pengantaran Driver. */
public class PesananDAO {
    private static final String SELECT = "SELECT p.id_pesanan,p.id_user,p.id_layanan,p.id_driver,"
            + "p.tujuan,p.total_biaya,p.status_pesanan,p.tanggal_pesanan,"
            + "l.nama_layanan,l.kategori,pel.nama AS nama_pelanggan,drv.nama AS nama_driver "
            + "FROM pesanan p JOIN layanan_umkm l ON l.id_layanan=p.id_layanan "
            + "JOIN users pel ON pel.id_user=p.id_user "
            + "LEFT JOIN users drv ON drv.id_user=p.id_driver ";

    public Pesanan insert(int idUser, int idLayanan, String tujuan, double totalBiaya) throws SQLException {
        String sql = "INSERT INTO pesanan (id_user,id_layanan,id_driver,tujuan,total_biaya,status_pesanan) "
                   + "VALUES (?,?,NULL,?,?,'Pending')";
        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idUser);
            ps.setInt(2, idLayanan);
            ps.setString(3, tujuan.trim());
            ps.setDouble(4, totalBiaya);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("ID pesanan baru tidak ditemukan.");
                return findById(keys.getInt(1));
            }
        }
    }

    public Pesanan findById(int id) throws SQLException {
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT + "WHERE p.id_pesanan=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public List<Pesanan> findByCustomer(int idUser) throws SQLException {
        return query(SELECT + "WHERE p.id_user=? ORDER BY p.tanggal_pesanan DESC", idUser);
    }

    public List<Pesanan> findPending() throws SQLException {
        return query(SELECT + "WHERE p.status_pesanan='Pending' AND p.id_driver IS NULL ORDER BY p.tanggal_pesanan ASC");
    }

    public List<Pesanan> findByDriver(int idDriver) throws SQLException {
        return query(SELECT + "WHERE p.id_driver=? ORDER BY p.tanggal_pesanan DESC", idDriver);
    }

    /** UPDATE atomik agar satu order tidak diambil dua driver. */
    public boolean takeOrder(int idPesanan, int idDriver) throws SQLException {
        String sql = "UPDATE pesanan SET id_driver=?,status_pesanan='Diproses' "
                   + "WHERE id_pesanan=? AND id_driver IS NULL AND status_pesanan='Pending'";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idDriver);
            ps.setInt(2, idPesanan);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateStatus(int idPesanan, int idDriver, String current, String next) throws SQLException {
        String sql = "UPDATE pesanan SET status_pesanan=? WHERE id_pesanan=? AND id_driver=? AND status_pesanan=?";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, next);
            ps.setInt(2, idPesanan);
            ps.setInt(3, idDriver);
            ps.setString(4, current);
            return ps.executeUpdate() == 1;
        }
    }

    public RingkasanPendapatan getDriverEarnings(int idDriver) throws SQLException {
        String sql = "SELECT COUNT(*) AS jumlah,COALESCE(SUM(total_biaya),0) AS total "
                   + "FROM pesanan WHERE id_driver=? AND status_pesanan='Selesai'";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idDriver);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return new RingkasanPendapatan(rs.getInt("jumlah"), rs.getDouble("total"));
            }
        }
    }

    private List<Pesanan> query(String sql, int... params) throws SQLException {
        List<Pesanan> result = new ArrayList<>();
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setInt(i + 1, params[i]);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(map(rs));
            }
        }
        return result;
    }

    private static Pesanan map(ResultSet rs) throws SQLException {
        Timestamp created = rs.getTimestamp("tanggal_pesanan");
        int rawDriverId = rs.getInt("id_driver");
        Integer driverId = rs.wasNull() ? null : rawDriverId;
        return new Pesanan(rs.getInt("id_pesanan"), rs.getInt("id_user"), rs.getInt("id_layanan"),
                driverId, rs.getString("nama_pelanggan"), rs.getString("nama_layanan"),
                rs.getString("kategori"), rs.getString("nama_driver"), rs.getString("tujuan"),
                rs.getDouble("total_biaya"), rs.getString("status_pesanan"),
                created == null ? null : created.toLocalDateTime());
    }
}
