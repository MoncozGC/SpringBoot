package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.component.KafkaProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Kafka Controller
 * Created by MoncozGC on 2022/11/22
 */
@Api(tags = "Kafka生产者")
@Controller
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @ApiOperation(value = "Kafka生产者")
    @ResponseBody
    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public CommonResult<String> sendMessage(@ApiParam(name = "message", value = "发送的消息")
                                            @RequestParam String message) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        kafkaProducer.send("消息发送: " + format.format(date));
        return CommonResult.success(message, format.format(date));
    }
}
