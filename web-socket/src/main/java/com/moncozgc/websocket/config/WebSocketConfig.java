package com.moncozgc.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * @Author JCccc
 * @Description EnableWebSocketMessageBroker-注解开启STOMP协议来传输基于代理的消息，此时控制器支持使用@MessageMapping
 * @Date 2021/6/30 8:53
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置消息代理
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //topic用来广播，user用来实现点对点
        // 以主题为前缀的目的地(destinations)
        config.enableSimpleBroker("/topic", "/user");
    }

    /**
     * 开放节点
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册两个STOMP的endpoint，分别用于广播和点对点

        // 广播发送消息
        // setAllowedOriginPatterns: 允许跨域
        // setAllowedOrigins: 不允许跨域
        registry.addEndpoint("/publicServer").setAllowedOriginPatterns("*").withSockJS();

        // 点对点发送消息
        registry.addEndpoint("/privateServer").setAllowedOriginPatterns("*").withSockJS();
    }


}
