# Jasa Serba Bisa Jatinangor (JSBJ)

Aplikasi desktop Java Swing untuk mendukung layanan kurir lokal dan jasa titip (jastip) bagi masyarakat dan mahasiswa di kawasan Jatinangor.

## Tim dan teknologi

- Salsabila Wiryawan (NPM: 140810250022): autentikasi, profil, dan manajemen pengguna.
- Jilan Safira (NPM: 140810250070): katalog layanan, pesanan, tracking, dan laporan.
- Mata kuliah: Praktikum Pemrograman Berorientasi Objek (PBO).
- Java 26, Maven, Java Swing, MySQL, dan MySQL Connector/J.

## Alur dan pembagian fitur

1. Pengguna dapat membuat akun sendiri sebagai **Pelanggan** melalui form registrasi.
2. Pelanggan, Driver, dan Admin masuk melalui form login yang sama. Setelah login, aplikasi mengarahkan pengguna ke dashboard sesuai role.
3. Pelanggan dapat melihat dan mengubah profil, melihat katalog UMKM, membuat pesanan, dan memantau riwayat serta status pesanan.
4. Driver dapat melihat pesanan berstatus `Pending`, mengambil pesanan, memperbarui status pengantaran, dan melihat riwayat serta penghasilan.
5. Admin mengelola pengguna dan membuat akun Driver. Registrasi publik tidak menyediakan pilihan role Driver atau Admin.
6. Admin mengelola katalog layanan/UMKM, memantau transaksi, dan melihat laporan.

Salsabila mengerjakan fitur autentikasi, profil, serta manajemen pengguna. Jilan mengerjakan dashboard pelanggan/driver, katalog layanan, pesanan, tracking, dan laporan. Dashboard Admin menjadi titik temu kedua bagian.

## Struktur source

Project Maven utama berada di folder `jasa/`. Source Java berada di `jasa/src/main/java/com/mycompany/jasa/` dan dipisahkan ke package `config`, `dao`, `model`, serta `view`.

## Persiapan database

Untuk instalasi database baru, jalankan seluruh script berikut di HeidiSQL atau MySQL Workbench. Script membuat tiga tabel dan menambahkan satu akun Admin awal. Jika database sudah memiliki data, jangan jalankan ulang query Admin kecuali email tersebut belum terdaftar.

```sql
CREATE DATABASE IF NOT EXISTS db_jasa_jatinangor
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE db_jasa_jatinangor;

CREATE TABLE IF NOT EXISTS users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    no_telepon VARCHAR(15),
    role ENUM('Pelanggan', 'Driver', 'Admin') DEFAULT 'Pelanggan'
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS layanan_umkm (
    id_layanan INT AUTO_INCREMENT PRIMARY KEY,
    nama_layanan VARCHAR(100) NOT NULL,
    kategori ENUM('Delivery Makanan', 'Jastip Toko', 'Kirim Barang') NOT NULL,
    lokasi_asal TEXT NOT NULL,
    biaya_dasar DOUBLE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pesanan (
    id_pesanan INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_layanan INT NOT NULL,
    id_driver INT NULL,
    tujuan TEXT NOT NULL,
    total_biaya DOUBLE NOT NULL,
    status_pesanan ENUM('Pending', 'Diproses', 'Diantar', 'Selesai') DEFAULT 'Pending',
    tanggal_pesanan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pesanan_user
        FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE,
    CONSTRAINT fk_pesanan_layanan
        FOREIGN KEY (id_layanan) REFERENCES layanan_umkm(id_layanan) ON DELETE CASCADE,
    CONSTRAINT fk_pesanan_driver
        FOREIGN KEY (id_driver) REFERENCES users(id_user) ON DELETE SET NULL
) ENGINE=InnoDB;

INSERT INTO users (nama, email, password, no_telepon, role)
SELECT 'Admin JSBJ', 'admin@jsbj.local',
       'pbkdf2-sha256$210000$5pytC0yZBorHcUzg65wQKQ==$XwLj1O3cA+dQKMTrYBBi9e7oGYc/0NTsAv4gwQlv23g=',
       '081234567890', 'Admin'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@jsbj.local'
);
```

Akun Admin awal untuk login:

- Email: `admin@jsbj.local`
- Password: `JsbjAdmin#2026`

Password pada query sudah di-hash dengan PBKDF2-HMAC-SHA256 mengikuti format yang dipakai `UserDAO`. Jangan mengganti nilai hash dengan password mentah. Segera ubah password Admin setelah login pertama.

Untuk database lama yang tabelnya sudah dibuat, sesuaikan skema sebelum memakai fitur terbaru:

```sql
ALTER TABLE users
    MODIFY role ENUM('Pelanggan', 'Driver', 'Admin') DEFAULT 'Pelanggan';

ALTER TABLE pesanan
    ADD COLUMN id_driver INT NULL,
    ADD CONSTRAINT fk_pesanan_driver FOREIGN KEY (id_driver)
        REFERENCES users(id_user) ON DELETE SET NULL;
```

`id_driver` kosong (`NULL`) saat pesanan baru dibuat. Ketika Driver mengambil pesanan, aplikasi mengisi kolom tersebut dan mengubah status menjadi `Diproses`.

## Konfigurasi koneksi

Maven mengambil dependency MySQL Connector/J dari `jasa/pom.xml`. Secara default, aplikasi menghubungkan ke `jdbc:mysql://localhost:3306/db_jasa_jatinangor` dengan user `root` dan password kosong. Jika pengaturan lokal berbeda, buat environment variable `JSBJ_DB_URL`, `JSBJ_DB_USER`, dan `JSBJ_DB_PASSWORD` sebelum menjalankan aplikasi.

Password akun disimpan sebagai hash PBKDF2, bukan teks biasa. Registrasi publik hanya membuat akun Pelanggan. Admin membuat akun Driver dan Admin dari dashboard.

## Membuka project

Buka folder `jasa/` sebagai project Maven di NetBeans. Pastikan MySQL aktif dan skema database sudah dibuat sebelum mencoba fitur yang terhubung ke database.
