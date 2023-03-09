package drug.erp.handle;

import Ice.Communicator;
import bottle.util.Log4j;
import framework.server.IMServerImps;
import framework.server.IPMessage;
import framework.server.IPersistentMessage;
import framework.server.Initializer;
import jdbc.imp.GenUniqueID;
import jdbc.imp.TomcatJDBC;
import jdbc.imp.TomcatJDBCDAO;
import jdbc.imp.TomcatJDBCTool;
import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * @Author: leeping
 * @Date: 2019/12/30 17:03
 */
public class IPersistentMessageImp  extends Thread implements IPersistentMessage, Initializer {

    private volatile boolean isRunning = true;

    // 批量修改列表
    private  final  LinkedBlockingQueue<Object[]> successMessageIdentityQueue = new LinkedBlockingQueue<>();

    @Override
    public long waitSendMessage(IPMessage message) {
        //等待发送的数据存入数据库

        String content = message.getContent();
        if (content.startsWith("ON_CACHE:")){
            return -1;
        }
        String identity = message.getIdentityName();
        String createTime = message.getTime();
        String insert = "INSERT INTO {{?tb_push_message_system}} ( oid, identity, content, msg_status, create_datetime ) VALUE ( ? , ? , ? , ? , ?)";
        long oid = GenUniqueID.secondID.currentTimestampLong();
        int i = TomcatJDBCDAO.update(insert,new Object[]{oid,identity,content,1,createTime});
        return i>0 ? oid : -1;
    }

    @Override
    public void sendMessageSuccess(IPMessage message) {
       //发送成功的数据
        System.out.println(" 发送成功: "+ Thread.currentThread()+" "+ message.getId()+","+message.getIdentityName()+">> "+message.getContent());
        successMessageIdentityQueue.offer(new Object[]{message.getId()});
//        // 加入批量修改列表
//        String update = "UPDATE {{?tb_push_message_system}} SET msg_status = msg_status|2 WHERE oid = ?";
//        TomcatJDBCDAO.update(update,new Object[]{message.getId()});
    }

    @Override
    public List<IPMessage> getOfflineMessageFromIdentityName(String identityName) {
//        System.out.println(Thread.currentThread()+" 查询离线消息 "+ identityName);
        //查询数据库是否存在等待发送的消息
        String select = " SELECT oid, identity, content, create_datetime FROM {{?tb_push_message_system}} WHERE cstatus&1=0 AND msg_status&2=0 AND identity=?";
        List<Object[]> lines = TomcatJDBCDAO.queryNoPage(select,new Object[]{identityName});
        if (lines.isEmpty()) return null;
        List<IPMessage> list = new ArrayList<>();
        for (Object[] rows : lines){
            long id = TomcatJDBCTool.convertObjectToSpecType(rows[0],0L);
            String identity = TomcatJDBCTool.convertObjectToSpecType(rows[1],"");
            String content = TomcatJDBCTool.convertObjectToSpecType(rows[2],"");
            String time = TomcatJDBCTool.convertObjectToSpecType(rows[3],"");
            list.add(IPMessage.create(id,identity,content,time));
        }
        return list;
    }

    @Override
    public String convertMessage(IPMessage message) {
        String content = message.getContent();
        if (content.startsWith("ON_CACHE:")){
            String msg = content.substring("ON_CACHE:".length());
            //System.out.println("发送非持久化消息: "+ message.getIdentityName()+">> "+ msg);
            return msg;
        }
        return content;
    }

    private void clearMessage() {
        //清理七天以上及 msg_status&2=0 的消息
        String update = "DELETE FROM {{?tb_push_message_system}} WHERE msg_status&2>0 OR  create_datetime<=DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        TomcatJDBCDAO.update(update);
    }


    private void updateSuccessMessageFlag(List<Object[]> params){
        if (params==null || params.size() == 0) return;

        final String updateSuccessSql = "UPDATE {{?tb_push_message_system}} SET msg_status = msg_status|2 WHERE oid = ?";
        int res = 0;
        TomcatJDBCDAO.update(updateSuccessSql,params);
        if (params.size()>0){
            int[] resArr = TomcatJDBCDAO.update(updateSuccessSql,params);
            for (int i : resArr ){
                if (i>0) res++;
            }
        }
       System.out.println(Log4j.sdf.format(new Date()) +" " + Thread.currentThread()+ " 批量修改已成功发送的标识> 待修改数: "+ params.size() +" ,成功数: "+ res+" , 队列剩余大小: "+ successMessageIdentityQueue.size());
    }

//        private volatile boolean kafkaIsRunning = true;
//    @Override
//    public void run() {
//        IMPToKafkaProperties kafkaConsumerToJava2Properties = new IMPToKafkaProperties();
//        String Message = "";
//        Log4j.info(">>> 进入Kafka方法: " + kafkaIsRunning);
//        Properties properties = new Properties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerToJava2Properties.getKafkaHost());
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerToJava2Properties.getGroupId());
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerToJava2Properties.getAutoOffsetReset());
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getKeyDecoder());
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getValueDecoder());
//
//        try {
//            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
//            consumer.subscribe(kafkaConsumerToJava2Properties.getTopic());
//            while (kafkaIsRunning) {
//                // consumer.poll参数类型为long: 0.11.0.0版本; 参数类型为Duration: 2.6.0版本
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//                for (ConsumerRecord<String, String> record : records) {
//                    Log4j.info(">>>>>>>>Consumer offset: " + record.offset() + " value: " + record.value());
//                    Message = record.value();
//                    Log4j.info(">>> Kafka方法");
//                }
//                Log4j.info(">>> 退出for循环: " + Message);
//                if (!Message.isEmpty()) {
//                    IMPRun();
//                    consumer.commitAsync();
//                }
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public void IMPRun() {
//        Log4j.info(">>> IM服务启动");
//        List<Object[]> params = new ArrayList<>();
//        int index = 0;
//        long last_ts = System.currentTimeMillis();
//        while (isRunning) {
//            try {
//                if (params.size() > 1000 || System.currentTimeMillis() - last_ts > 30 * 1000L) {
//                    updateSuccessMessageFlag(params);
//                    params.clear();
//                    last_ts = System.currentTimeMillis();
//                }
//                IMPToKafkaConsumer impToKafkaConsumer = new IMPToKafkaConsumer();
//                impToKafkaConsumer.start();
//                Log4j.info(">>> IM方法: ");
//                kafkaIsRunning = true;
////                String KafkaMessage = getDefaultKafkaConsumer(new IMPToKafkaProperties());
////                Log4j.info(">>> IM方法: "+KafkaMessage);
//                System.out.println(successMessageIdentityQueue.hashCode() + ">>> successMessageIdentityQueue size " + successMessageIdentityQueue.size());
//                Object[] param = successMessageIdentityQueue.poll();
//                if (param == null) {
//                    System.out.println(">>> 休眠");
//                    Thread.sleep(3000);
//                    continue;
//                }
//                params.add(param);
//                System.out.println("加入: " + Arrays.toString(param));
//            } catch (Exception e) {
//                Log4j.error("", e);
//            }
//            index++;
//            if (isRunning && index > 60 * 60 * 24) {
//                index = 0;
//                clearMessage();
//            }
//        }
//    }

    @Override
    public void run() {

        Log4j.info(">>> IM服务启动");
        List<Object[]> params = new ArrayList<>();
        int index = 0;
        long last_ts = System.currentTimeMillis();
        while (isRunning){
            try {
                if (params.size()> 1000 || System.currentTimeMillis() - last_ts > 30*1000L){
                    updateSuccessMessageFlag(params);
                    params.clear();
                    last_ts = System.currentTimeMillis();
                }
//                IMPToKafkaConsumer impToKafkaConsumer = new IMPToKafkaConsumer();
//                impToKafkaConsumer.start();
                Log4j.info(">>> IM方法: ");
                kafkaIsRunning = true;
                String KafkaMessage = getDefaultKafkaConsumer(new IMPToKafkaProperties());
                Log4j.info(">>> IM方法: "+KafkaMessage);

//                System.out.println(successMessageIdentityQueue.hashCode()+ ">>> successMessageIdentityQueue size "+ successMessageIdentityQueue.size());

                String[] strings = new String[]{KafkaMessage};
                successMessageIdentityQueue.offer(strings);
                Object[] param = successMessageIdentityQueue.poll();
                if (param == null){
                    System.out.println(">>> 休眠");
                    Thread.sleep(3000);
                    continue;
                }
                params.add(param);
                System.out.println("加入: "+ Arrays.toString(param));
            } catch (Exception e) {
                Log4j.error("",e);
            }
            index++;
            if (isRunning && index> 60*60*24) {
                index = 0;
                clearMessage();
            }
        }
    }

    private volatile boolean kafkaIsRunning = true;

    public String getDefaultKafkaConsumer(IMPToKafkaProperties kafkaConsumerToJava2Properties) {
        String Message = null;
        Log4j.info(">>> 进入Kafka方法: " + kafkaIsRunning);
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerToJava2Properties.getKafkaHost());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerToJava2Properties.getGroupId());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerToJava2Properties.getAutoOffsetReset());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getKeyDecoder());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerToJava2Properties.getValueDecoder());

        try {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(kafkaConsumerToJava2Properties.getTopic());
            while (kafkaIsRunning) {
                Message = null;
                // consumer.poll参数类型为long: 0.11.0.0版本; 参数类型为Duration: 2.6.0版本
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    Log4j.info(">>>>>>>>Consumer offset: " + record.offset() + " value: " + record.value());
                    Message = record.value();
                    if (!Message.isEmpty()) {
                        sendMessage(Message);
                    }
                    Log4j.info(">>> Kafka方法");
                }
                Log4j.info(">>> 退出for循环: " + Message);
//                if (!Message.isEmpty()) {
//                    kafkaIsRunning = false;
//                    consumer.commitAsync();
//                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Message;
    }

    public void sendMessage(String message) {
        Log4j.info("消息发送发放调用: " + message);
    }

    @Override
    public void initialization(String serverName, String groupName, Communicator communicator) {
        //初始化数据库
        TomcatJDBC.initializeCatchException("mysql-im.properties");
        this.setName("IM_STORAGE_T_"+ getId());
        this.start();
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        kafkaIsRunning = false;
        TomcatJDBC.destroy();
    }
}
