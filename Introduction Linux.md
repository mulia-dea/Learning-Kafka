# Introduction Linux
## Linux Permission
### User dan Group
* **User**
  - Sebuah "user" adalah entitas tunggal yang diidentifikasi oleh sistem Linux
  - Ketika seorang pengguna masuk ke dalam sistem, identitasnya (termasuk hak akses dan hak istimewa) ditentukan oleh akun pengguna tersebut
  - Seorang pengguna memiliki direktori rumah di sistem, yang biasanya digunakan untuk menyimpan file dan konfigurasi pribadi.
* **Group**
  - Kumpulan satu atau lebih pengguna yang berkumpul dalam satu unit.
  - Kumpulan dari beberapa pengguna yang dapat berbagi akses ke file dan folder tertentu
  - Seorang pengguna dapat menjadi anggota dari beberapa grup, yang berarti ia dapat membagi izin dan hak akses dengan pengguna lain dalam grup yang sama
* **Keuntungan Konsep User dan Group**
  - **Manajemen Akses yang Terpusat:** administrator sistem dapat mengelola akses file dan folder secara lebih terpusat.Sebagai contoh, administrator dapat memberikan izin tertentu kepada satu grup dan semua anggota grup tersebut secara otomatis mendapatkan akses tersebut.
  - **Peningkatan Keamanan:** Dengan memberikan izin akses yang sesuai kepada grup yang tepat, administrator dapat memastikan bahwa pengguna hanya memiliki akses ke file yang mereka butuhkan untuk pekerjaan mereka. Ini membantu mengurangi risiko penyalahgunaan atau akses yang tidak sah
  - **Pemisahan Tugas:** Konsep user dan group memungkinkan pemisahan tugas dan hak akses dalam organisasi atau lingkungan kerja. Ini memungkinkan administrator untuk mengatur izin berdasarkan peran atau tanggung jawab pengguna, yang membantu dalam menjaga integritas data dan keamanan sistem.

### Permission
Setiap file dan direktori di sistem Linux memiliki tiga jenis izin: izin untuk pemilik (owner), grup, dan pengguna lain (lainnya).
Tiga jenis izin yang biasa digunakan adalah:
* **Read (R):** Memungkinkan pengguna membaca isi dari file atau melihat isi dari sebuah direktori.
* **Write (W):** Memungkinkan pengguna menulis atau mengubah file, serta membuat atau menghapus file di dalam sebuah direktori.
* **Execute (X):**
  - Memungkinkan pengguna menjalankan file (jika itu adalah program) atau mengakses isi dari sebuah direktori (jika izin X diberikan untuk sebuah direktori). Untuk direktori:
  - Jika perizinan ini diberikan pada sebuah direktori, pengguna dapat masuk ke dalam direktori tersebut dan menjalankan perintah di dalamnya.

### Commands Tools
- chmod: change permissions of a file
- chown: change the ownership of a file
- rm: removes files/directories
