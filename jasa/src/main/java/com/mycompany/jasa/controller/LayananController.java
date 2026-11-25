package com.mycompany.jasa.controller;

import com.mycompany.jasa.dao.LayananDAO;
import com.mycompany.jasa.dao.UserDAO;
import com.mycompany.jasa.model.LayananUmkm;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/** Aturan katalog layanan dan hak admin untuk mengelolanya. */
public class LayananController {
    public static final List<String> KATEGORI = List.of("Delivery Makanan", "Jastip Toko", "Kirim Barang");
    private final LayananDAO layananDAO;
    private final UserDAO userDAO;

    public LayananController() { this(new LayananDAO(), new UserDAO()); }
    LayananController(LayananDAO layananDAO, UserDAO userDAO) { this.layananDAO = layananDAO; this.userDAO = userDAO; }

    public List<LayananUmkm> catalog(User actor) {
        requireRole(actor, Role.PELANGGAN, Role.ADMIN);
        try { return layananDAO.findAll(); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Memuat katalog layanan", ex); }
    }
    public List<LayananUmkm> manageList(User actor) {
        requireRole(actor, Role.ADMIN);
        try { return layananDAO.findAll(); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Memuat katalog", ex); }
    }
    public LayananUmkm create(User actor, String nama, String kategori, String lokasi, double biaya) {
        requireRole(actor, Role.ADMIN);
        LayananUmkm row = validated(0, nama, kategori, lokasi, biaya);
        try { return layananDAO.insert(row); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Menambahkan layanan", ex); }
    }
    public void update(User actor, int id, String nama, String kategori, String lokasi, double biaya) {
        requireRole(actor, Role.ADMIN);
        LayananUmkm row = validated(id, nama, kategori, lokasi, biaya);
        try { if (!layananDAO.update(row)) throw new ControllerException("Layanan tidak ditemukan."); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Mengubah layanan", ex); }
    }
    public void delete(User actor, int id) {
        requireRole(actor, Role.ADMIN);
        try {
            if (!layananDAO.deleteIfUnused(id)) {
                throw new ControllerException("Layanan sudah dipakai pesanan atau tidak ditemukan; data riwayat dijaga.");
            }
        } catch (SQLException ex) { throw ControllerSupport.databaseError("Menghapus layanan", ex); }
    }
    public LayananUmkm get(User actor, int id) {
        requireRole(actor, Role.PELANGGAN, Role.ADMIN);
        try {
            LayananUmkm result = layananDAO.findById(id);
            if (result == null) throw new ControllerException("Layanan tidak ditemukan.");
            return result;
        } catch (SQLException ex) { throw ControllerSupport.databaseError("Memuat layanan", ex); }
    }
    private LayananUmkm validated(int id, String nama, String kategori, String lokasi, double biaya) {
        if (nama == null || nama.isBlank()) throw new ControllerException("Nama layanan wajib diisi.");
        if (!KATEGORI.contains(kategori)) throw new ControllerException("Pilih kategori layanan yang tersedia.");
        if (lokasi == null || lokasi.isBlank()) throw new ControllerException("Lokasi asal wajib diisi.");
        if (!Double.isFinite(biaya) || biaya < 0) throw new ControllerException("Tarif harus berupa angka nol atau lebih.");
        return new LayananUmkm(id, nama.trim(), kategori, lokasi.trim(), biaya);
    }
    private void requireRole(User actor, Role... allowed) {
        if (actor == null || actor.getIdUser() <= 0) throw new ControllerException("Silakan login kembali.");
        try {
            User fresh = userDAO.findById(actor.getIdUser());
            if (fresh == null || !Set.of(allowed).contains(fresh.getRole())) throw new ControllerException("Role akun tidak memiliki akses ke fitur ini.");
        } catch (SQLException ex) { throw ControllerSupport.databaseError("Memeriksa hak akses", ex); }
    }
}
