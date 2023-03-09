package kafka.moncozgc.kafkaweb.consumer;

import java.util.Properties;


public class ConsumerMain {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(Configuration.Mode.CONSUMER);

        Properties properties = configuration.loadPropertiesFile();

        KafkaConsumerService consumer = new KafkaConsumerService(properties);
        consumer.start();
    }
}