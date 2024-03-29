package kafka.moncozgc.kafkaweb.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class is responsible to consume messages from a Kafka Topic
 */
public class KafkaConsumerService {
    private final Logger log = Logger.getLogger(KafkaConsumerService.class.getName());

    private Consumer<String, String> consumer;
    private Properties properties;

    private boolean running;

    /**
     * KafkaProducerService constructor
     *
     * @param properties
     */
    public KafkaConsumerService(Properties properties) {
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(properties);
    }

    public void start() {
        running = true;
        subscribe();
        readMessages();
    }

    private void subscribe() {
        try {
            List<String> topics = Collections.singletonList(properties.getProperty(Configuration.TOPIC_NAME));

            log.log(Level.INFO, "Subscribing to topic {0}...", topics);
            consumer.subscribe(topics);
            log.info("Consumer subscription complete.");

        } catch (IllegalArgumentException | IllegalStateException exception) {
            log.log(Level.SEVERE, "Consumer subscription failed.", exception);
        }
    }

    /**
     * This method consumes messages from Kafka
     */
    public void readMessages() {
        while (running) {
            log.info("Polling...");
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(3));
            if (!consumerRecords.isEmpty()) {
//                consumerRecords.forEach(this::logRecordInfo);
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.print(">>>>>>>>Consumer offset: " + record.offset() + " value: " + record.value() + "\n");
                }
                consumer.commitSync();
            }
        }
    }

    private void logRecordInfo(ConsumerRecord<String, String> record) {
        log.log(Level.INFO, "Message received from topic {0}, partition {1}",
                new Object[]{record.topic(), record.partition()});
        log.log(Level.INFO, "Record key: {0}", record.key());
        log.log(Level.INFO, "Record value: {0}", record.value());
        log.log(Level.INFO, "Record offset: {0}", record.offset());
    }
}
