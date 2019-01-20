package com.lcydream.project.operation.javaapi;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CreateNode
 *
 * @author Luo Chun Yun
 * @date 2018/8/31 22:15
 */
public class CreateNode implements Watcher{

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(CONNECTSTRING, 3000, new CreateNode());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //创建节点
        String retVal = zooKeeper.create("/luo", "321".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("创建临时节点结果："+retVal);

        //获取节点数据
        zooKeeper.getData("/luo",true,new Stat());

        //修改节点数据
        zooKeeper.setData("/luo","456".getBytes(),-1);

        Stat stat=zooKeeper.exists("/luoCY",true);
        //表示节点不存在
        if(stat==null){
            //创建永久节点
            String retValP = zooKeeper.create("/luoCY", "321".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("创建永久节点结果："+retValP);
            List<String> childrenLuoCY = zooKeeper.getChildren("/luoCY", true);
            System.out.println(JSON.toJSONString(childrenLuoCY));
        }else {
            List<String> childrenLuoCY = zooKeeper.getChildren("/luoCY", true);
            System.out.println(JSON.toJSONString(childrenLuoCY));
        }

        //监听永久节点
        //zooKeeper.getData("/luoCY",true,new Stat());

        Stat statChild=zooKeeper.exists("/luoCY/luo",true);
        //表示节点不存在
        if(statChild==null) {
            //新增永久节点的子节点
            zooKeeper.create("/luoCY/luo", "321".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.getChildren("/luoCY/luo",true);
        }else {
            zooKeeper.getChildren("/luoCY/luo",true);
        }

        //监听永久节点的子节点
        //zooKeeper.getData("/luoCY/luo",true,new Stat());

        //修改永久节点的子节点数据
        zooKeeper.setData("/luoCY/luo","456".getBytes(),-1);

        //删除永久节点的子节点
        zooKeeper.delete("/luoCY/luo",-1);

        //删除节点
        zooKeeper.delete("/luoCY",-1);

        TimeUnit.SECONDS.sleep(30);




    }

    @Override
    public void process(WatchedEvent event){
        //如果当前的连接状态是连接成功，就利用计数器去控制
        if(event.getState()==Event.KeeperState.SyncConnected) {
            if(event.getType() == Event.EventType.None || null == event.getPath()) {
                countDownLatch.countDown();
                System.out.println(event.getState()+"==>"+event.getType());
            }else if(event.getType() == Event.EventType.NodeChildrenChanged){
                try {
                    System.out.println("NodeChildrenChanged修改:"+event.getPath()+"<==>修改后的是:"+
                            zooKeeper.getData(event.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(event.getType() == Event.EventType.NodeCreated){
                try {
                    System.out.println("NodeCreated修改:"+event.getPath()+"<==>修改后的是:"+
                            zooKeeper.getData(event.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(event.getType() == Event.EventType.NodeDataChanged){
                try {
                    System.out.println("NodeDataChanged修改:"+event.getPath()+"<==>修改后的是:"+
                            zooKeeper.getData(event.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(event.getType() == Event.EventType.NodeDeleted){
                System.out.println("NodeDeleted:"+event.getPath());
            }
            System.out.println("节点监听状态是:"+event.getType());
        }
        System.out.println(zooKeeper.getState());
    }
}
