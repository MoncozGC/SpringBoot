package com.moncozgc.mall.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * kafka生产者
 * Created by MoncozGC on 2022/11/22
 */
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String message) {
        kafkaTemplate.send("test", message);
    }
}
