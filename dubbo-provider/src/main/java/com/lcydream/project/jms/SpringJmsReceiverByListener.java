package com.lcydream.project.jms;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpringJmsReceiverByListener
 *
 * @author Luo Chun Yun
 * @date 2018/11/15 22:17
 */
public class SpringJmsReceiverByListener {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:META-INF/spring/service-jms.xml");
        try {
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
