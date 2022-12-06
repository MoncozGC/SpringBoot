package com.moncozgc.mall.service;

/**
 * Created by MoncozGC on 2022/12/6
 */
public interface MailMessageService {

    /**
     * 发送简单文本格式邮件信息
     */
    boolean sendSimpleMailMessageText();

    /**
     * 发送html模板格式邮件信息
     */
    boolean sendSimpleMailMessageHtml();
}
