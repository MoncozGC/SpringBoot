package com.moncozgc.employ.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置类
 *
 * @EnableAsync: 开启异步任务
 * Created by MoncozGC on 2022/11/21
 */
@Configuration()
@EnableAsync
public class ThreadTestConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor tp = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        tp.setCorePoolSize(10);
        // 设置最大线程数
        tp.setMaxPoolSize(100);
        // 线程使用的缓冲队列
        tp.setQueueCapacity(10);
        // 设置程序关闭时要等待线程全部执行完
        tp.setWaitForTasksToCompleteOnShutdown(true);
        // 设置等待时间, 超过等待时间后立即停止
        tp.setAwaitTerminationSeconds(60);
        // 线程名称前缀
        tp.setThreadNamePrefix("Test-Async-");
        // 初始化线程
        tp.initialize();
        return tp;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
