package com.mycompany.jasa.model;

/** Data layanan/UMKM yang tampil di katalog. */
public class LayananUmkm {
    private int idLayanan;
    private String namaLayanan;
    private String kategori;
    private String lokasiAsal;
    private double biayaDasar;

    public LayananUmkm(int idLayanan, String namaLayanan, String kategori, String lokasiAsal, double biayaDasar) {
        this.idLayanan = idLayanan;
        this.namaLayanan = namaLayanan;
        this.kategori = kategori;
        this.lokasiAsal = lokasiAsal;
        this.biayaDasar = biayaDasar;
    }
    public int getIdLayanan() { return idLayanan; }
    public void setIdLayanan(int idLayanan) { this.idLayanan = idLayanan; }
    public String getNamaLayanan() { return namaLayanan; }
    public void setNamaLayanan(String namaLayanan) { this.namaLayanan = namaLayanan; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public String getLokasiAsal() { return lokasiAsal; }
    public void setLokasiAsal(String lokasiAsal) { this.lokasiAsal = lokasiAsal; }
    public double getBiayaDasar() { return biayaDasar; }
    public void setBiayaDasar(double biayaDasar) { this.biayaDasar = biayaDasar; }
}
