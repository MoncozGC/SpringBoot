package drug.erp.handle;

import framework.server.IPMessage;
import jdbc.imp.TomcatJDBC;
import jdbc.imp.TomcatJDBCDAO;
import jdbc.imp.TomcatJDBCTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by MoncozGC on 2022/11/28
 */
public class IMPTest {
    // 批量修改列表
    private static final LinkedBlockingQueue<Object[]> successMessageIdentityQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        TomcatJDBC.initializeCatchException("mysql-im.properties");
        // kafka消费者数据poll
        IMPToKafkaConsumer impToKafkaConsumer = new IMPToKafkaConsumer();
        impToKafkaConsumer.start();
        impToKafkaConsumer.join();
        System.out.println(impToKafkaConsumer.value);
        // IM消息数据发送
        IPersistentMessageImp iPersistentMessageImp = new IPersistentMessageImp();
        String[] message = new String[]{"ceshi"};
        successMessageIdentityQueue.add(message);
        iPersistentMessageImp.start();

        //查询数据库是否存在等待发送的消息
//        String select = " SELECT oid, identity, content, create_datetime FROM {{?tb_push_message_system}} WHERE cstatus&1=0 AND msg_status&2=0 AND identity=?";
//        List<Object[]> lines = TomcatJDBCDAO.queryNoPage(select, new Object[]{"1577798539000001002"});
//        List<IPMessage> list = new ArrayList<>();
//        for (Object[] rows : lines) {
//            long id = TomcatJDBCTool.convertObjectToSpecType(rows[0], 0L);
//            String identity = TomcatJDBCTool.convertObjectToSpecType(rows[1], "");
//            String content = TomcatJDBCTool.convertObjectToSpecType(rows[2], "");
//            String time = TomcatJDBCTool.convertObjectToSpecType(rows[3], "");
//            list.add(IPMessage.create(id, identity, content, time));
//        }
//        for (IPMessage ipMessage : list) {
//            System.out.println(ipMessage);
//        }
    }
}
