package com.lcydream.project.jms;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;

/**
 * SpringJmsSender
 *
 * @author Luo Chun Yun
 * @date 2018/11/15 22:10
 */
public class SpringJmsSender {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:service-jms.xml");
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        jmsTemplate.send((session) -> {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("magic love");
            return textMessage;
        });
    }

}
