# Bangun Kafka Create dengan Java
Membuat Kelas CreateTopic untuk pembuatan topik Kafka 
Ini menggunakan API Kafka AdminClient untuk membuat topik yang ditentukan dengan konfigurasi yang diinginkan.
```
package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CreateTopic {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String bootstrapServers = "worker2.k8s.alldataint.com:9092";

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        String topicName = "consumer-paralel";
        int partitions = 3;
        short replication = 1;

        AdminClient adminClient = AdminClient.create(props);
        NewTopic newTopic = new NewTopic(topicName, partitions, replication);

        KafkaFuture<Void> future = adminClient.createTopics(Collections.singleton(newTopic)).all();

        future.get();

        System.out.println("Topic "+ topicName + " created successfully");
    }
}
```
Variabel di bawah bisa dimodifikasi sesuai kebutuhan:
```
String bootstrapServers = "worker2.k8s.alldataint.com:9092";
String topicName = "consumer-paralel";
int partitions = 3;
short replication = 1;
```

# Bangun Kafka Producer dengan Java
Membuat kelas **Producer** untuk mengirimkan data ke sebuah topik pada Kafka menggunakan Kafka Producer.
```
package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        String bootstrapServers = "worker2.k8s.alldataint.com:9092";
        String topic = "consumer-paralel";

        // Konfigurasi producer
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(properties);

        for(int i=0; i<1000; i++){
            ProducerRecord record = new ProducerRecord(topic, "Data ke "+ i); 
            //kirim data
            producer.send(record);
        }
        producer.close();
    }
}
```
Pada konfigurasi properties, bisa disesuaikan dengan konfigurasi yang dibutuhkan 

Disini saya menggunakan loop untuk mengirimkan data format string ke topik Kafka.
```
for(int i=0; i<1000; i++){
    ProducerRecord record = new ProducerRecord(topic, "Data ke "+ i); 
    //kirim data
    producer.send(record);
}
```

# Bangun Kafka Consumer dengan Java
Kelas Consumer bertujuan untuk mengkonsumsi data dari sebuah topik Kafka. Ini menggunakan Kafka Consumer untuk menerima pesan dari topik yang ditentukan dalam  Kafka.
```
package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "worker2.k8s.alldataint.com:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
//        mengatur opsi untuk mengaktifkan atau menonaktifkan komit otomatis offset konsumen.
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//        mengatur interval waktu (dalam milidetik) antara komit otomatis offset konsumen ke broker
        props.put("auto.commit.interval.ms", "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList("consumer-paralel"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            records.forEach(record -> {
                System.out.println("Received message: " + record.value() + " di partisi " + record.partition() + " dengan offset ke " + record.offset());
            });
        }
    }
}
```
Pada konfigurasi properties, bisa disesuaikan dengan konfigurasi yang dibutuhkan 

# Delete Topic dengan Java
Membuat kelas 'ContohDelete' untuk menghapus sebuah topik Kafka menggunakan Kafka AdminClient.
```
package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class contohDelete {
    public static void main(String[] args) {
        String bootstrapServers = "worker2.k8s.alldataint.com:9092";

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        try {
            AdminClient adminClient = AdminClient.create(props);

            String topicName = "test-topic";
            deleteTopic(adminClient, topicName);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void deleteTopic(AdminClient adminClient, String topicName) throws ExecutionException, InterruptedException {
        KafkaFuture<Void> future = adminClient.deleteTopics(Collections.singleton(topicName)).all();

        future.get();
        System.out.println("Topic " + topicName + " deleted successfully");
    }
}
```
Modifikasi variabel di bawah sesuai konfigurasi Kafka
```
String bootstrapServers = "worker2.k8s.alldataint.com:9092";
String topicName = "test-topic";
```
