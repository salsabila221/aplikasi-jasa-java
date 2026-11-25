-- Jalankan pada database JSBJ yang sudah dipilih di HeidiSQL.
-- Aman dijalankan berulang; hanya menambahkan layanan demo jika namanya belum ada.
INSERT INTO `layanan_umkm` (`nama_layanan`, `kategori`, `lokasi_asal`, `biaya_dasar`)
SELECT 'Antar Makanan Lokal', 'Delivery Makanan', 'Jatinangor', 5000
WHERE NOT EXISTS (SELECT 1 FROM `layanan_umkm` WHERE `nama_layanan`='Antar Makanan Lokal');

INSERT INTO `layanan_umkm` (`nama_layanan`, `kategori`, `lokasi_asal`, `biaya_dasar`)
SELECT 'Jastip Toko', 'Jastip Toko', 'Jatinangor', 8000
WHERE NOT EXISTS (SELECT 1 FROM `layanan_umkm` WHERE `nama_layanan`='Jastip Toko');

INSERT INTO `layanan_umkm` (`nama_layanan`, `kategori`, `lokasi_asal`, `biaya_dasar`)
SELECT 'Kirim Barang Lokal', 'Kirim Barang', 'Jatinangor', 10000
WHERE NOT EXISTS (SELECT 1 FROM `layanan_umkm` WHERE `nama_layanan`='Kirim Barang Lokal');
