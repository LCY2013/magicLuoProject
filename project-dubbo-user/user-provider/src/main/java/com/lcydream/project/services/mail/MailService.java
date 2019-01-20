package com.lcydream.project.services.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MailService
 *
 * @author Luo Chun Yun
 * @date 2018/11/19 15:59
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     *
     * @Description: 从模板中构建邮件内容
     * @param nickName        用户昵称
     * @param content        邮件内容
     * @param email            接受邮件
     */
    public void send(String nickName, String content, String email) {
        String to = email;
        String text = nickName + ": welcome to this";
        Map<String, String> map = new HashMap<>(1);
        map.put("nickName", nickName);
        map.put("content", content);
        try {
            // 根据模板内容，动态把map中的数据填充进去，生成HTML
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.ftl");
            // map中的key，对应模板中的${key}表达式
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        sendMail(to, null, text);
    }

    /**
     *
     * @Description: 执行发送邮件
     * @param to            收件人邮箱
     * @param subject        邮件主题
     * @param content        邮件内容
     */
    private void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setRecipients(Message.RecipientType.CC,simpleMailMessage.getFrom());
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(simpleMailMessage.getFrom());
            if (subject != null) {
                messageHelper.setSubject(subject);
            } else {
                messageHelper.setSubject(simpleMailMessage.getSubject());
            }
            messageHelper.setTo(to);
            messageHelper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
