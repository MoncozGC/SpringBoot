package com.moncozgc.mall.component;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * Created by MoncozGC on 2022/11/25
 */
public class KafkaConsumerToJava2 {
    public static void main(String[] args) {
        // 1.创建消费者的配置对象
        Properties properties = new Properties();
        // 2.给消费者配置对象添加参数
        KafkaConsumerProperties kafkaConsumerToJava2Properties = new KafkaConsumerProperties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerToJava2Properties.getKafkaHost());
        // 配置序列化 必须
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 配置消费者组（组名任意起名） 必须
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        // 创建消费者对象
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        // 注册要消费的主题（可以消费多个主题）
        ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        /**
         * subscribe() 方法接收一个主题列表作为参数。我们也可以在调用 subscribe() 方法时传入一个正则表达式。正则表达式可以匹配多个主题，如果有人创建一个新的主题，并且主题的名字与正则表达式匹配，那么会立即触发一次再均衡，消费者就可以读取新添加的主题。如果应用程序需要读取多个主题，并且可以处理不同类型的数据，那么这种方式就很管用。在 KIafka 和其他系统之间复制数据时，使用正则表达式的方式订阅多个主题是很常见的做法。例如：cust.subscribe("test.*);
         * 原文链接：https://blog.csdn.net/zhengzhaoyang122/article/details/116074165
         */
        kafkaConsumer.subscribe(topics);
        // 拉取数据打印
        while (true) {
            /**
             * 轮询（while循环）不只是获取数据那么简单。在第一调用一个新消费者的 poll() 方法时，它会负责查找 GroupCoordinator 协调器，然后加入群组，接收分配的分区。如果发生了再均衡，这个过程也是在轮询期间发生的。当然，心跳也是从轮询里发送出来的，所以，我们要确保在轮询期间所做的任何处理工作都应该尽快完成。
             * 原文链接：https://blog.csdn.net/zhengzhaoyang122/article/details/116074165
             */
            // 设置 1s 中消费一批数据
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(100));
            // 打印消费到的数据
            // poll 方法返回一个记录列表。每条记录都包含了记录所属主题的信息、记录所在分区的信息，记录在分区里的偏移量，以及记录的键值对
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);
            }

            // 异步提交offset
            kafkaConsumer.commitAsync();
        }
    }
}
