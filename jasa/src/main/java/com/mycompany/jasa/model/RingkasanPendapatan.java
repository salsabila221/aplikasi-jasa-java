/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jasa.model;

/**
 *
 * @author HP
 */
public class RingkasanPendapatan {
    private final int jumlahPesanan;
    private final double totalPendapatan;

    public RingkasanPendapatan(
            int jumlahPesanan,
            double totalPendapatan) {

        this.jumlahPesanan = jumlahPesanan;
        this.totalPendapatan = totalPendapatan;
    }

    public int getJumlahPesanan() {
        return jumlahPesanan;
    }

    public double getTotalPendapatan() {
        return totalPendapatan;
    }
}
