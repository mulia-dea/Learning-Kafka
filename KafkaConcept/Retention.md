# Kafka Concepts
## Retention
Retensi adalah kebijakan yang menentukan berapa lama pesan akan disimpan dalam Kafka (sistem). Setelah melewati batas waktu retensi, pesan akan dihapus

## Jenis/Kebijakan Retensi
Beberapa jenis retensi:
1. **Retensi Waktu (Time-based Retention):** Data akan disimpan dalam topik/partisi/segment selama periode waktu tertentu yang telah ditentukan sebelum dihapus. Misalnya, dalam waktu 7 hari retensi diatur, maka pesan tersebut akan disimpan dalam waktu 7 hari dan kemudian di hapus
2. **Retensi Ukuran (Size-based Retention):** Data akan disimpan dalam topik/partisi/segment hingga ukuran maksimum tertentu tercapai, kemudian di hapus. Misalnya, mengatur retensi agar topik memiliki ukuran maksimum 1 GB, setelah mencapai 1GB maka pesan tersebut akan dihapus
3. **Retensi Kombinasi (Combined Retention)**: ini merupakan kombinasi dari waktu dan ukuran. Dalam hal ini, data akan disimpan hingga mencapai salah satu batas yang ditetapkan, baik itu batas waktu maupun batas ukuran, mana yang tercapai lebih dulu.
4. **Retensi Compact**: Di mana Kafka hanya menyimpan entri/pesan terbaru untuk setiap kunci(key) yang unik dalam partisi topik. Kafka mempertahankan satu salinan terbaru untuk setiap key unik, dan data lama dengan key yang sama akan dihapus.

   **Cara kerja:**
   Ketika sebuah pesan baru dengan key yang sudah ada ditambahkan ke partisi yang diatur dengan kebijakan retensi compact maka:
   - Kafka akan memeriksa apakah kunci tersebut sudah ada/tidak sebelumnya dalam partisi.
   - Jika iya, Kafka akan mengganti entri lama dengan entri baru, sehingga hanya ada satu salinan terbaru untuk setiap key yg unik.
   - Jika tidak, Kafka akan menambahkan pesan baru ke partisi seperti biasa.
     
5. **Retensi delete**: di mana Kafka secara otomatis menghapus pesan-pesan yang telah melewati batas waktu yanng telah ditetapkan
6. **Retensi compact + delete:** ini adalah kombinasi dari retensi compact dan delete. Kombinasi compact + delete memungkinkan Kafka untuk mengoptimalkan penyimpanan data dengan hanya menyimpan entri terbaru untuk setiap key unik dan secara periodik menghapus pesan-pesan lama yang sudah tidak relevan/digunakan.

Ketika hanya menggunakan retensi compact tanpa retensi delete, data tidak akan secara otomatis dihapus dari partisi-topik berdasarkan batas waktu. 
Artinya, ketika menggunakan retensi compact, Kafka akan terus menyimpan data dalam partisi-topik, tetapi hanya akan menyimpan satu entri terbaru untuk setiap kunci(key) unik. Data lama dengan key yang sama akan dihapus dan digantikan oleh entri terbaru.
tetapi tidak secara langsung menghapus pesan-pesan berdasarkan waktu. Namun, pesan-pesan yang memenuhi syarat dan memiliki kunci unik akan tetap ada. Untuk menghapus data berdasarkan waktu tertentu atau secara otomatis, masih perlu menggunakan kebijakan retensi delete atau melakukan penghapusan manual.

