package com.lcydream.project.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * JmsListener
 *
 * @author Luo Chun Yun
 * @date 2018/11/15 22:14
 */
public class JmsListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
