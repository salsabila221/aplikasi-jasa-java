package com.mycompany.jasa.model;

import java.time.LocalDateTime;

/** Data transaksi/pengantaran JSBJ. */
public class Pesanan {
    private int idPesanan;
    private int idUser;
    private int idLayanan;
    private Integer idDriver;
    private String namaPelanggan;
    private String namaLayanan;
    private String kategori;
    private String namaDriver;
    private String tujuan;
    private double totalBiaya;
    private String statusPesanan;
    private LocalDateTime tanggalPesanan;

    public Pesanan(int idPesanan, int idUser, int idLayanan, Integer idDriver,
                   String namaPelanggan, String namaLayanan, String kategori,
                   String namaDriver, String tujuan, double totalBiaya,
                   String statusPesanan, LocalDateTime tanggalPesanan) {
        this.idPesanan = idPesanan;
        this.idUser = idUser;
        this.idLayanan = idLayanan;
        this.idDriver = idDriver;
        this.namaPelanggan = namaPelanggan;
        this.namaLayanan = namaLayanan;
        this.kategori = kategori;
        this.namaDriver = namaDriver;
        this.tujuan = tujuan;
        this.totalBiaya = totalBiaya;
        this.statusPesanan = statusPesanan;
        this.tanggalPesanan = tanggalPesanan;
    }

    public int getIdPesanan() { return idPesanan; }
    public int getIdUser() { return idUser; }
    public int getIdLayanan() { return idLayanan; }
    public Integer getIdDriver() { return idDriver; }
    public String getNamaPelanggan() { return namaPelanggan; }
    public String getNamaLayanan() { return namaLayanan; }
    public String getKategori() { return kategori; }
    public String getNamaDriver() { return namaDriver; }
    public String getTujuan() { return tujuan; }
    public double getTotalBiaya() { return totalBiaya; }
    public String getStatusPesanan() { return statusPesanan; }
    public LocalDateTime getTanggalPesanan() { return tanggalPesanan; }
}
