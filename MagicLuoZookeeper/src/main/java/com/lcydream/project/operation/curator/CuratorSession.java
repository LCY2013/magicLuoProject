package com.lcydream.project.operation.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * CuratorSession
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 11:27
 */
public class CuratorSession {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";

    public static void main(String[] args) {
        //创建会话有两种方式 normal
        CuratorFramework curatorFramework =
                CuratorFrameworkFactory.newClient(CONNECTSTRING,
                        30000,
                30000,
                        new ExponentialBackoffRetry(
                                2000, 3));
        //start方法自动连接
        curatorFramework.start();

        //fluent风格(链式编程)
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString(CONNECTSTRING)
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .retryPolicy(
                        new ExponentialBackoffRetry(
                                2000, 3))
                .build();
        curator.start();

        System.out.println("连接成功");
    }

}
