# Kafka Concept
## Konsep Offset
offset merujuk pada posisi dalam partisi suatu topik
- **Unik per partisi**: Setiap partisi dalam sebuah topik memiliki serangkaian offset yang terpisah. Dengan demikian, offset 0 dalam partisi A tidak sama dengan offset 0 dalam partisi B.
- **Berurutan:** Offset dalam suatu partisi diatur secara berurutan, artinya offset berikutnya selalu lebih besar dari offset sebelumnya.
- **Menandai posisi konsumsi:** Consumer Kafka menggunakan offset untuk menandai posisi mereka dalam partisi saat membaca pesan-pesan dari topik.
- **Dikelola oleh consumer**: Konsumen bertanggung jawab atas manajemen offset mereka sendiri. Consumer dapat memutuskan kapan harus mengirimkan komit offset ke broker (dalam mode auto commit atau manual)

## Konfigurasi Offset
- **Enable Auto Commit:** Properti ini menentukan apakah komit offset otomatis diaktifkan **(true)** atau dinonaktifkan **(false)** untuk konsumen. Ketika diaktifkan, konsumen secara otomatis akan mengirimkan komit offset ke broker pada interval waktu tertentu.
- **Auto Commit Interval:** Properti ini menentukan interval waktu (dalam milidetik) antara komit otomatis. Konsumen akan mengirimkan komit offset ke broker setiap kali interval ini berlalu.
- **AUTO_OFFSET_RESET_CONFIG:** properti konfigurasi yang digunakan dalam konsumen (consumer) Kafka untuk menentukan perilaku konsumen ketika mereka bergabung dengan kelompok konsumen (consumer group). Valuenya terdiri dari:
  - **earliest:** konsumen akan mulai membaca pesan-pesan yang paling awal yang tersedia dalam topik.
  - **latest:** Ini berarti konsumen akan mulai membaca pesan-pesan yang baru saja dipublikasikan ke topik.
 
  
