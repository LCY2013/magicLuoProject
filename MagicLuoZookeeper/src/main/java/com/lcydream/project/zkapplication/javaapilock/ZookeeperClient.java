package com.lcydream.project.zkapplication.javaapilock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * ZookeeperClient
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 20:36
 */
public class ZookeeperClient {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";

    private static int sessionTimeout = 30000;

    public static ZooKeeper getInstance(){
        final CountDownLatch countDownLatch =
                new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(CONNECTSTRING, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(event.getState() == Event.KeeperState.SyncConnected){
                        countDownLatch.countDown();
                        System.out.println("zookeeper connected success...");
                    }
                }
            });
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }

    public static int getSessionTimeout() {
        return sessionTimeout;
    }
}
