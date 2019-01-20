package com.lcydream.project.operation.javaapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * CreateZkSession
 *
 * @author Luo Chun Yun
 * @date 2018/8/31 21:32
 */
public class CreateZkSession {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //如果当前的连接状态是连接成功，就利用计数器去控制
                if(watchedEvent.getState()==Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                    System.out.println(watchedEvent.getState());
                }
            }
        });
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
    }

}
