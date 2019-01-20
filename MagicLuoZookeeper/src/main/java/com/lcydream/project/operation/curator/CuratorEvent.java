package com.lcydream.project.operation.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * CuratorEvent
 *  三种节点监听
 *  PathCache：监视一个路径下节点的创建、删除、节点数据更新
 *  NodeCache：监视一个节点的创建、更新、删除
 *  TreeCache：PathCache+NodeCache(监听路径下的创建、更新、删除事件)
 *  缓存路径下的所有子节点的数据
 * @author Luo Chun Yun
 * @date 2018/9/1 16:25
 */
public class CuratorEvent {

    public static void main(String[] args) throws Exception {
        CuratorFramework curator =
                CuratorClientUtils.getInstance();

        //NodeCache
        NodeCache nodeCache =
                new NodeCache(curator, "/magicLuo", false);
        nodeCache.start(true);

        nodeCache.getListenable()
                .addListener(() ->
                    System.out.println(new String(
                            "节点数据发生变化，变化后的结果："+
                            new String(nodeCache.getCurrentData().getData())))
                );

        curator.setData().forPath("/magicLuo","845".getBytes());

        //PathCache
        PathChildrenCache pathChildrenCache =
                new PathChildrenCache(curator, "/magicLuo", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable()
                .addListener((client,event) -> {
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            System.out.println("创建子节点："+event.getType()+":"+client.getData());
                            break;
                        case CHILD_UPDATED:
                            System.out.println("更新子节点："+event.getType()+":"+client.getData());
                            break;
                        case CHILD_REMOVED:
                            System.out.println("删除子节点："+event.getType()+":"+client.getData());
                            break;
                        case INITIALIZED:
                            System.out.println("初始化子节点："+event.getType()+":"+client.getData());
                            break;
                        default:
                            System.out.println("other:"+event.getType()+":"+client.getData());
                    }
                });

        //创建子节点
        curator.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/magicLuo/luo","435".getBytes());
        TimeUnit.SECONDS.sleep(2);
        //修改子节点
        curator.setData().forPath("/magicLuo/luo","845".getBytes());
        TimeUnit.SECONDS.sleep(2);
        //删除子节点
        curator.delete().deletingChildrenIfNeeded()
                .forPath("/magicLuo/luo");
        TimeUnit.SECONDS.sleep(2);

        System.in.read();
    }
}
