package com.lcydream.project.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * JmsTopicPersistenteReceiver
 *
 * @author Luo Chun Yun
 * @date 2018/11/14 22:01
 */
public class JmsTopicPersistenteReceiver {

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
            //设置持久订阅，这里设置了之后就可以在这个消费者注册后消费以前的消息，但是没注册前的还是消费不了
            connection.setClientID("magic-topic");
            //建立连接
            connection.start();
            //创建会话
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //创建主题(这里的主题如果存在就不会创建，参数是主题的名字)
            Topic topic = session.createTopic("magic-topic");
            //创建消息接受者
            MessageConsumer consumer = session.createDurableSubscriber(topic,"magic-topic");
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
