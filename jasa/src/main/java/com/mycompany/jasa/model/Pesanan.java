package com.mycompany.jasa.model;

import java.time.LocalDateTime;

/** Data transaksi pesanan beserta nama relasi untuk ditampilkan di tabel. */
public class Pesanan {
    private int idPesanan;
    private int idUser;
    private Integer idDriver;
    private String namaPelanggan;
    private String namaDriver;
    private String namaLayanan;
    private String kategori;
    private String tujuan;
    private double totalBiaya;
    private String statusPesanan;
    private LocalDateTime tanggalPesanan;

    public Pesanan(int idPesanan, int idUser, Integer idDriver, String namaPelanggan,
            String namaDriver, String namaLayanan, String kategori, String tujuan,
            double totalBiaya, String statusPesanan, LocalDateTime tanggalPesanan) {
        this.idPesanan = idPesanan; this.idUser = idUser; this.idDriver = idDriver;
        this.namaPelanggan = namaPelanggan; this.namaDriver = namaDriver;
        this.namaLayanan = namaLayanan; this.kategori = kategori; this.tujuan = tujuan;
        this.totalBiaya = totalBiaya; this.statusPesanan = statusPesanan;
        this.tanggalPesanan = tanggalPesanan;
    }
    public int getIdPesanan() { return idPesanan; }
    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public Integer getIdDriver() { return idDriver; }
    public void setIdDriver(Integer idDriver) { this.idDriver = idDriver; }
    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
    public String getNamaDriver() { return namaDriver; }
    public void setNamaDriver(String namaDriver) { this.namaDriver = namaDriver; }
    public String getNamaLayanan() { return namaLayanan; }
    public void setNamaLayanan(String namaLayanan) { this.namaLayanan = namaLayanan; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public String getTujuan() { return tujuan; }
    public void setTujuan(String tujuan) { this.tujuan = tujuan; }
    public double getTotalBiaya() { return totalBiaya; }
    public void setTotalBiaya(double totalBiaya) { this.totalBiaya = totalBiaya; }
    public String getStatusPesanan() { return statusPesanan; }
    public void setStatusPesanan(String statusPesanan) { this.statusPesanan = statusPesanan; }
    public LocalDateTime getTanggalPesanan() { return tanggalPesanan; }
    public void setTanggalPesanan(LocalDateTime tanggalPesanan) { this.tanggalPesanan = tanggalPesanan; }
}
