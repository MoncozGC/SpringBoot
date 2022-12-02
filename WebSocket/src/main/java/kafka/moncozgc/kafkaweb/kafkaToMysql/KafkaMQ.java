package kafka.moncozgc.kafkaweb.kafkaToMysql;


import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.OptionHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.*;

public class KafkaMQ extends Thread {
//    private static Logger logger = LoggerBuilder.getLogger("KafkaMQ");
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(KafkaMQ.class);

    private String topic;
    private String group;

    public KafkaMQ(String topic, String group){
        this.topic = topic;
        this.group = group;
    }

    @Override
    public void run() {
        KafkaConsumer consumer = createConsumer(this.group);
        consumer.subscribe(Collections.singletonList(this.topic));
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(80);
            for(ConsumerRecord<String,String> record : records){
                String value = record.value();
                System.out.println("kakfa consumer data >>> " + value);
                JSONObject obj = JSON.parseObject(value);
                // kafka_test_dj
                if("INSERT".equalsIgnoreCase(obj.getString("type"))
                        &&"test".equalsIgnoreCase(obj.getString("database"))
                        &&"kafka_test_dj".equalsIgnoreCase(obj.getString("table"))){
                    JSONArray dataArry = obj.getJSONArray("data");
                    if( dataArry != null && dataArry.size() > 0 ){
                        for(int i = 0; i < dataArry.size(); i++){
                            DataWriter.addCacheData(dataArry.getJSONObject(i).toJSONString());
                        }
                    }
                }

            }
            try{
                consumer.commitSync();
            }catch (CommitFailedException e){
//                logger.error("commit failed msg" + e.getMessage());
            }
        }
    }
    private KafkaConsumer createConsumer(String groupName) {
        Properties kfkProperties = new Properties();
        kfkProperties.put("bootstrap.servers", "192.168.153.100:9092");
        kfkProperties.put("group.id", groupName);//根据需要修改group
        kfkProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kfkProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kfkProperties.put("enable.auto.commit",false);
        kfkProperties.put("auto.offset.reset","latest");
        return new KafkaConsumer<>(kfkProperties);
    }
    private static final Map<String,Logger> container = new HashMap<>();
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String ORACLE_DBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String SQLSERVER_DBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    /*must be config begin*/
    public static final String LOG_PATH="/tmp/scraper/logs/";
    /*must be config end*/
    public static final String UNISERVICE_SCRETKEY = "secretKey";
    public static final String UNISERVICE_TOKEN = "accessToken";
    public static final String UNISERVICE_CHANNEL = "channel";
    public static final String UNISERVICE_SERVICE_ID = "serviceId";
    public static final String KAFKA_MESSAGE_BODY = "kafkaBody";
    public static final String KAFKA_MESSAGE_PARTITION_KEY = "kafkaPartiKey";
    public static Logger getLogger(String name) {
        Logger logger = container.get(name);
        if(logger != null) {
            return logger;
        }
        synchronized (KafkaMQ.class) {
            logger = container.get(name);
            if(logger != null) {
                return logger;
            }
            logger = build(name);
            container.put(name,logger);
        }
        return logger;
    }

    private static Logger build(String name) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.SIMPLIFIED_CHINESE);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger("FILE-" + name);
        logger.setAdditive(false);
        RollingFileAppender appender = new RollingFileAppender();
        appender.setContext(context);
        appender.setName("FILE-" + name);
        appender.setFile(OptionHelper.substVars(format.format(new Date())+"/"+ name + ".log",context));
        appender.setAppend(true);
        appender.setPrudent(false);
        SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();
        String fp = OptionHelper.substVars(format.format(new Date())+"/"+ name + "/.%d{yyyy-MM-dd}.%i.log",context);
        FileSize fileSize = new FileSize(1024);
        policy.setMaxFileSize(fileSize);
        policy.setFileNamePattern(fp);
        policy.setMaxHistory(7);
        policy.setTotalSizeCap(FileSize.valueOf("32GB"));
        policy.setParent(appender);
        policy.setContext(context);
        policy.setCleanHistoryOnStart(true);
        policy.start();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d %p (%file:%line\\)- %m%n");
        encoder.start();
        PatternLayoutEncoder encoder1 = new PatternLayoutEncoder();
        encoder1.setContext(context);
        encoder1.setPattern("%d %p (%file:%line\\)- %m%n");
        encoder1.start();
        appender.setRollingPolicy(policy);
        appender.setEncoder(encoder);
        appender.start();
        /*设置动态日志控制台输出*/
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(encoder1);
        consoleAppender.start();
        logger.addAppender(consoleAppender);
        logger.addAppender(appender);
        return logger;
    }
}