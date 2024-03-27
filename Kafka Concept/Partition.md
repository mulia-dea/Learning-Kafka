# Kafka Concept 
## Definisi Partisi 
- Partisi adalah cara Kafka membagi data dalam topik menjadi bagian-bagian yang terpisah.
- Setiap topik dalam Kafka dapat memiliki beberapa partisi. 
- Setiap pesan yang dipublikasikan ke topik disimpan dalam salah satu dari partisi-partisi ini.

## Manfaat Partisi
Partisi memungkinkan Kafka untuk membagi beban kerja, mempercepat proses, dan meningkatkan daya tahan terhadap kesalahan.
- **Skalabilitas**: Dengan memiliki beberapa partisi, Kafka dapat menyebarkan beban message di tiap partisi yang memungkinkan Kafka untuk menangani aliran data besar dengan efisien.
- **Pemrosesan Paralel**: Dengan membagi topik menjadi beberapa partisi, kafka memungkinkan pemrosesan data paralel, di mana setiap konsumen dapat bekerja pada sebagian dari data secara independen.
- **Toleransi Kesalahan** :Setiap partisi dapat memiliki replika, yang merupakan salinan dari partisi yang asli. Jika sebuah partisi mengalami kegagalan, Kafka dapat terus melayani data dari replikasi partisi yang masih aktif.

## Konfigurasi Partisi
Ukuran Partisi: Penting untuk memperhatikan ukuran partisi. Partisi yang terlalu besar dapat menyebabkan kinerja yang buruk dan meningkatkan kompleksitas manajemen.

## Segmen
### Konsep Segmen
- Segmen adalah unit dasar dalam penyimpanan data Kafka yang digunakan untuk menyimpan pesan-pesan yang diterima oleh topik
- Setiap partisi tersebut dapat menjadi besar seiring waktu karena akumulasi pesan baru. Jika partisi menjadi terlalu besar, ini dapat menyebabkan kinerja yang buruk dan kesulitan dalam manajemen.
- Segmentasi di partisi adalah proses membagi partisi menjadi segmen-segmen yang lebih kecil.
- Setiap partisi terdiri dari beberapa segmen yang menyimpan pesan-pesan data
- Pemisahan partisi menggunakan segmen untuk mengatur dan menyimpan data secara efisien
- Segmen-segmen baru dibuat secara otomatis oleh Kafka saat partisi ditulis.
- Setelah segmen mencapai batas ukuran tertentu atau batas waktu tertentu, Kafka menutup segmen itu dan memulai segmen baru.
- Batas segment bisa berdasarkan jumlah pesan atau ukuran data. Jika batas segmen tidak ditentukan secara eksplisit, maka kafka akan menetapkan nya secara default

### Masalah Segmentasi Pada Partisi
Pembagian segmen dalam partisi ditentukan dalam kafka sendiri berdasarkan konfigurasi retensi yang telah ditetapkan oleh pengguna. 
1. **Ukuran Segmen Terlalu Besar:**
   - Segmen-segmen yang terlalu besar dapat menyebabkan keterlambatan dalam pengiriman pesan dan meningkatkan waktu pemulihan dalam kasus kegagalan.
   - Hal ini dapat terjadi jika kebijakan retensi tidak diatur dengan benar atau jika terjadi lonjakan trafik yang menghasilkan segmen-segmen besar.
2. **Pembuatan Segmen Terlalu Sering:**
   - Pembuatan segmen-segmen baru terlalu sering dapat menyebabkan overhead yang tidak perlu dalam manajemen partisi-topik.
   - Ini dapat terjadi jika kebijakan retensi waktu terlalu singkat atau jika konfigurasi retensi ukuran tidak diatur dengan baik.


