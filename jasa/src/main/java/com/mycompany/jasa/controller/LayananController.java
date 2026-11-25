package com.mycompany.jasa.controller;

import com.mycompany.jasa.dao.LayananDAO;
import com.mycompany.jasa.dao.UserDAO;
import com.mycompany.jasa.model.Layanan;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.User;
import java.sql.SQLException;
import java.util.List;

/** Controller katalog layanan. Pembacaan untuk pelanggan, CRUD khusus Admin. */
public class LayananController {
    public static final String[] KATEGORI = {"Delivery Makanan", "Jastip Toko", "Kirim Barang"};

    private final LayananDAO layananDAO;
    private final UserDAO userDAO;

    public LayananController() {
        this.layananDAO = new LayananDAO();
        this.userDAO = new UserDAO();
    }

    public List<Layanan> getSemuaLayanan() {
        try { return layananDAO.findAll(); }
        catch (SQLException ex) { throw databaseError("Memuat katalog", ex); }
    }

    public List<Layanan> getLayananByKategori(String kategori) {
        try { return layananDAO.findByCategory(kategori); }
        catch (SQLException ex) { throw databaseError("Memfilter katalog", ex); }
    }

    public Layanan getById(int id) {
        if (id <= 0) throw new ControllerException("Layanan tidak valid.");
        try {
            Layanan item = layananDAO.findById(id);
            if (item == null) throw new ControllerException("Layanan tidak ditemukan.");
            return item;
        } catch (SQLException ex) { throw databaseError("Memuat detail layanan", ex); }
    }

    public Layanan tambahLayanan(User actor, String nama, String kategori, String lokasi, double biaya) {
        requireAdmin(actor);
        Layanan item = validasi(0, nama, kategori, lokasi, biaya);
        try { return layananDAO.insert(item); }
        catch (SQLException ex) { throw databaseError("Menambahkan layanan", ex); }
    }

    public boolean ubahLayanan(User actor, int id, String nama, String kategori, String lokasi, double biaya) {
        requireAdmin(actor);
        if (id <= 0) throw new ControllerException("Pilih layanan yang akan diubah.");
        Layanan item = validasi(id, nama, kategori, lokasi, biaya);
        try {
            if (!layananDAO.update(item)) throw new ControllerException("Layanan tidak ditemukan.");
            return true;
        } catch (SQLException ex) { throw databaseError("Mengubah layanan", ex); }
    }

    public boolean hapusLayanan(User actor, int id) {
        requireAdmin(actor);
        if (id <= 0) throw new ControllerException("Pilih layanan yang akan dihapus.");
        try {
            if (!layananDAO.delete(id)) {
                throw new ControllerException("Layanan tidak ditemukan atau sudah memiliki riwayat pesanan.");
            }
            return true;
        } catch (SQLException ex) { throw databaseError("Menghapus layanan", ex); }
    }

    private Layanan validasi(int id, String nama, String kategori, String lokasi, double biaya) {
        if (nama == null || nama.isBlank()) throw new ControllerException("Nama layanan wajib diisi.");
        if (kategori == null || !List.of(KATEGORI).contains(kategori)) throw new ControllerException("Kategori tidak valid.");
        if (lokasi == null || lokasi.isBlank()) throw new ControllerException("Lokasi asal wajib diisi.");
        if (!Double.isFinite(biaya) || biaya < 0) throw new ControllerException("Biaya dasar tidak valid.");
        return new Layanan(id, nama.trim(), kategori, lokasi.trim(), biaya);
    }

    private void requireAdmin(User actor) {
        if (actor == null || actor.getIdUser() <= 0) throw new ControllerException("Sesi tidak valid.");
        try {
            User current = userDAO.findById(actor.getIdUser());
            if (current == null || current.getRole() != Role.ADMIN) {
                throw new ControllerException("Fitur ini hanya dapat digunakan Admin.");
            }
        } catch (SQLException ex) { throw databaseError("Memeriksa hak akses", ex); }
    }

    private ControllerException databaseError(String action, SQLException ex) {
        return new ControllerException(action + " gagal: " + ex.getMessage(), ex);
    }
}
