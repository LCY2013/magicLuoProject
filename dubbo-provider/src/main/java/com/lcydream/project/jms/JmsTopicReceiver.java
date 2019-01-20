package com.lcydream.project.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * JmsTopicReceiver
 *
 * @author Luo Chun Yun
 * @date 2018/11/14 21:56
 */
public class JmsTopicReceiver {

    private static Logger logger = Logger.getLogger(JmsReceiver.class);

    public static void main(String[] args) {
        //获取中间件的工厂
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://192.168.21.160:61616");
        //获取连接
        Connection connection = null;
        try {
            //创建连接
            connection =
                    connectionFactory.createConnection();
            //建立连接
            connection.start();
            //创建会话
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //创建队列(这里的队列如果存在就不会创建，参数是队列的名字)
            Destination destination = session.createTopic("magic-topic");
            //创建消息接受者
            MessageConsumer consumer = session.createConsumer(destination);
            //阻塞接受信息
            TextMessage textMessage = (TextMessage)consumer.receive();
            logger.info(textMessage.getText());
            session.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭中间件连接
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
