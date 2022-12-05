package com.moncozgc.mall.service;

/**
 * 定时器任务
 * Created by MoncozGC on 2022/12/5
 */
public interface TaskService {

    /**
     * 定时器每分钟执行一次
     */
    void reportCurrentByCron();
}
