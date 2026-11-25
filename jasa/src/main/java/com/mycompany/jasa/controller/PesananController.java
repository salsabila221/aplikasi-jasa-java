package com.mycompany.jasa.controller;

import com.mycompany.jasa.dao.LayananDAO;
import com.mycompany.jasa.dao.PesananDAO;
import com.mycompany.jasa.dao.UserDAO;
import com.mycompany.jasa.model.LayananUmkm;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.RingkasanPendapatan;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.User;
import java.sql.SQLException;
import java.util.List;

/** Alur pemesanan pelanggan, penugasan driver, progres, dan laporan admin. */
public class PesananController {
    private final PesananDAO pesananDAO;
    private final LayananDAO layananDAO;
    private final UserDAO userDAO;
    public PesananController() { this(new PesananDAO(), new LayananDAO(), new UserDAO()); }
    PesananController(PesananDAO pesananDAO, LayananDAO layananDAO, UserDAO userDAO) {
        this.pesananDAO = pesananDAO; this.layananDAO = layananDAO; this.userDAO = userDAO;
    }

    public void placeOrder(User customer, int idLayanan, String tujuan) {
        requireRole(customer, Role.PELANGGAN);
        if (tujuan == null || tujuan.isBlank()) throw new ControllerException("Alamat tujuan wajib diisi.");
        try {
            LayananUmkm layanan = layananDAO.findById(idLayanan);
            if (layanan == null) throw new ControllerException("Layanan tidak tersedia.");
            // Skema tidak menyimpan jarak atau komponen tarif lain; tarif order memakai biaya_dasar katalog.
            Pesanan order = new Pesanan(0, customer.getIdUser(), null, null, null,
                    layanan.getNamaLayanan(), layanan.getKategori(), tujuan.trim(),
                    layanan.getBiayaDasar(), "Pending", null);
            pesananDAO.insert(order, idLayanan);
        } catch (SQLException ex) { throw ControllerSupport.databaseError("Membuat pesanan", ex); }
    }
    public List<Pesanan> customerOrders(User customer) {
        requireRole(customer, Role.PELANGGAN);
        try { return pesananDAO.findForCustomer(customer.getIdUser()); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Memuat pesanan pelanggan", ex); }
    }
    public List<Pesanan> pendingOrders(User driver) {
        requireRole(driver, Role.DRIVER);
        try { return pesananDAO.findPending(); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Memuat order masuk", ex); }
    }
    public List<Pesanan> driverOrders(User driver) {
        requireRole(driver, Role.DRIVER);
        try { return pesananDAO.findForDriver(driver.getIdUser()); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Memuat riwayat driver", ex); }
    }
    public void claimOrder(User driver, int idOrder) {
        requireRole(driver, Role.DRIVER);
        try {
            if (!pesananDAO.claim(idOrder, driver.getIdUser())) {
                throw new ControllerException("Pesanan sudah diambil driver lain atau tidak lagi Pending.");
            }
        } catch (SQLException ex) { throw ControllerSupport.databaseError("Mengambil pesanan", ex); }
    }
    public String advanceDriverOrder(User driver, int idOrder) {
        requireRole(driver, Role.DRIVER);
        try {
            Pesanan selected = pesananDAO.findForDriver(driver.getIdUser()).stream()
                    .filter(order -> order.getIdPesanan() == idOrder).findFirst()
                    .orElseThrow(() -> new ControllerException("Pesanan ini bukan milik driver yang sedang login."));
            String next = switch (selected.getStatusPesanan()) {
                case "Diproses" -> "Diantar";
                case "Diantar" -> "Selesai";
                default -> throw new ControllerException("Tidak ada progres berikutnya untuk pesanan ini.");
            };
            if (!pesananDAO.advanceStatus(idOrder, driver.getIdUser(), selected.getStatusPesanan(), next)) {
                throw new ControllerException("Status pesanan berubah. Muat ulang lalu coba lagi.");
            }
            return next;
        } catch (SQLException ex) { throw ControllerSupport.databaseError("Memperbarui status", ex); }
    }
    public double driverEarnings(User driver) {
        requireRole(driver, Role.DRIVER);
        try { return pesananDAO.completedEarnings(driver.getIdUser()); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Menghitung penghasilan driver", ex); }
    }
    public List<Pesanan> adminOrders(User admin) {
        requireRole(admin, Role.ADMIN);
        try { return pesananDAO.findAll(); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Memuat monitoring transaksi", ex); }
    }
    public List<RingkasanPendapatan> dailyReport(User admin) {
        requireRole(admin, Role.ADMIN);
        try { return pesananDAO.dailyReport(); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Membuat laporan harian", ex); }
    }
    public List<RingkasanPendapatan> monthlyReport(User admin) {
        requireRole(admin, Role.ADMIN);
        try { return pesananDAO.monthlyReport(); }
        catch (SQLException ex) { throw ControllerSupport.databaseError("Membuat laporan bulanan", ex); }
    }
    private void requireRole(User actor, Role required) {
        if (actor == null || actor.getIdUser() <= 0) throw new ControllerException("Sesi tidak valid. Silakan login kembali.");
        try {
            User current = userDAO.findById(actor.getIdUser());
            if (current == null || current.getRole() != required) throw new ControllerException("Fitur ini hanya untuk role " + required.getDatabaseValue() + ".");
        } catch (SQLException ex) { throw ControllerSupport.databaseError("Memeriksa hak akses", ex); }
    }
}
