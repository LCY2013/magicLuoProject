package com.lcydream.project.zkapplication.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * MasterSelector
 *
 * @author Luo Chun Yun
 * @date 2018/9/4 21:18
 */
public class MasterSelector {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";

    private final static String MASTER_PATH="/master";

    public MasterSelector(MasterCenter clientCenter) {
        System.out.println(clientCenter+":开始争抢master节点");
        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder().connectString(CONNECTSTRING).
                retryPolicy(new ExponentialBackoffRetry(30000,3)).build();
        LeaderSelector leaderSelector=new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println(clientCenter+"成为master");
                TimeUnit.SECONDS.sleep(3);
                client.delete();
            }
        });
        leaderSelector.autoRequeue();
        //开始选举
        leaderSelector.start();
    }
}
