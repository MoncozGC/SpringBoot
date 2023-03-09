//package drug.erp.handle;
//
//import bottle.mq_kafka.KConsumerWarp;
//import bottle.mq_kafka.KFKConsumeMessageCallback;
//import bottle.mq_kafka.KafkaUtil;
//import jdbc.define.option.JDBCUtils;
//import jdbc.imp.TomcatJDBC;
//import jdbc.imp.TomcatJDBCDAO;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//
//import java.util.List;
//import java.util.Properties;
//
///**
// * Created by MoncozGC on 2022/11/25
// */
//public class KafkaConsumer {
//    public static void main(String[] args) {
////        String groupName = "bhds_erp_binlog_sync_test_group1";
////        String[] topics = {"test"};
////
////        KFKConsumeMessageCallback kfkConsumeMessageCallback = new KFKConsumeMessageCallback() {
////            @Override
////            public boolean batch(ConsumerRecords<String, String> records) {
////                return true;
////            }
////
////            @Override
////            public boolean singe(ConsumerRecord<String, String> record) {
////                return false;
////            }
////        };
////
////        KafkaUtil.addConsume(groupName, topics, kfkConsumeMessageCallback);
////
////        String serverAddress = "139.9.91.167:9092";
////        String groupId = "bhds_erp_binlog_sync_test_group1";
////        KConsumerWarp kConsumerWarp = new KConsumerWarp(serverAddress, groupId, kfkConsumeMessageCallback, "test");
////        kConsumerWarp.run();
//        TomcatJDBC.initializeCatchException("mysql-im.properties");
//        String select = " SELECT oid, identity, content, create_datetime FROM {{?tb_push_message_system}} WHERE cstatus&1=0 AND msg_status&2=0 AND identity=?";
//        String identityName = "1577798539000001002";
//        List<Object[]> lines = TomcatJDBCDAO.queryNoPage(select, new Object[]{identityName});
//
//        for (Object[] line : lines) {
//            for (Object o : line) {
//                System.out.println(o.toString());
//            }
//        }
//        IMPToKafkaProperties impToKafkaProperties = new IMPToKafkaProperties();
//        System.out.println(impToKafkaProperties.getKafkaHost());
//
//    }
//}
