package com.mycompany.jasa.dao;

import com.mycompany.jasa.config.KoneksiDB;
import com.mycompany.jasa.model.LayananUmkm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Query JDBC khusus tabel layanan_umkm. */
public class LayananDAO {
    public List<LayananUmkm> findAll() throws SQLException {
        String sql = "SELECT id_layanan, nama_layanan, kategori, lokasi_asal, biaya_dasar "
                + "FROM layanan_umkm ORDER BY kategori, nama_layanan";
        List<LayananUmkm> rows = new ArrayList<>();
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) rows.add(map(rs));
        }
        return rows;
    }
    public LayananUmkm findById(int id) throws SQLException {
        String sql = "SELECT id_layanan, nama_layanan, kategori, lokasi_asal, biaya_dasar FROM layanan_umkm WHERE id_layanan=?";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    public LayananUmkm insert(LayananUmkm row) throws SQLException {
        String sql = "INSERT INTO layanan_umkm (nama_layanan,kategori,lokasi_asal,biaya_dasar) VALUES (?,?,?,?)";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            bind(ps, row); ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("ID layanan baru tidak ditemukan.");
                row.setIdLayanan(keys.getInt(1)); return row;
            }
        }
    }
    public boolean update(LayananUmkm row) throws SQLException {
        String sql = "UPDATE layanan_umkm SET nama_layanan=?,kategori=?,lokasi_asal=?,biaya_dasar=? WHERE id_layanan=?";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, row); ps.setInt(5, row.getIdLayanan()); return ps.executeUpdate() == 1;
        }
    }
    /** Layanan yang sudah dipakai transaksi tidak dapat dihapus agar riwayat tetap utuh. */
    public boolean deleteIfUnused(int id) throws SQLException {
        String sql = "DELETE FROM layanan_umkm WHERE id_layanan=? AND NOT EXISTS "
                + "(SELECT 1 FROM pesanan WHERE id_layanan=?)";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); ps.setInt(2, id); return ps.executeUpdate() == 1;
        }
    }
    private static void bind(PreparedStatement ps, LayananUmkm row) throws SQLException {
        ps.setString(1, row.getNamaLayanan()); ps.setString(2, row.getKategori());
        ps.setString(3, row.getLokasiAsal()); ps.setDouble(4, row.getBiayaDasar());
    }
    private static LayananUmkm map(ResultSet rs) throws SQLException {
        return new LayananUmkm(rs.getInt("id_layanan"), rs.getString("nama_layanan"),
                rs.getString("kategori"), rs.getString("lokasi_asal"), rs.getDouble("biaya_dasar"));
    }
}
