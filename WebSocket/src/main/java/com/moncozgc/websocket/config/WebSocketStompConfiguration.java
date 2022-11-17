package com.moncozgc.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.util.List;

/**
 * 为WebSocket客户端定义简单的传输协议配置方法，通常需要配合使用@EnableWebSocketMessageBroker注解开启相应功能
 *
 * Created by MoncozGC on 2022/11/17
 */
@EnableWebSocketMessageBroker
public class WebSocketStompConfiguration implements WebSocketMessageBrokerConfigurer {
    /**
     * 有关处理来自客户端的消息和发送到客户端的消息的配置选项
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {

    }

    /**
     * 注册STOMP端点(可以是多个)，并将端点映射到特定的URL上，并（可选）启用和配置SockJS回退选项。
     * 这里的配置主要用于构建WebSocketHandlerMapping
     *
     * <pre>
     * 这里的配置项有两种类型：
     * 1、{@code StompEndpointRegistry} 接口定义的配置
     * 2、{@code StompWebSocketEndpointRegistration} 接口定义的配置
     * 3、{@code SockJsServiceRegistration} 接口定义的配置
     * </pre>
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * 从WebMvcStompEndpointRegistry.getHandlerMapping()方法中可以看出，
         * WebSocketHandlerMapping优先使用我们这里配置的UrlPathHelper，如果我们这里没有配置这个值，
         * WebSocketHandlerMapping会使用在其父类AbstractHandlerMapping中创建的UrlPathHelper。
         */
        registry.setUrlPathHelper(null);
        /*
         * 设置STOMP所使用的HandlerMapping的优先级，默认值为1
         */
        registry.setOrder(1);
        /*
         * 自定义一个处理客户端错误帧的处理器，
         * 这里的StompSubProtocolErrorHandler会被设置到WebMvcStompEndpointRegistry类持有的StompSubProtocolHandler对象中
         */
//        registry.setErrorHandler(stompSubProtocolErrorHandler());
        /*
         * websocket的端点，客户端需要注册这个端点进行链接
         */
        StompWebSocketEndpointRegistration stompWebSocketEndpointRegistration = registry.addEndpoint("/stomp/ws");

        /*
         * 配置允许的浏览器Origin Header的值，这个配置主要是针对浏览器设计的。HTTP 协议中的 Origin Header
         * 存在于请求中，用于指明当前请求来自于哪个站点。默认空表示只支持同源请求，“*”表示支持所有站点请求，其他以“http://”、“https://”
         * 表示支持特定站点的请求。
         */
        stompWebSocketEndpointRegistration.setAllowedOrigins("*");
        /*
         * 没有设置时默认使用DefaultHandshakeHandler
         */
        stompWebSocketEndpointRegistration.setHandshakeHandler(null);
        /*
         * OriginHandshakeInterceptor为默认拦截器，用于验证
         * OriginHeader,这里的配置只是多添加一个拦截器，不会覆盖掉默认拦截器。
         */
//        stompWebSocketEndpointRegistration.addInterceptors(userHandshakeInterceptor());
        /*
         * SockJsServiceRegistration为返回值，可以配置SockJS
         */
        SockJsServiceRegistration sockJsServiceRegistration = stompWebSocketEndpointRegistration.withSockJS();
        /*
         *
         */
        sockJsServiceRegistration.setHttpMessageCacheSize(100);

    }

//    @Bean
//    public HandshakeInterceptor userHandshakeInterceptor() {
//        return new UserHandshakeInterceptor();
//    }

    @Bean
    public StompSubProtocolErrorHandler stompSubProtocolErrorHandler() {
        return new StompSubProtocolErrorHandler();
    }

    /**
     * 消息中介的相关配置
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 客户端订阅消息的基础路径
        config.setApplicationDestinationPrefixes("/app");
        // 服务器广播消息的基础路径
        config.enableSimpleBroker("/topic");
    }

    /**
     * 配置消息转换器，以便在从带注解的方法上提取消息和发送消息时使用。返回的boolean值类型用于确定是否还要添加默认转换器。
     */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return true;
    }

    /**
     * 客户端消息传入通道的相关配置。默认情况下，通道由大小为1的线程池支持。建议生产环境设置合适的自定义线程池配置。
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
    }

    /**
     * 客户端消息传出通道的相关配置。默认情况下，通道由大小为1的线程池支持。建议生产环境设置合适的自定义线程池配置。
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
    }

    /**
     * 添加解析器以支持自定义控制器方法参数类型。不会修改内置方法参数解析器，如果需要修改内置方法参数解析器，直接配置SimpAnnotationMethodMessageHandler
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        WebSocketMessageBrokerConfigurer.super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 添加处理程序以支持自定义控制器方法返回值类型。内置处理器的修改直接配置SimpAnnotationMethodMessageHandler
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        WebSocketMessageBrokerConfigurer.super.addReturnValueHandlers(returnValueHandlers);
    }

}

