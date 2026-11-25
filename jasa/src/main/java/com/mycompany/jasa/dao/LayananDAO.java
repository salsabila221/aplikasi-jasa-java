package com.mycompany.jasa.dao;

import com.mycompany.jasa.config.KoneksiDB;
import com.mycompany.jasa.model.Layanan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** DAO untuk tabel layanan_umkm. */
public class LayananDAO {
    public List<Layanan> findAll() throws SQLException {
        String sql = "SELECT id_layanan, nama_layanan, kategori, lokasi_asal, biaya_dasar "
                   + "FROM layanan_umkm ORDER BY id_layanan DESC";
        List<Layanan> result = new ArrayList<>();
        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    public List<Layanan> findByCategory(String kategori) throws SQLException {
        if (kategori == null || kategori.isBlank() || kategori.equalsIgnoreCase("Semua Kategori")) {
            return findAll();
        }
        String sql = "SELECT id_layanan, nama_layanan, kategori, lokasi_asal, biaya_dasar "
                   + "FROM layanan_umkm WHERE kategori=? ORDER BY id_layanan DESC";
        List<Layanan> result = new ArrayList<>();
        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kategori);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(map(rs));
            }
        }
        return result;
    }

    public Layanan findById(int id) throws SQLException {
        String sql = "SELECT id_layanan, nama_layanan, kategori, lokasi_asal, biaya_dasar "
                   + "FROM layanan_umkm WHERE id_layanan=?";
        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public Layanan insert(Layanan item) throws SQLException {
        validate(item);
        String sql = "INSERT INTO layanan_umkm (nama_layanan, kategori, lokasi_asal, biaya_dasar) VALUES (?,?,?,?)";
        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            bind(ps, item);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("ID layanan baru tidak ditemukan.");
                item.setIdLayanan(keys.getInt(1));
                return item;
            }
        }
    }

    public boolean update(Layanan item) throws SQLException {
        validate(item);
        String sql = "UPDATE layanan_umkm SET nama_layanan=?, kategori=?, lokasi_asal=?, biaya_dasar=? WHERE id_layanan=?";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, item);
            ps.setInt(5, item.getIdLayanan());
            return ps.executeUpdate() == 1;
        }
    }

    /** Menolak penghapusan layanan yang sudah terhubung ke transaksi. */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM layanan_umkm WHERE id_layanan=? AND NOT EXISTS "
                   + "(SELECT 1 FROM pesanan WHERE pesanan.id_layanan=layanan_umkm.id_layanan)";
        try (Connection c = KoneksiDB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private static void bind(PreparedStatement ps, Layanan item) throws SQLException {
        ps.setString(1, item.getNamaLayanan().trim());
        ps.setString(2, item.getKategori());
        ps.setString(3, item.getLokasiAsal().trim());
        ps.setDouble(4, item.getBiayaDasar());
    }

    private static Layanan map(ResultSet rs) throws SQLException {
        return new Layanan(rs.getInt("id_layanan"), rs.getString("nama_layanan"),
                rs.getString("kategori"), rs.getString("lokasi_asal"), rs.getDouble("biaya_dasar"));
    }

    private static void validate(Layanan item) throws SQLException {
        if (item == null || item.getNamaLayanan() == null || item.getNamaLayanan().isBlank())
            throw new SQLException("Nama layanan wajib diisi.");
        if (item.getKategori() == null || !List.of("Delivery Makanan", "Jastip Toko", "Kirim Barang").contains(item.getKategori()))
            throw new SQLException("Kategori layanan tidak valid.");
        if (item.getLokasiAsal() == null || item.getLokasiAsal().isBlank())
            throw new SQLException("Lokasi asal wajib diisi.");
        if (!Double.isFinite(item.getBiayaDasar()) || item.getBiayaDasar() < 0)
            throw new SQLException("Biaya dasar tidak valid.");
    }
}
