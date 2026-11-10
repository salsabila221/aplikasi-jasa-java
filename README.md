# Jasa Serba Bisa Jatinangor (JSBJ) 🚚

Aplikasi berbasis desktop (Java Swing) yang dirancang untuk mendukung operasional layanan kurir lokal dan jasa titip (jastip) terintegrasi bagi masyarakat serta mahasiswa di kawasan Jatinangor.

---

## 👥 Tim Pengembang
* **Salsabila Wiryawan** (NPM: 140810250022)
* **Jilan Safira** (NPM: 140810250070)

* **Mata Kuliah:** Praktikum Pemrograman Berorientasi Objek (PBO)
* **Tema Proyek:** Bisnis UMKM di Daerah Jatinangor

---

## 🛠️ Teknologi & Tools
* **Language:** Java (JDK 17+)
* **GUI Framework:** Java Swing
* **Database:** MySQL / PostgreSQL
* **Driver:** MySQL Connector/J

---

## 🗄️ Database Setup (SQL Script)

Buat database baru bernama `db_jasa_jatinangor` di phpMyAdmin / MySQL Workbench, lalu jalankan query berikut:

```sql
CREATE DATABASE IF NOT EXISTS db_jasa_jatinangor;
USE db_jasa_jatinangor;

-- 1. Tabel users (Manajemen Pengguna)
CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    no_telepon VARCHAR(15),
    role ENUM('Pelanggan', 'Admin') DEFAULT 'Pelanggan'
);

-- 2. Tabel layanan_umkm (Katalog Jasa & Mitra UMKM)
CREATE TABLE layanan_umkm (
    id_layanan INT AUTO_INCREMENT PRIMARY KEY,
    nama_layanan VARCHAR(100) NOT NULL,
    kategori ENUM('Delivery Makanan', 'Jastip Toko', 'Kirim Barang') NOT NULL,
    lokasi_asal TEXT NOT NULL,
    biaya_dasar DOUBLE NOT NULL
);

-- 3. Tabel pesanan (Transaksi & Tracking)
CREATE TABLE pesanan (
    id_pesanan INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_layanan INT NOT NULL,
    tujuan TEXT NOT NULL,
    total_biaya DOUBLE NOT NULL,
    status_pesanan ENUM('Pending', 'Diproses', 'Diantar', 'Selesai') DEFAULT 'Pending',
    tanggal_pesanan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_layanan) REFERENCES layanan_umkm(id_layanan) ON DELETE CASCADE
);