package com.moncozgc.employ.service.impl;

import com.moncozgc.employ.service.ThreadTestService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 多线程, 实现Service
 * Created by MoncozGC on 2022/11/21
 */
@Service
public class ThreadTestServiceImpl implements ThreadTestService {

    @Override
    @Async
    public void executeThread(int i) {
        System.out.println("线程" + Thread.currentThread().getName() + "执行异步任务" + i);
    }
}
