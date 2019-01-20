package com.lcydream.project.zkapplication.javaapilock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * LockWacher
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 21:13
 */
public class LockWacher implements Watcher {

    private CountDownLatch countDownLatch;

    public LockWacher(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getType() == Event.EventType.NodeDeleted){
            countDownLatch.countDown();
        }
    }

}
