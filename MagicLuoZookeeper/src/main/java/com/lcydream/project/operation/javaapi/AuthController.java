package com.lcydream.project.operation.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * AuthController
 *  权限控制模式
 * schema：授权对象
 * ip     : 192.168.1.1
 * Digest  : username:password
 * world  : 开放式的权限控制模式，数据节点的访问权限对所有用户开放。 world:anyone
 * super  ：超级用户，可以对zookeeper上的数据节点进行操作
 *
 *  // acl (create /delete /admin /read/write)
 *  //权限模式： ip/Digest（username:password）/world/super
 * @author Luo Chun Yun
 * @date 2018/9/1 9:38
 */
public class AuthController implements Watcher {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static CountDownLatch countDownLatchTwo = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static int i = 0;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(CONNECTSTRING, 3000, new AuthController());
        countDownLatch.await();
        //生成zk的认证权限
        ACL aclOne = new ACL(ZooDefs.Perms.READ,new Id("digest",
                DigestAuthenticationProvider.generateDigest("root:root")));
        ACL aclTwo = new ACL(ZooDefs.Perms.CREATE,new Id("ip", "192.168.21.160"));
        List<ACL> list = new ArrayList<>();
        list.add(aclOne);
        list.add(aclTwo);
        String path = zooKeeper.create("/luo", "456".getBytes(), list, CreateMode.PERSISTENT);
        System.out.println("--->"+path);

        ZooKeeper zk = new ZooKeeper(CONNECTSTRING, 3000, new AuthController());
        countDownLatchTwo.await();
        zk.addAuthInfo("digest", "r:r".getBytes());
        zk.delete("/luo",-1);

    }

    @Override
    public void process(WatchedEvent event) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if(event.getState()==Event.KeeperState.SyncConnected){
            if(Event.EventType.None==event.getType()&&null==event.getPath() && i==0){
                countDownLatch.countDown();
                System.out.println(event.getState()+"-->"+event.getType());
                i++;
            }else {
                countDownLatchTwo.countDown();
                System.out.println(event.getState()+"-->"+event.getType());
            }
        }
    }
}
