package com.lcydream.project.operation.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CuratorClientUtils
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 14:54
 */
public class CuratorClientUtils {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";

    private static Logger logger = LoggerFactory.getLogger(CuratorClientUtils.class);

    public static CuratorFramework getInstance(){
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
        logger.info("<--curator get instance success-->");
        return curator;
    }
}
