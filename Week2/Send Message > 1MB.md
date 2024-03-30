# Send Message >1 Mb with Kafka
Disini saya menggunakan file(data) >1MB dengan format TXT dan JSON.
## Step1. Create Topic
Default konfigurasi kafka dalam mengirim pesan terbatas yaitu 1MB. oleh karena itu, ketika ingin mengirim pesan lebih dari 1MB, diperlukan mengubah konfigurasi.
Dalam hal ini konfigurasi yang diubah **max.message.bytes** pada saat membuat topik. Disini saya mengubah nya menjadi 10000000 atau 10MB.
```
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic nama_topik \
  --config max.message.bytes=10000000
```
## Kafka Producer Configuration

