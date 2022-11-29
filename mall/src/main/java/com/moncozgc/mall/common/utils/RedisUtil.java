package com.moncozgc.mall.common.utils;

import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Properties;

/**
 * @author wangzai
 * @version 1.0
 * @date 2022/2/16 11:13
 */
public class RedisUtil {
    public static JedisPool jedisPool = null;

    public static Jedis getJedisClient(Properties properties) {

        if (jedisPool == null) {

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal"))); //最大可用连接数
            jedisPoolConfig.setBlockWhenExhausted(Boolean.getBoolean(properties.getProperty("whenExhausted"))); //连接耗尽是否等待
            jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(properties.getProperty("maxWaitMillis"))); //等待时间
            jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle"))); //最大闲置连接数
            jedisPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty("minIdle"))); //最小闲置连接数
            jedisPoolConfig.setTestOnBorrow(true); //取连接的时候进行一下测试 ping pong

            try {
                jedisPool = new JedisPool(
                        jedisPoolConfig,
                        properties.getProperty("redis_host"),
                        Integer.parseInt(properties.getProperty("redis_port")),
                        Integer.parseInt(properties.getProperty("redis_timeout")),
                        properties.getProperty("redis_password")
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("连接redis失败！");
            }

            System.out.println("开辟连接池");
            return jedisPool.getResource();

        } else {
            return jedisPool.getResource();
        }
    }

    // 从redis插入key
    public static void insertJedis(Properties properties, String key, String val) {
        Jedis jedis = null;
        try {
            jedis = getJedisClient(properties);
            jedis.select(7);
            jedis.set(key, val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭Jedis
            if (jedis != null)
                jedis.close();
        }
    }

    // 从redis获取key
    public static String getJedis(Properties properties, String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedisClient(properties);
//            jedis.select(selectDB);
            res = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭Jedis
            if (jedis != null)
                jedis.close();
        }
        return res;
    }

    @SneakyThrows
    public static void main(String[] args) {
//        Properties properties = PropertiesUtil.loadProperties("task.properties");
//        String erp_ck_path_key = properties.getProperty("erp_ck_path_key");
//        String checkPointPath = RedisUtil.getJedis(properties, erp_ck_path_key);
//        System.out.println(checkPointPath);
        System.out.println(Duration.ofMillis(1000));
    }

}
