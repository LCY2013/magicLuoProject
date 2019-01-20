package com.lcydream.project.jms;

import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * JmsSender
 *  activeMq p2p模型下的发送端
 * @author Luo Chun Yun
 * @date 2018/11/13 21:07
 */
public class JmsSender {

    private static Logger logger = Logger.getLogger(JmsSender.class);

    public static void main(String[] args) {
        //获取中间件的工厂
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("failover:(tcp://192.168.21.160:61616,"+
                        "tcp://192.168.21.163:61616,tcp://192.168.21.164:61616)");
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
            //创建消息接受者
            MessageProducer producer = session.createProducer(destination);
            /*//阻塞接受信息
            TextMessage textMessage = session.createTextMessage("hello magic");
            //设置文本消息的属性
            textMessage.setStringProperty("lover","yyr");
            producer.send(textMessage);*/
            for(int i=0;i<10;i++){
                //阻塞接受信息
                TextMessage textMessage = session.createTextMessage("hello magic"+(i+1));
                producer.send(textMessage);
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
