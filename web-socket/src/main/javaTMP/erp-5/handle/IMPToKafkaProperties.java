package drug.erp.handle;


import bottle.properties.abs.ApplicationPropertiesBase;
import bottle.properties.annotations.PropertiesFilePath;
import bottle.properties.annotations.PropertiesName;

import java.util.Collection;
import java.util.Collections;

/**
 * Kafka通用配置.
 *
 * Created by MoncozGC on 2022/11/25
 */
@PropertiesFilePath("/kafkamq-im.properties")
public class IMPToKafkaProperties {

    static {
        ApplicationPropertiesBase.initStaticFields(IMPToKafkaProperties.class);
    }

    /**
     * Kafka主机
     */
    @PropertiesName("server.address")
    private static String kafkaHost = "139.9.91.167:9092";

    /**
     * 消费topic的组ID
     */
    @PropertiesName("group.id")
    private static String groupId;

    /**
     * TOPIC
     */
    private static Collection<String> topic = Collections.singleton("test");

    /**
     * 消息确认
     */
    private static String ack = "all";

    /**
     * 后台定期提交offset
     */
    private static String autoCommit = "true";

    /**
     * 消费者提交offset的时间间隔：单位：毫秒，当enable.auto.commit为true时生效
     */
    private static String autoCommitIntervalMs = "1000";

    /**
     * 键解码器
     */
    private static String keyDecoder = "org.apache.kafka.common.serialization.StringDeserializer";

    /**
     * 值解码器
     */
    private static String valueDecoder = "org.apache.kafka.common.serialization.StringDeserializer";

    /**
     * 消费者：重启后配置offset
     * earliest：消费者恢复到当前topic最早的offset
     * latest：消费者从最新的offset开始消费
     * none：如果消费者组没找到之前的offset抛出异常
     * 其他任何值都会抛出异常
     */
    private static String autoOffsetReset = "latest";


    public IMPToKafkaProperties() {

    }

    @Override
    public String toString() {
        return "KafkaCommonProperties{" +
                "kafkaHost='" + kafkaHost + '\'' +
                ", ack='" + ack + '\'' +
                ", groupId='" + groupId + '\'' +
                ", autoCommit='" + autoCommit + '\'' +
                ", autoCommitIntervalMs='" + autoCommitIntervalMs + '\'' +
                ", keyDecoder='" + keyDecoder + '\'' +
                ", valueDecoder='" + valueDecoder + '\'' +
                ", autoOffsetReset='" + autoOffsetReset + '\'' +
                ", topic=" + topic +
                '}';
    }

    public static String getKafkaHost() {
        return kafkaHost;
    }

    public static void setKafkaHost(String kafkaHost) {
        IMPToKafkaProperties.kafkaHost = kafkaHost;
    }

    public static String getAck() {
        return ack;
    }

    public static void setAck(String ack) {
        IMPToKafkaProperties.ack = ack;
    }

    public static String getGroupId() {
        return groupId;
    }

    public static void setGroupId(String groupId) {
        IMPToKafkaProperties.groupId = groupId;
    }

    public static String getAutoCommit() {
        return autoCommit;
    }

    public static void setAutoCommit(String autoCommit) {
        IMPToKafkaProperties.autoCommit = autoCommit;
    }

    public static String getAutoCommitIntervalMs() {
        return autoCommitIntervalMs;
    }

    public static void setAutoCommitIntervalMs(String autoCommitIntervalMs) {
        IMPToKafkaProperties.autoCommitIntervalMs = autoCommitIntervalMs;
    }

    public static String getKeyDecoder() {
        return keyDecoder;
    }

    public static void setKeyDecoder(String keyDecoder) {
        IMPToKafkaProperties.keyDecoder = keyDecoder;
    }

    public static String getValueDecoder() {
        return valueDecoder;
    }

    public static void setValueDecoder(String valueDecoder) {
        IMPToKafkaProperties.valueDecoder = valueDecoder;
    }

    public static String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public static void setAutoOffsetReset(String autoOffsetReset) {
        IMPToKafkaProperties.autoOffsetReset = autoOffsetReset;
    }

    public static Collection<String> getTopic() {
        return topic;
    }

    public static void setTopic(Collection<String> topic) {
        IMPToKafkaProperties.topic = topic;
    }
}

