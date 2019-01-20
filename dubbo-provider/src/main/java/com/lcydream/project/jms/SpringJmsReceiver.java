package com.lcydream.project.jms;

import com.alibaba.fastjson.JSON;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * SpringJmsReceiver
 *
 * @author Luo Chun Yun
 * @date 2018/11/15 22:04
 */
public class SpringJmsReceiver {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:META-INF/spring/service-jms.xml");
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        Object msg = jmsTemplate.receiveAndConvert();
        System.out.println(JSON.toJSONString(msg));
    }
}
