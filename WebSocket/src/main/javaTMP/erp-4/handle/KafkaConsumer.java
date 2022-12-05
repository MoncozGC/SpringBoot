package drug.erp.handle;

import bottle.mq_kafka.KConsumerWarp;
import bottle.mq_kafka.KFKConsumeMessageCallback;
import bottle.mq_kafka.KafkaUtil;
import jdbc.imp.TomcatJDBC;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 * Created by pengqi on 2022/11/25
 */
public class KafkaConsumer {
    public static void main(String[] args) {
        String groupName = "bhds_erp_binlog_sync_test_group1";
        String[] topics = {"test"};

        KFKConsumeMessageCallback kfkConsumeMessageCallback = new KFKConsumeMessageCallback() {
            @Override
            public boolean batch(ConsumerRecords<String, String> records) {
                return true;
            }

            @Override
            public boolean singe(ConsumerRecord<String, String> record) {
                return false;
            }
        };

        KafkaUtil.addConsume(groupName, topics, kfkConsumeMessageCallback);

        String serverAddress = "139.9.91.167:9092";
        String groupId = "bhds_erp_binlog_sync_test_group1";
        KConsumerWarp kConsumerWarp = new KConsumerWarp(serverAddress, groupId, kfkConsumeMessageCallback, "test");
        kConsumerWarp.run();
    }
}
