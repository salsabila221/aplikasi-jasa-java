-- Jalankan pada database JSBJ yang sudah dipilih di HeidiSQL.
-- Tidak membuat atau memilih database.

SET FOREIGN_KEY_CHECKS = 0;

-- Alias lama, bukan bagian dari skema aplikasi saat ini.
-- Jika tabel ini ada dan berisi data yang masih diperlukan, ekspor dahulu sebelum menjalankan script.
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `layanan`;
DROP TABLE IF EXISTS `pengguna`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS `users` (
    `id_user` INT AUTO_INCREMENT PRIMARY KEY,
    `nama` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `no_telepon` VARCHAR(15),
    `role` ENUM('Pelanggan', 'Driver', 'Admin') NOT NULL DEFAULT 'Pelanggan'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `layanan_umkm` (
    `id_layanan` INT AUTO_INCREMENT PRIMARY KEY,
    `nama_layanan` VARCHAR(100) NOT NULL,
    `kategori` ENUM('Delivery Makanan', 'Jastip Toko', 'Kirim Barang') NOT NULL,
    `lokasi_asal` TEXT NOT NULL,
    `biaya_dasar` DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `pesanan` (
    `id_pesanan` INT AUTO_INCREMENT PRIMARY KEY,
    `id_user` INT NOT NULL,
    `id_layanan` INT NOT NULL,
    `id_driver` INT NULL,
    `tujuan` TEXT NOT NULL,
    `total_biaya` DOUBLE NOT NULL,
    `status_pesanan` ENUM('Pending', 'Diproses', 'Diantar', 'Selesai') NOT NULL DEFAULT 'Pending',
    `tanggal_pesanan` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_pesanan_user`
        FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
    CONSTRAINT `fk_pesanan_layanan`
        FOREIGN KEY (`id_layanan`) REFERENCES `layanan_umkm` (`id_layanan`) ON DELETE RESTRICT,
    CONSTRAINT `fk_pesanan_driver`
        FOREIGN KEY (`id_driver`) REFERENCES `users` (`id_user`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tarif contoh untuk demo awal; Admin dapat mengubah atau menambah layanan dari aplikasi.
INSERT INTO `layanan_umkm` (`nama_layanan`, `kategori`, `lokasi_asal`, `biaya_dasar`)
SELECT 'Antar Makanan Lokal', 'Delivery Makanan', 'Jatinangor', 5000
WHERE NOT EXISTS (SELECT 1 FROM `layanan_umkm` WHERE `nama_layanan`='Antar Makanan Lokal');
INSERT INTO `layanan_umkm` (`nama_layanan`, `kategori`, `lokasi_asal`, `biaya_dasar`)
SELECT 'Jastip Toko', 'Jastip Toko', 'Jatinangor', 8000
WHERE NOT EXISTS (SELECT 1 FROM `layanan_umkm` WHERE `nama_layanan`='Jastip Toko');
INSERT INTO `layanan_umkm` (`nama_layanan`, `kategori`, `lokasi_asal`, `biaya_dasar`)
SELECT 'Kirim Barang Lokal', 'Kirim Barang', 'Jatinangor', 10000
WHERE NOT EXISTS (SELECT 1 FROM `layanan_umkm` WHERE `nama_layanan`='Kirim Barang Lokal');

-- Akun Admin awal. Password memakai format PBKDF2 yang diverifikasi AuthController.
INSERT INTO `users` (`nama`, `email`, `password`, `no_telepon`, `role`)
SELECT 'Admin JSBJ', 'admin@jsbj.local',
       'pbkdf2-sha256$210000$5pytC0yZBorHcUzg65wQKQ==$XwLj1O3cA+dQKMTrYBBi9e7oGYc/0NTsAv4gwQlv23g=',
       '081234567890', 'Admin'
WHERE NOT EXISTS (
    SELECT 1 FROM `users` WHERE `email` = 'admin@jsbj.local'
);
