package ice.moncozgc.ice;

import ice.moncozgc.ice.log.Log4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by MoncozGC on 2022/11/25
 */
public class IPMessage {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long id;
    String identityName;
    String content;
    String time;

    private IPMessage() {
    }

    static IPMessage create(String identityName, String message) {
        IPMessage msg = new IPMessage();
        msg.id = 0;
        msg.identityName = identityName;
        msg.content = message;
        msg.time = simpleDateFormat.format(new Date());
        return msg;
    }

    public static IPMessage create(long id, String identityName, String message, String time) {
        IPMessage msg = new IPMessage();
        msg.id = id;
        msg.identityName = identityName;
        msg.content = message;
        msg.time = time;
        return msg;
    }

    public long getId() {
        return id;
    }

    public String getIdentityName() {
        return identityName;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

//    @Override
//    public String toString() {
//        return GoogleGsonUtil.javaBeanToJson(this);
//    }

    public static void main(String[] args) {
        IPMessage ipMessage = new IPMessage();
        ipMessage.id = 1;
        ipMessage.content = "测试文本";
        ipMessage.identityName = "identity";
        List<IPMessage> list = new ArrayList<>();

        IPMessage ipMessage2 = new IPMessage();
        ipMessage2.id = 2;
        ipMessage2.content = "测试文本2";
        ipMessage2.identityName = "identity2";

        list.add(ipMessage);
        list.add(ipMessage2);
        //待发送消息存储的队列
        LinkedBlockingQueue<IPMessage> messageQueue = new LinkedBlockingQueue<>();
        boolean offer = messageQueue.offer(ipMessage);
        messageQueue.offer(ipMessage2);
        System.out.println(messageQueue.size());
        for (IPMessage message : messageQueue) {
            System.out.println(message);
        }
        System.out.println(offer);

        Log4j.info(Integer.MAX_VALUE);
    }
}
