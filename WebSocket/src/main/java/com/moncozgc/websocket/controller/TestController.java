package com.moncozgc.websocket.controller;

import com.moncozgc.websocket.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author JCccc
 * @Description
 * @Date 2021/8/20 8:53
 */
@Slf4j
@Controller
public class TestController {
    @Autowired
    public SimpMessagingTemplate template;

    /**
     * 广播
     *
     * @param msg 发送的信息
     */
    @ResponseBody
    @RequestMapping("/pushToAll")
    public void subscribe(@RequestBody Message msg) {
        log.info("广播消息进入方法, 发送的消息为: " + msg.getContent());
        template.convertAndSend("/topic/all", msg.getContent());
    }

    /**
     * 点对点发送消息
     *
     * @param msg 发送的信息
     */
    @ResponseBody
    @RequestMapping("/pushToOne")
    public void queue(@RequestBody Message msg) {
        log.info("点对点发送消息进入方法, 发送的消息为: " + msg.getContent());
        // convertAndSendToUser 源码中拼接了和前端一致的订阅规则
        template.convertAndSendToUser(msg.getTo(), "/message", msg.getContent());
    }
}