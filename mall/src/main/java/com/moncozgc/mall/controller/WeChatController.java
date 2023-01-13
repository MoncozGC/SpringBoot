package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.CommonResult;
import com.moncozgc.mall.service.WeChatPushService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pengqi on 2023/1/10
 */
@RestController("wx")
public class WeChatController {
    @Autowired
    WeChatPushService weChatPushService;

    /**
     * 测试公众号通道
     */
    @ApiOperation("微信公众号消息推送-通道测试")
    @RequestMapping(value = "wxTest", method = RequestMethod.GET)
    public CommonResult<String> templateWXPush() {
        return weChatPushService.templateWXPush();
    }
}
