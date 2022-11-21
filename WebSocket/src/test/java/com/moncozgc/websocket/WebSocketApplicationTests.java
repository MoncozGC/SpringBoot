package com.moncozgc.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest
class WebSocketApplicationTests {

    @Test
    void contextLoads() {
        AtomicLong ids = new AtomicLong(30);
        String id = Long.toHexString(ids.getAndIncrement());

        System.out.println(Long.toHexString(30L));
        System.out.println(id);


        System.out.println(Long.toHexString(ids.getAndIncrement()));
        System.out.println(Long.toHexString(ids.addAndGet(0)));
    }

}
