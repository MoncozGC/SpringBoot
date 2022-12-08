package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.service.MailMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送邮件Controller
 *
 * Created by MoncozGC on 2022/12/6
 */
@Api(tags = "邮件接口")
@RestController
@RequestMapping("/mail")
public class MailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailMessageService mailMessageService;

    @ApiOperation("发送文本邮件")
    @PostMapping("sendMailMessageTest")
    public CommonResult<Object> sendMailMessageText() {
        return mailMessageService.sendSimpleMailMessageText() ? CommonResult.success("文本邮件发送成功") : CommonResult.failed("文本邮件发送失败");
    }

    @ApiOperation("发送HTML邮件")
    @PostMapping("sendMailMessageHtml")
    public CommonResult<Object> sendMailMessageHtml() {
        return mailMessageService.sendSimpleMailMessageHtml() ? CommonResult.success("HTML邮件发送成功") : CommonResult.failed("HTML邮件发送失败");
    }

}
