package com.moncozgc.mall.controller;

import com.moncozgc.mall.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 定时器Controller
 * Created by pengqi on 2022/12/5
 */
@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 定时器, 每分钟执行一次
     */
    public void taskEveryMinuteOfDay() {
        taskService.reportCurrentByCron();
    }
}
