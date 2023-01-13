package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.common.utils.MemoryDayUtil;
import com.moncozgc.mall.config.PushConfigure;
import com.moncozgc.mall.service.WeChatPushService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

/**
 * Created by pengqi on 2023/1/10
 */
@Service
@Slf4j
public class WeChatPushServiceImpl implements WeChatPushService {
    @Override
    public CommonResult<String> templateWXPush() {
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(PushConfigure.getAppId());
        wxStorage.setSecret(PushConfigure.getSecret());
        wxStorage.setAccessToken(PushConfigure.getToken());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(PushConfigure.getUserId())
                .templateId(PushConfigure.getTemplateId())
                .build();
        // 配置你的信息
        long loveDays = MemoryDayUtil.calculationLianAi(PushConfigure.getLoveDate());
        long birthdays = MemoryDayUtil.calculationBirthday(PushConfigure.getBirthday());

        templateMessage.addData(new WxMpTemplateData("loveDays", loveDays + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("birthdays", birthdays + "", "#FFA500"));

        String remark = "测试微信公众号消息发送中 =^_^= ";
        if (loveDays % 365 == 0) {
            remark = "\n今天是恋爱" + (loveDays / 365) + "周年纪念日!";
        }
        if (birthdays == 0) {
            remark = "\n今天是生日,生日快乐呀!";
        }
        if (loveDays % 365 == 0 && birthdays == 0) {
            remark = "\n今天是生日,也是恋爱" + (loveDays / 365) + "周年纪念日!";
        }

        templateMessage.addData(new WxMpTemplateData("remark", remark, "#FF1493"));
//        templateMessage.addData(new WxMpTemplateData("rainbow", RainbowUtil.getRainbow(), "#FF69B4"));
        log.info(templateMessage.toJson());
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(templateMessage.toJson(), "推送成功!");
    }
}
