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

    /**
     * 根据模板文件发送验证码邮件
     */
    boolean sendMailMessageAuthCode(String phone, String code);
}
