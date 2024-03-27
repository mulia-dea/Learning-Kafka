# Bangun Kafka Create dengan Java


# Bangun Kafka Producer dengan Java

```
package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class EventProducer {
    public static void main(String[] args) {
        String bootstrapServers = "worker2.k8s.alldataint.com:9092";
        String topic = "consumer-paralel";

        // Konfigurasi producer
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(properties);

        for(int i=10000; i<12000; i++){
            ProducerRecord record = new ProducerRecord(topic, "Data ke "+ i); //ofset ke berapa
            //kirim data
            producer.send(record);
        }
        producer.close();
    }
}
```
