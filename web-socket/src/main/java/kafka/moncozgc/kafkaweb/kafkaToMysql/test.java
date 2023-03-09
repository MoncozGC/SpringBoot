package kafka.moncozgc.kafkaweb.kafkaToMysql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by MoncozGC on 2022/11/29
 */
public class test {
    public static void main(String[] args) {
//        String value = "{\"id\": \"1001\",\"name\": \"数据测试\",\"create_datatime\": \"2020-11-29 15:12:21\"}";
        String value = "{\"data\":[ {\"id\": \"1001\",\"name\": \"数据测试\",\"create_datatime\": \"2020-11-29 15:12:21\"}]}";
//        String value = "{\"list\":[  {\"amount\":0,\"soinsStatus\":\"\",\"viewStateText\":0,\"edit\":true,\"mobile\":\"13234444555\",\"channelType\":\"\",\"creditStatus\":\"\",\"fundsStatus\":\"\",\"idNo\":\"\",\"auditTime\":\"\",\"createTime\":\"2019-08-13 17:01:55\",\"stateText\":\"\",\"name\":\"客户姓名\",\"id\":372,\"lendRequestId\":0,\"state\":0}  ]  }";
        JSONObject obj = JSON.parseObject(value);
        System.out.println(obj.getJSONArray("data"));

        System.out.println(obj.getString("String"));
        System.out.println("INSERT".equalsIgnoreCase(new String("RUN")));

        if ("INSERT".equalsIgnoreCase(obj.getString("type"))
                && "test".equalsIgnoreCase(obj.getString("database"))
                && "kafka_test_dj".equalsIgnoreCase(obj.getString("table"))) {
            System.out.println("1");
        }

        String value1 = "{\"name\":\"数据测试\",\"create_datatime\":\"2013-11-29 15:12:21\",\"id\":\"1001\"}";
        BlockingQueue<String> tableDataQueue = new LinkedBlockingQueue<String>();
        tableDataQueue.offer(value1);
        List<String> cacheDataList = new ArrayList<>();
        tableDataQueue.drainTo(cacheDataList);
        for (String s : cacheDataList) {
            System.out.println(s);
        }
    }
}
