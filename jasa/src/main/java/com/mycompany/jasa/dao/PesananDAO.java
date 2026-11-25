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

/** Query JDBC untuk transaksi, tracking, penugasan kurir, dan laporan. */
public class PesananDAO {
    private static final String SELECT = "SELECT p.id_pesanan,p.id_user,p.id_driver,u.nama AS pelanggan, "
            + "d.nama AS driver,l.nama_layanan,l.kategori,p.tujuan,p.total_biaya,p.status_pesanan,p.tanggal_pesanan "
            + "FROM pesanan p JOIN users u ON u.id_user=p.id_user "
            + "LEFT JOIN users d ON d.id_user=p.id_driver JOIN layanan_umkm l ON l.id_layanan=p.id_layanan ";

    public Pesanan insert(Pesanan order, int idLayanan) throws SQLException {
        String sql = "INSERT INTO pesanan (id_user,id_layanan,id_driver,tujuan,total_biaya,status_pesanan) "
                + "VALUES (?,?,NULL,?,?,'Pending')";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getIdUser()); ps.setInt(2, idLayanan); ps.setString(3, order.getTujuan());
            ps.setDouble(4, order.getTotalBiaya()); ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("ID pesanan baru tidak ditemukan.");
                order.setIdPesanan(keys.getInt(1)); return order;
            }
        }
    }
    public List<Pesanan> findForCustomer(int idUser) throws SQLException {
        return query(SELECT + "WHERE p.id_user=? ORDER BY p.tanggal_pesanan DESC", idUser);
    }
    public List<Pesanan> findPending() throws SQLException {
        return query(SELECT + "WHERE p.status_pesanan='Pending' AND p.id_driver IS NULL ORDER BY p.tanggal_pesanan");
    }
    public List<Pesanan> findForDriver(int idDriver) throws SQLException {
        return query(SELECT + "WHERE p.id_driver=? ORDER BY p.tanggal_pesanan DESC", idDriver);
    }
    public List<Pesanan> findAll() throws SQLException {
        return query(SELECT + "ORDER BY p.tanggal_pesanan DESC");
    }
    /** Atomic claim prevents two drivers from taking the same pending order. */
    public boolean claim(int idOrder, int idDriver) throws SQLException {
        String sql = "UPDATE pesanan SET id_driver=?,status_pesanan='Diproses' "
                + "WHERE id_pesanan=? AND id_driver IS NULL AND status_pesanan='Pending'";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idDriver); ps.setInt(2, idOrder); return ps.executeUpdate() == 1;
        }
    }
    public boolean advanceStatus(int idOrder, int idDriver, String current, String next) throws SQLException {
        String sql = "UPDATE pesanan SET status_pesanan=? WHERE id_pesanan=? AND id_driver=? AND status_pesanan=?";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, next); ps.setInt(2, idOrder); ps.setInt(3, idDriver); ps.setString(4, current);
            return ps.executeUpdate() == 1;
        }
    }
    public double completedEarnings(int idDriver) throws SQLException {
        String sql = "SELECT COALESCE(SUM(total_biaya),0) FROM pesanan WHERE id_driver=? AND status_pesanan='Selesai'";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idDriver); try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getDouble(1); }
        }
    }
    public List<RingkasanPendapatan> dailyReport() throws SQLException {
        return report("DATE_FORMAT(tanggal_pesanan,'%Y-%m-%d')");
    }
    public List<RingkasanPendapatan> monthlyReport() throws SQLException {
        return report("DATE_FORMAT(tanggal_pesanan,'%Y-%m')");
    }
    private List<RingkasanPendapatan> report(String labelExpression) throws SQLException {
        String sql = "SELECT " + labelExpression + " AS periode,COUNT(*) AS jumlah,COALESCE(SUM(total_biaya),0) AS total "
                + "FROM pesanan WHERE status_pesanan='Selesai' GROUP BY " + labelExpression + " ORDER BY periode DESC";
        List<RingkasanPendapatan> rows = new ArrayList<>();
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) rows.add(new RingkasanPendapatan(rs.getString("periode"), rs.getInt("jumlah"), rs.getDouble("total")));
        }
        return rows;
    }
    private List<Pesanan> query(String sql, Integer... values) throws SQLException {
        List<Pesanan> rows = new ArrayList<>();
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i=0; i<values.length; i++) ps.setInt(i+1, values[i]);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) rows.add(map(rs)); }
        }
        return rows;
    }
    private static Pesanan map(ResultSet rs) throws SQLException {
        Timestamp time = rs.getTimestamp("tanggal_pesanan");
        int driver = rs.getInt("id_driver"); Integer driverId = rs.wasNull() ? null : driver;
        return new Pesanan(rs.getInt("id_pesanan"), rs.getInt("id_user"), driverId,
                rs.getString("pelanggan"), rs.getString("driver"), rs.getString("nama_layanan"),
                rs.getString("kategori"), rs.getString("tujuan"), rs.getDouble("total_biaya"),
                rs.getString("status_pesanan"), time == null ? null : time.toLocalDateTime());
    }
}
