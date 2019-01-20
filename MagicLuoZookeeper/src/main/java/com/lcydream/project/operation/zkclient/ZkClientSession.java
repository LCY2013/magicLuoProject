package com.lcydream.project.operation.zkclient;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.ZkClient;

/**
 * ZkClientSession
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 10:55
 */
public class ZkClientSession {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(CONNECTSTRING, 30000);
        System.out.println("连接成功:"+ JSON.toJSONString(zkClient));
    }

}
