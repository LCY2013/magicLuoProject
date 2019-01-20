package com.lcydream.project.jms.listener;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.request.UserRegisterRequest;
import com.lcydream.project.services.mail.MailService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * RegisterQueueMessageListener
 *  监听消息处理
 * @author Luo Chun Yun
 * @date 2018/11/19 15:17
 */
public class RegisterQueueMessageListener implements MessageListener {

    /**
     * 日志组件
     */
    private static Logger logger = Logger.getLogger(RegisterQueueMessageListener.class);

    @Autowired
    private MailService mailService;

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage)message;
            try {
                //获取用户注册的信息
                String messageText = textMessage.getText();
                //用户注册信息不为空
                if(StringUtils.isNotBlank(messageText)){
                    UserRegisterRequest userRegisterRequest =
                            JSON.parseObject(messageText, UserRegisterRequest.class);
                    mailService.send("magicProject","welcome to register magic Shopping Mall",userRegisterRequest.getUsername());
                    logger.info("发送注册成功邮件到"+userRegisterRequest.getUsername()+"成功!");
                }else {
                    UserRegisterRequest userRegisterRequest =
                            JSON.parseObject(messageText, UserRegisterRequest.class);
                    mailService.send("magicProject","welcome to register magic Shopping Mall","1475653689@qq.com");
                    logger.info("发送注册成功邮件到1475653689@qq.com成功!");
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
