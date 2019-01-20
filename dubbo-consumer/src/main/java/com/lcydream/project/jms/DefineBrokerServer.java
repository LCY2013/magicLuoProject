package com.lcydream.project.jms;

import org.apache.activemq.broker.BrokerService;

/**
 * DefineBrokerServer
 * 启动一个broker服务
 * @author Luo Chun Yun
 * @date 2018/11/14 22:05
 */
public class DefineBrokerServer {

    public static void main(String[] args) {
        BrokerService brokerService =
                new BrokerService();
        brokerService.setUseJmx(true);
        try {
            brokerService.addConnector("tcp://127.0.0.1:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
