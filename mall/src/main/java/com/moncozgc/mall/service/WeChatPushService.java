package com.moncozgc.mall.service;

import com.moncozgc.mall.common.CommonResult;

/**
 * Created by pengqi on 2023/1/10
 */
public interface WeChatPushService {

    /**
     * 使用模板发送消息至微信公众号
     *
     * @return
     */
    CommonResult<String> templateWXPush();
}
