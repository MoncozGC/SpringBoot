package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.controller.MailController;
import com.moncozgc.mall.service.MailMessageService;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by MoncozGC on 2022/12/6
 */
@Service
public class MailMessageServiceImpl implements MailMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailController.class);

    // 发送者邮箱
    private final String SENDER_EMAIL = "2869488716@qq.com";

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public boolean sendSimpleMailMessageText() {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);

            // 发送邮件的邮箱地址
            mimeMessageHelper.setFrom(SENDER_EMAIL);
            // 回复发送至邮箱地址
//            message.setReplyTo(SENDER_EMAIL);
            // 邮件接受的邮箱地址, 【可以设置多个, 多个时采取数组】
            String[] mail = {"183966516@qq.com"};
            mimeMessageHelper.setTo(mail);
            // 抄送的邮箱地址 【可以设置多个, 多个时采取数组】
            mimeMessageHelper.setCc(SENDER_EMAIL);
            // 邮件的日期
            mimeMessageHelper.setSentDate(new Date());
            // 邮件的主题
            mimeMessageHelper.setSubject("SpringBoot测试邮件发送");
            // 邮件的标题, 第二个参数为true则以html发送邮件
            mimeMessageHelper.setText("邮件测试发送");

            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            LOGGER.info("文本邮件发送失败");
            return false;
        }
        LOGGER.info("文本邮件发送成功");
        return true;
    }

    @Override
    public boolean sendSimpleMailMessageHtml() {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);

            // 发送邮件的邮箱地址
            mimeMessageHelper.setFrom(SENDER_EMAIL);
            // 抄送的邮箱地址 【可以设置多个, 多个时采取数组】
            mimeMessageHelper.setCc(SENDER_EMAIL);
            // 邮件接受的邮箱地址, 【可以设置多个, 多个时采取数组】
            String[] mail = {"183966516@qq.com"};
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setSubject("邮件主题");
            mimeMessageHelper.setSentDate(new Date());

            HashMap<String, Object> mailMap = new HashMap<>();
            mailMap.put("title", "HTML邮件主题");
            mailMap.put("content", "HTML邮件正文");
            // mail.ftl中有两个变量, 也就是map需要去替换掉的值
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("authenticationMail.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailMap);
            mimeMessageHelper.setText(text, true);

            // 添加图片附件
            String url = "https://www.baidu.com/img/flexible/logo/pc/index.png";
            URL imgUrl = new URL(url);
            mimeMessageHelper.addAttachment("img.jpg", imgUrl::openStream);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("HTML邮件发送失败");
            return false;
        }
        LOGGER.info("HTML邮件发送成功");
        return true;
    }

}
