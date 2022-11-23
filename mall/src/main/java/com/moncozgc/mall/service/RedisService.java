package com.moncozgc.mall.service;

import java.util.concurrent.TimeUnit;

/**
 * redis基本操作
 * 对象和数组都以json形式进行存储
 * Created by MoncozGC on 2022/11/22
 */
public interface RedisService {
    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 获取数据
     *
     * @param key 键
     */
    String get(String key);

    /**
     * 设置过期时间
     *
     * @param key    键
     * @param expire 过期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 存在返回为false(对原生hasKey进行取反操作)
     */
    boolean hasKey(String key);

    /**
     * 自增长
     *
     * @param key   键
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

    /**
     * 返回key的过期时间
     *
     * @param key      键
     * @param timeUnit 想要展示的时间单位
     * @return key的过期时间
     */
    Long getExpire(String key, TimeUnit timeUnit);

}
