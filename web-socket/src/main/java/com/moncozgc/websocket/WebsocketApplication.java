package com.moncozgc.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://blog.csdn.net/qq_35387940/article/details/119817167 Springboot 整合 WebSocket ，使用STOMP协议 ，前后端整合实战
 */
@SpringBootApplication(scanBasePackages = {"com.moncozgc.websocket"})
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

}
