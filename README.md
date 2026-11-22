# Jasa Serba Bisa Jatinangor (JSBJ)

Aplikasi desktop Java Swing untuk mendukung layanan kurir lokal dan jasa titip (jastip) bagi masyarakat dan mahasiswa di kawasan Jatinangor.

## Tim dan teknologi

- Salsabila Wiryawan (NPM: 140810250022): autentikasi, profil, dan manajemen pengguna.
- Jilan Safira (NPM: 140810250070): katalog layanan, pesanan, tracking, dan laporan.
- Mata kuliah: Praktikum Pemrograman Berorientasi Objek (PBO).
- Java, Maven, Java Swing, MySQL, dan MySQL Connector/J.

## Alur dan pembagian fitur

1. Pengguna dapat membuat akun sendiri sebagai **Pelanggan** melalui form registrasi.
2. Pelanggan, Driver, dan Admin masuk melalui form login yang sama. Setelah login, aplikasi mengarahkan pengguna ke dashboard sesuai role.
3. Pelanggan dapat melihat dan mengubah profil, melihat katalog UMKM, membuat pesanan, dan memantau riwayat serta status pesanan.
4. Driver dapat melihat pesanan berstatus `Pending`, mengambil pesanan, memperbarui status pengantaran, dan melihat riwayat serta penghasilan.
5. Admin mengelola pengguna dan membuat akun Driver maupun Admin. Registrasi publik hanya membuat akun Pelanggan.
6. Admin mengelola katalog layanan/UMKM, memantau transaksi, dan melihat laporan.

Salsabila mengerjakan fitur autentikasi, profil, serta manajemen pengguna. Jilan mengerjakan dashboard pelanggan/driver, katalog layanan, pesanan, tracking, dan laporan. Dashboard Admin menjadi titik temu kedua bagian.

## Struktur dan skema utama

Project Maven berada di folder `jasa/`. Source Java berada di `jasa/src/main/java/com/mycompany/jasa/`, dalam package `config`, `dao`, `model`, dan `view`.

Skema database tunggal yang dipakai aplikasi adalah:

- `users`: akun Pelanggan, Driver, dan Admin.
- `layanan_umkm`: katalog jasa dan mitra UMKM.
- `pesanan`: transaksi, dengan `id_driver` opsional yang merujuk ke `users.id_user`.

DAO yang ada saat ini memakai `users`. Belum ada DAO layanan/pesanan atau file SQL lain di project; method dan query akun yang tidak dipakai sudah dibersihkan. Script schema lengkap, termasuk penghapusan alias lama `pengguna`, `layanan`, dan `orders` serta Admin awal, ada di [jasa/schema.sql](jasa/schema.sql).

## Menyiapkan database

Pilih database hosting JSBJ di HeidiSQL, lalu jalankan isi `jasa/schema.sql`. Script tidak membuat atau memilih database. Script menghapus tabel alias lama jika ada, kemudian membuat tiga tabel standar dengan role `Pelanggan`, `Driver`, `Admin` dan kolom `id_driver`.

Perintah `DROP TABLE` di script hanya ditujukan untuk alias lama (`pengguna`, `layanan`, `orders`). Jalankan pada database khusus proyek ini dan ekspor data alias terlebih dahulu jika masih diperlukan.

Akun Admin awal:

- Email: `admin@jsbj.local`
- Password: `JsbjAdmin#2026`

Hash password pada SQL memakai format PBKDF2 yang sama dengan `UserDAO`. Registrasi mandiri hanya untuk Pelanggan; Admin membuat akun Driver atau Admin melalui dashboard.

## Konfigurasi koneksi database

Kredensial koneksi tidak disimpan di source code. Di root project Maven `jasa/`, salin `db.properties.example` menjadi `db.properties`, lalu isi `db.url`, `db.user`, dan `db.password` dari dashboard hosting. File `db.properties` diabaikan Git; jangan commit atau kirim file tersebut ke repository. Rekan tim perlu mengisi file lokal mereka sendiri.

Contoh format URL:

```properties
db.url=jdbc:mysql://HOST:3306/NAMA_DATABASE
db.user=USERNAME
db.password=PASSWORD
```

Driver MySQL Connector/J dideklarasikan di `jasa/pom.xml`. NetBeans/Maven akan mengunduhnya saat project di-reload.

## Membuka project

Buka folder `jasa/` sebagai project Maven di NetBeans. Pastikan database hosting sudah berisi skema dari `jasa/schema.sql` dan file lokal `jasa/db.properties` sudah diisi.
