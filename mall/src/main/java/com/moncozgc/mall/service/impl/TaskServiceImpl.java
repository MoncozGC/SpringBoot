package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时器实现类
 * 注: @Scheduled注解方法不能接受任何参数或返回任何内容
 * Created by MoncozGC on 2022/12/5
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Scheduled(cron = "0 1 * * * * ")
    @Override
    public void reportCurrentByCron() {
        LOGGER.info("定时器每分钟执行一次: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
