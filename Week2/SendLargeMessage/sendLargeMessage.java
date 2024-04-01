package org.example;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Properties;


public class Producer {
    public static void main(String[] args) throws IOException {
        String bootstrapServers = "worker2.k8s.alldataint.com:9092";
        String topic = "topic-long-message";
        // Konfigurasi producer
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.MAX_REQUEST_SIZE_CONFIG,"11000000");

        String fileName = "message.txt";

        try {
            // Baca isi file menjadi String
            String fileContent = readFileToString(fileName);

            //Untuk read file Json To String
            //String fileContent = readJsonToString(fileName);

            // Inisialisasi KafkaProducer
            org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(properties);

            // Kirim pesan ke topik Kafka
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, fileContent);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("Pesan berhasil dikirim ke partisi: " + metadata.partition() +
                                ", offset: " + metadata.offset());
                    } else {
                        System.err.println("Gagal mengirim pesan: " + exception.getMessage());
                    }
                }
            });
            // Tutup producer Kafka
            producer.close();

        } catch (IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }

    }

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

    private static String readJsonToString(String fileName) throws IOException, ParseException {
        org.json.simple.parser.JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        try (FileReader reader = new FileReader(fileName)) {
            jsonArray = (JSONArray) jsonParser.parse(reader);
            // Proses objek JSON sesuai kebutuhan Anda
        }
        return jsonArray.toJSONString();
    }