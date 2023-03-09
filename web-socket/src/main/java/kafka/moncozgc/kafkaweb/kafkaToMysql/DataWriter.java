package kafka.moncozgc.kafkaweb.kafkaToMysql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataWriter implements Runnable {
    public static BlockingQueue<String> tableDataQueue = new LinkedBlockingQueue<String>();

    public static void addCacheData(String str) {
        tableDataQueue.offer(str);
    }

    public DataWriter() {
        try {
            Class.forName(classdriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //    public final String url = "jdbc:mysql://127.0.0.1:3306/erp-im?characterEncoding=utf8&rewriteBatchedStatements=true";
//    public final String url = "jdbc:mysql://localhost:3306/erp-im?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    public final String url = "jdbc:mysql://192.168.153.140:3306/erp-im?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    public final String username = "root";
    public final String password = "hadoop";
    public final String classdriver = "com.mysql.cj.jdbc.Driver";
    /**
     * public final String url = "jdbc:oracle:thin:@xx.xx.xx.xx:xxxx/xxxx";
     * public final String username = "xxxxxxx";
     * public final String password = "xxxxxxx";
     * public final String classdriver = "oracle.jdbc.driver.OracleDriver";
     */
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;

    public void run() {
        while (true) {
            try {
                if (!tableDataQueue.isEmpty()) {
                    System.out.println("进入数据库方法 >>>");
                    List<String> cacheDataList = new ArrayList<>();
                    tableDataQueue.drainTo(cacheDataList, 2);
                    if (!cacheDataList.isEmpty()) {
                        insertBatch(cacheDataList);
                    }
                }
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 对数据库进行批量插入数据操作
     * 执行次数100万
     */
    public void insertBatch(List<String> cacheDataList) {
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < cacheDataList.size(); i++) {
                JSONObject obj = JSON.parseObject(cacheDataList.get(i));
                Integer id = obj.getInteger("id");
                String name = obj.getString("name");
                Date time1 = obj.getDate("create_datetime");

                String sql = "insert into kafka_test_dj(oid,identity,create_datetime) values (" + id + ",'" + name + "','" + sdf.format(time1) + "')";

                //利用addBatch方法将SQL语句加入到stmt对象中
                stmt.addBatch(sql);
                System.out.println("连接成功, 正在写入数据 >>>");
            }
            stmt.executeBatch();
            stmt.clearBatch();
            conn.commit();
            close(); //关闭资源
            System.out.println("数据写入完成 >>>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}