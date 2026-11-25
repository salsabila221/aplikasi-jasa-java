/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jasa.controller;
import com.mycompany.jasa.dao.LayananDAO;
import com.mycompany.jasa.dao.PesananDAO;
import com.mycompany.jasa.dao.UserDAO;

import com.mycompany.jasa.model.Layanan;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.RingkasanPendapatan;
import com.mycompany.jasa.model.Role;
import com.mycompany.jasa.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author HP
 */
public class PesananController {
    private final PesananDAO pesananDAO = new PesananDAO();
    private final LayananDAO layananDAO = new LayananDAO();
    private final UserDAO userDAO = new UserDAO();

    // Membuat pesanan pelanggan
    public Pesanan buatPesanan(
            User pelanggan,
            int idLayanan,
            String tujuan) {

        requireRole(pelanggan, Role.PELANGGAN);

        if (tujuan == null || tujuan.isBlank()) {
            throw new ControllerException(
                    "Alamat tujuan wajib diisi."
            );
        }

        try {
            Layanan layanan = layananDAO.findById(idLayanan);

            if (layanan == null) {
                throw new ControllerException(
                        "Layanan tidak ditemukan."
                );
            }

            return pesananDAO.insert(
                    pelanggan.getIdUser(),
                    idLayanan,
                    tujuan.trim(),
                    layanan.getBiayaDasar()
            );

        } catch (SQLException ex) {
            throw databaseError("Membuat pesanan", ex);
        }
    }

    // Riwayat pesanan pelanggan
    public List<Pesanan> riwayatPelanggan(User pelanggan) {
        requireRole(pelanggan, Role.PELANGGAN);

        try {
            return pesananDAO.findByCustomer(
                    pelanggan.getIdUser()
            );
        } catch (SQLException ex) {
            throw databaseError("Memuat riwayat pelanggan", ex);
        }
    }

    // Daftar order Pending untuk Driver
    public List<Pesanan> pesananPending(User driver) {
        requireRole(driver, Role.DRIVER);

        try {
            return pesananDAO.findPending();
        } catch (SQLException ex) {
            throw databaseError("Memuat order masuk", ex);
        }
    }

    // Pesanan yang sudah diambil Driver
    public List<Pesanan> pesananDriver(User driver) {
        requireRole(driver, Role.DRIVER);

        try {
            return pesananDAO.findByDriver(
                    driver.getIdUser()
            );
        } catch (SQLException ex) {
            throw databaseError("Memuat pesanan Driver", ex);
        }
    }

    // Driver mengambil pesanan
    public void ambilPesanan(User driver, int idPesanan) {
        requireRole(driver, Role.DRIVER);

        try {
            if (!pesananDAO.takeOrder(
                    idPesanan,
                    driver.getIdUser())) {

                throw new ControllerException(
                        "Pesanan sudah diambil Driver lain."
                );
            }
        } catch (SQLException ex) {
            throw databaseError("Mengambil pesanan", ex);
        }
    }

    // Mengubah status pesanan
    public String lanjutkanStatus(User driver, int idPesanan) {
        requireRole(driver, Role.DRIVER);

        try {
            Pesanan item = pesananDAO.findById(idPesanan);

            if (item == null) {
                throw new ControllerException(
                        "Pesanan tidak ditemukan."
                );
            }

            if (item.getIdDriver() == null
                    || item.getIdDriver() != driver.getIdUser()) {

                throw new ControllerException(
                        "Pesanan ini bukan tanggung jawab Anda."
                );
            }

            String current = item.getStatusPesanan();
            String next;

            if ("Diproses".equals(current)) {
                next = "Diantar";
            } else if ("Diantar".equals(current)) {
                next = "Selesai";
            } else {
                throw new ControllerException(
                        "Status pesanan tidak dapat diperbarui."
                );
            }

            if (!pesananDAO.updateStatus(
                    idPesanan,
                    driver.getIdUser(),
                    current,
                    next)) {

                throw new ControllerException(
                        "Gagal memperbarui status pesanan."
                );
            }

            return next;

        } catch (SQLException ex) {
            throw databaseError("Memperbarui status pesanan", ex);
        }
    }

    // Ringkasan pendapatan Driver
    public RingkasanPendapatan ringkasanDriver(User driver) {
        requireRole(driver, Role.DRIVER);

        try {
            return pesananDAO.getDriverEarnings(
                    driver.getIdUser()
            );
        } catch (SQLException ex) {
            throw databaseError("Memuat ringkasan Driver", ex);
        }
    }

    // Memastikan role pengguna benar
    private void requireRole(User actor, Role role) {

        if (actor == null || actor.getIdUser() <= 0) {
            throw new ControllerException(
                    "Sesi tidak valid. Login kembali."
            );
        }

        try {
            User current = userDAO.findById(actor.getIdUser());

            if (current == null || current.getRole() != role) {
                throw new ControllerException(
                        "Akses tidak sesuai dengan role pengguna."
                );
            }

        } catch (SQLException ex) {
            throw databaseError("Memeriksa hak akses", ex);
        }
    }

    private ControllerException databaseError(
            String action,
            SQLException ex) {

        return new ControllerException(
                action + " gagal: " + ex.getMessage(),
                ex
        );
    }
}
