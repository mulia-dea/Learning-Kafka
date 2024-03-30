# Send Message >1 Mb with Kafka
Disini saya menggunakan file(data) >1MB dengan format TXT dan JSON.
## Step1. Create Topic
Default konfigurasi kafka dalam mengirim pesan terbatas yaitu 1MB. oleh karena itu, ketika ingin mengirim pesan lebih dari 1MB, diperlukan mengubah konfigurasi.
Dalam hal ini konfigurasi yang diubah **max.message.bytes** pada saat membuat topik. **max.message.bytes** merupakan konfigurasi tambahan untuk topik baru. Di sini, kita menetapkan batas maksimum ukuran pesan (dalam byte) untuk topik tersebut. Dalam contoh ini, kita mengatur batas maksimum ukuran pesan menjadi 10.000.000 byte (atau sekitar 10 megabyte).
```
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic nama_topik \
  --config max.message.bytes=10000000
```

## Kafka Producer Configuration
Pada settingan konfigurasi procedure perlu ditambahkan properti **MAX_REQUEST_SIZE_CONFIG**. Ini digunakan untuk menentukan ukuran maksimum permintaan dalam byte yang akan dikirimkan produsen ke Kafka. Meskipun pada saat membuat topik kita sudah menambahkan config max.message.bytes, tetap diperlukan untuk konfigurasi MAX_REQUEST_SIZE_CONFIG agar ngeproduce data/pesan ke kafka.
```
Properties properties = new Properties();
properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
properties.setProperty(ProducerConfig.MAX_REQUEST_SIZE_CONFIG,"11000000");
```

## Read File Text (TXT) to String
Code dibawah merupakan untuk 
```
private static String readFileToString(String fileName) throws IOException {
    InputStream inputStream = new FileInputStream(fileName);
    StringBuilder stringBuilder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
    }
    return stringBuilder.toString();
}
```

## Read File JSON to String
private static String readFileToString(String fileName) throws IOException, ParseException {
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonArray;
    try (FileReader reader = new FileReader(fileName)) {
        jsonArray = (JSONArray) jsonParser.parse(reader);
        // Proses objek JSON sesuai kebutuhan Anda
    }
    return jsonArray.toJSONString();
}

