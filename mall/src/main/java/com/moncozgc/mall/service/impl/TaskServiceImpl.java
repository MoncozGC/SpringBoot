package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.service.PythonService;
import com.moncozgc.mall.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PythonService pythonService;

    @Scheduled(cron = "0 0 18 * * * ")
    @Override
    public void reportCurrentByCron() {
        pythonService.PythonToIntegrate("SERVER", "weather.py");
        LOGGER.info("定时器每分钟执行一次: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
