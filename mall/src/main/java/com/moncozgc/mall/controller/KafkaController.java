package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.component.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Kafka Controller
 * Created by MoncozGC on 2022/11/22
 */
@Controller
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult sendMessag(@RequestParam String message) {
        kafkaProducer.send(message);
        return CommonResult.success(message, "发送成功");
    }
}
