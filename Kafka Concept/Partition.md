# Kafka Concept 
## Definisi Paartisi 
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

## Retention
