package com.lcydream.project.jms;

import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * JmsReceiver
 *  activeMq p2p模型
 * @author Luo Chun Yun
 * @date 2018/11/13 21:06
 */
public class JmsReceiver {

    private static Logger logger = Logger.getLogger(JmsReceiver.class);

    public static void main(String[] args) {
        //获取中间件的工厂
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("failover:(tcp://192.168.21.160:61616," +
                        "tcp://192.168.21.163:61616,tcp://192.168.21.164:61616)?randomize=false");
        //获取连接
        Connection connection = null;
        try {
            //创建连接
            connection =
                    connectionFactory.createConnection();
            //建立连接
            connection.start();
            //创建会话
            //Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
            //创建队列(这里的队列如果存在就不会创建，参数是队列的名字)
            Destination destination = session.createQueue("magic");
            /*//创建消息接受者
            MessageConsumer consumer = session.createConsumer(destination);
            //阻塞接受信息
            TextMessage textMessage = (TextMessage)consumer.receive();
            logger.info(JSONObject.toJSONString(textMessage));
            logger.info(textMessage.getStringProperty("lover"));*/
            //创建消息接受者
            MessageConsumer consumer = session.createConsumer(destination);
            for (int i=0;i<10;i++){
                //阻塞接受信息
                TextMessage textMessage = (TextMessage)consumer.receive();
                logger.info(textMessage.getText());
                //if(i==4){
                    textMessage.acknowledge();
                //}
            }
            //session.commit();
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
