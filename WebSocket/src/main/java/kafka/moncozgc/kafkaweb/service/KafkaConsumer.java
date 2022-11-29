package kafka.moncozgc.kafkaweb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author MoncozGC
 * @Date 2022/11/18
 * @Description
 */
public class KafkaConsumer {
    // 接收主题消费者
    private static ConsumerConnector backconsumer;

    // 接收主题
    public static String BACKTOPIC = "websocketTopic";

    boolean starttype = false;

    public void initkafka() {
        if (starttype) {
            return;
        }
        try {
            initBackConsumer();
            startBackConsumer();
            starttype = true;
        } catch (Exception e) {
            starttype = false;
            backconsumer.shutdown();
        }

    }

    /**
     * 初始化接收主题消费者
     */
    private static void initBackConsumer() {
        Properties props = new Properties();
        // zookeeper 集群地址
        props.put("zookeeper.connect",
                "xx.xx.xx.xx:2181,2xx.xx.xx.xx:2181,xx.xx.xx.xx:2181");
        // group 代表一个消费组
        props.put("group.id", "csnWebSocket-group"); //组名任意写
        // zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "5000");
        props.put("auto.offset.reset", "largest");
        props.put("enable.auto.commit", "false");
        // 序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        ConsumerConfig config = new ConsumerConfig(props);

        backconsumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
    }

    /**
     * 开启接收主题消费者线程
     */
    public static void startBackConsumer() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
                topicCountMap.put(BACKTOPIC, new Integer(1));
                StringDecoder keyDecoder = new StringDecoder(
                        new VerifiableProperties());
                StringDecoder valueDecoder = new StringDecoder(
                        new VerifiableProperties());
                Map<String, List<KafkaStream<String, String>>> consumerMap = backconsumer
                        .createMessageStreams(topicCountMap, keyDecoder,
                                valueDecoder);
                KafkaStream<String, String> stream = consumerMap.get(BACKTOPIC)
                        .get(0);
                ConsumerIterator<String, String> it = stream.iterator();
                String message = "";
                JSONObject obj = new JSONObject();
                while (true) {
                    while (it.hasNext()) {
                        message = it.next().message();
                        try {
                            obj = JSON.parseObject(message);
                            for (WebSocket socket: WebSocket.webSocketSet
                            ) {
                                try {
                                    //webSocket推送消息
                                    socket.sendMessage(obj.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (JSONException e) {
                            continue;
                        }

                    }
                }
            }
        }, "BackThread").start();
    }
}
