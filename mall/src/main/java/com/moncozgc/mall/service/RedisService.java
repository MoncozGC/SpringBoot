package com.moncozgc.mall.service;

/**
 * redis基本操作
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
     * 自增长
     *
     * @param key   键
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

}
