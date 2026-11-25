package com.mycompany.jasa.model;

/** Rekap jumlah dan nilai pesanan selesai untuk periode tertentu. */
public class RingkasanPendapatan {
    private String periode;
    private int jumlahPesanan;
    private double totalPendapatan;

    public RingkasanPendapatan(String periode, int jumlahPesanan, double totalPendapatan) {
        this.periode = periode; this.jumlahPesanan = jumlahPesanan; this.totalPendapatan = totalPendapatan;
    }
    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }
    public int getJumlahPesanan() { return jumlahPesanan; }
    public void setJumlahPesanan(int jumlahPesanan) { this.jumlahPesanan = jumlahPesanan; }
    public double getTotalPendapatan() { return totalPendapatan; }
    public void setTotalPendapatan(double totalPendapatan) { this.totalPendapatan = totalPendapatan; }
}
