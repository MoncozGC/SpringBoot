package com.moncozgc.employ.controlle;

import com.moncozgc.employ.service.ThreadTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MoncozGC on 2022/11/21
 */
@RestController
public class ThreadTestController {

    @Autowired
    private ThreadTestService threadTestService;

    @RequestMapping("ThreadTest")
    public Object ThreadTest() {
        for (int i = 0; i < 100; i++) {
            threadTestService.executeThread(i);
        }
        return "ok";
    }
}
