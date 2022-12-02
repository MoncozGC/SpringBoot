//package drug.erp.handle;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//
//import java.time.Duration;
//import java.util.Properties;
//
///**
// * kafka消费者
// * Created by MoncozGC on 2022/11/28
// */
//public class IMPToKafkaConsumer extends Thread {
//
//    String value = "";
//    private final boolean isRunning = true;
//
//
//    public IMPToKafkaConsumer() {
//    }
//
//    public static KafkaConsumer<String, String> getDefaultKafkaConsumer(IMPToKafkaProperties kafkaConsumerToJava2Properties) {
//        Properties properties = new Properties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerToJava2Properties.getKafkaHost());
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerToJava2Properties.getGroupId());
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerToJava2Properties.getAutoOffsetReset());
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getKeyDecoder());
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getValueDecoder());
//
//        return new KafkaConsumer<>(properties);
//    }
//
//
//    @Override
//    public void run() {
//        try {
//            IMPToKafkaProperties kafkaConsumerToJava2Properties = new IMPToKafkaProperties();
//            KafkaConsumer<String, String> consumer = getDefaultKafkaConsumer(kafkaConsumerToJava2Properties);
//            consumer.subscribe(kafkaConsumerToJava2Properties.getTopic());
//            while (isRunning) {
//                // consumer.poll参数类型为long: 0.11.0.0版本; 参数类型为Duration: 2.6.0版本
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.print(">>>>>>>>Consumer offset: " + record.offset() + " value: " + record.value() + "\n");
//                    value = record.value();
//                }
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//}
