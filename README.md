# Personal Finance Tracker

## Deskripsi Proyek

**Personal Finance Tracker** adalah sebuah aplikasi berbasis Java yang dirancang untuk membantu pengguna mengelola keuangan pribadi mereka. Aplikasi ini memungkinkan pengguna untuk mencatat transaksi (pendapatan dan pengeluaran), mengelola kategori transaksi, menetapkan anggaran, serta menghasilkan laporan keuangan. Proyek ini dibangun dengan pendekatan berorientasi objek, menggunakan interface untuk memisahkan fungsionalitas yang disediakan (provided) dan yang dibutuhkan (required) antar kelas, sehingga menciptakan desain yang modular dan terstruktur dengan alur yang jelas.

## Anggota Kelompok

- **Muhammad Mahathir** (2208107010056)
- **Irfan Rizadi** (2208107010062)

## Fitur Aplikasi

1. **Tambah Transaksi**: Mencatat transaksi baru (pendapatan/pengeluaran) dengan deskripsi, jumlah (dalam Rupiah), kategori, dan tanggal.
2. **Lihat Semua Transaksi**: Menampilkan daftar semua transaksi beserta total pendapatan dan pengeluaran.
3. **Tambah Kategori**: Menambahkan kategori baru untuk pendapatan atau pengeluaran.
4. **Lihat Semua Kategori**: Menampilkan daftar semua kategori yang tersedia.
5. **Tetapkan Anggaran**: Menetapkan anggaran bulanan untuk kategori pengeluaran tertentu.
6. **Lihat Penggunaan Anggaran**: Menampilkan persentase penggunaan anggaran berdasarkan transaksi.
7. **Laporan Pengeluaran per Kategori**: Menghasilkan laporan pengeluaran berdasarkan kategori dalam rentang tanggal tertentu.
8. **Laporan Pendapatan vs Pengeluaran**: Menghasilkan laporan yang membandingkan pendapatan dan pengeluaran serta status keuangan (surplus/defisit/seimbang).

## Alur Aplikasi

1. **Memulai Aplikasi**: Pengguna menjalankan kelas utama `PersonalFinanceTracker`, yang menampilkan menu utama.
2. **Inisialisasi**: Aplikasi menginisialisasi komponen seperti `CategoryManager` (dengan kategori default), `TransactionManager`, `BudgetManager`, dan `ReportGenerator`, serta menghubungkannya melalui metode `connectTo*`.
3. **Interaksi Pengguna**:
   - Pengguna memilih opsi dari menu (1-8 atau 0 untuk keluar).
   - Setiap opsi memanggil fungsi terkait, seperti menambah transaksi atau membuat laporan.
4. **Keluar**: Pengguna memilih opsi "0" untuk mengakhiri aplikasi.

## Struktur Interface

Aplikasi ini menggunakan pendekatan interface untuk memisahkan tanggung jawab antar kelas. Interface dibagi menjadi **provided** (yang disediakan oleh kelas) dan **required** (yang dibutuhkan oleh kelas dari kelas lain).

### Interface Provided

- **`ITransactionManager`**: Mengelola transaksi (tambah, lihat semua, lihat berdasarkan tanggal, hitung total pendapatan/pengeluaran).
- **`ICategoryManager`**: Mengelola kategori (tambah, lihat semua, cek keberadaan, ambil detail kategori).
- **`IBudgetManager`**: Mengelola anggaran (tetapkan anggaran, lihat anggaran, hitung persentase penggunaan).
- **`IReportGenerator`**: Menghasilkan laporan (pengeluaran per kategori, pendapatan vs pengeluaran).

### Interface Required

- **`TransactionManager`** membutuhkan `ICategoryManager` untuk memverifikasi kategori saat menambah transaksi.
- **`BudgetManager`** membutuhkan:
  - `ICategoryManager` untuk memverifikasi kategori.
  - `ITransactionManager` untuk menghitung penggunaan anggaran berdasarkan transaksi.
- **`ReportGenerator`** membutuhkan:
  - `ITransactionManager` untuk mengambil data transaksi.
  - `ICategoryManager` untuk mengambil informasi kategori.

## Struktur Kode

- **`Transaction.java`**: Kelas model untuk menyimpan data transaksi.
- **`Category.java`**: Kelas model untuk menyimpan data kategori.
- **`Budget.java`**: Kelas model untuk menyimpan data anggaran.
- **`TransactionManager.java`**: Implementasi `ITransactionManager`.
- **`CategoryManager.java`**: Implementasi `ICategoryManager`.
- **`BudgetManager.java`**: Implementasi `IBudgetManager`.
- **`ReportGenerator.java`**: Implementasi `IReportGenerator`.
- **`PersonalFinanceTracker.java`**: Kelas utama untuk menjalankan aplikasi dan menampilkan menu.

## Cara Menjalankan

1. Pastikan Anda memiliki Java Development Kit (JDK) terinstal.
2. Kompilasi file Java ke direktori `out`:
   ```bash
   javac -d out PersonalFinanceTracker.java
   ```
3. Jalankan aplikasi dari direktori `out`:
   ```bash
   java -cp out PersonalFinanceTracker
   ```
4. Ikuti instruksi di layar untuk menggunakan fitur aplikasi.

## Kontribusi

Proyek ini dibuat sebagai bagian dari tugas akademik oleh Muhammad Mahathir dan Irfan Rizadi. Jika ada saran atau perbaikan, silakan hubungi kami melalui email kampus.
