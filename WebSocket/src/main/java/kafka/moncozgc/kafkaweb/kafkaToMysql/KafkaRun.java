package kafka.moncozgc.kafkaweb.kafkaToMysql;


import ch.qos.logback.classic.Logger;

/**
 * 将kafka的数据信息保持至mysql
 * https://blog.csdn.net/mochou111/article/details/105639638
 *
 * JSON数据格式: {"data":[{"id": "1081","name": "数据测试","create_datetime": "2012-11-29 15:12:21"}]}
 * mysql创建语句:
 * CREATE TABLE `kafka_test_dj`  (
 *   `oid` bigint(20) NOT NULL COMMENT '业务无关-数据关联ID-代码生成唯一/RedisGlobalKeys.genTableOID',
 *   `identity` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '消息标识',
 *   `create_datetime` VARCHAR(48) NULL COMMENT '时间',
 *   `datetime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
 * ) ;
 *
 */
public class KafkaRun {
    private static Logger logger = KafkaMQ.getLogger("KafkaRun");

    public static void main(String[] args){

        DataWriter dataWriter = new DataWriter();
        for( int i = 0; i < 32; i++ ){
            new KafkaMQ("test","tes").start();
            if( i%8 == 0 ){
                new Thread(dataWriter).start();
            }
        }
    }
}
