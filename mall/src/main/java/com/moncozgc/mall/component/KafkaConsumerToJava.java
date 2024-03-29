package com.moncozgc.mall.component;

import com.moncozgc.mall.utils.PropertiesUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;

/**
 * kafka消费者 【数据一直可能无法消费到数据, 由于配置了offset的属性, 暂时注释掉】
 * 配置信息在: KafkaConsumerToJava2Properties.java
 * Created by MoncozGC on 2022/11/25
 */
public class KafkaConsumerToJava {

    public static KafkaConsumer<String, String> getDefaultKafkaConsumer(KafkaConsumerProperties kafkaConsumerToJava2Properties) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerToJava2Properties.getKafkaHost());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerToJava2Properties.getGroupId());
//        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerToJava2Properties.getAutoCommit());
//        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaConsumerToJava2Properties.getAutoCommitIntervalMs());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerToJava2Properties.getAutoOffsetReset());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getKeyDecoder());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getValueDecoder());
        return new KafkaConsumer<>(properties);
    }

    public static void main(String[] args) {
        try {
            Properties properties = PropertiesUtil.loadProperties("task.properties");
            KafkaConsumerProperties kafkaConsumerToJava2Properties = new KafkaConsumerProperties();
            KafkaConsumer<String, String> consumer = getDefaultKafkaConsumer(kafkaConsumerToJava2Properties);
            consumer.subscribe(kafkaConsumerToJava2Properties.getTopic());
            String offset = "";
            while (Boolean.TRUE) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.print(">>>>>>>>Consumer offset: " + record.offset() + " value: " + record.value() + "\n");
                    offset = record.offset() + "";
                }
                // 写入将offset写入redis中【需要找到合适的地方写入redis中】
//                RedisUtil.insertJedis(properties, "kafkaERP", offset);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}