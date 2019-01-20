package com.lcydream.project.operation.curator;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.*;

/**
 * CuratorSession
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 11:27
 */
public class CuratorApi {

    public static void main(String[] args) {
        CuratorFramework curator =
                CuratorClientUtils.getInstance();

        try {
            //创建节点
            String createPath = curator.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath("/luo/luo-1/luo-2/luo-3","963".getBytes());
            System.out.println("创建节点："+createPath);

            //删除节点
            curator.delete()
                    .deletingChildrenIfNeeded()
                    .forPath("/dubbo");

            //获取数据信息
            Stat stat = new Stat();
            byte[] bytes = curator.getData()
                    .storingStatIn(stat)
                    .forPath("/magicLuo");
            System.out.println(new String(bytes) + "--->" + JSON.toJSONString(stat));

            //更新内容
            Stat updatePath = curator.setData()
                    .forPath("/magicLuo", "369".getBytes());
            System.out.println("更新数据："+updatePath);

            //异步操作
            //ExecutorService service = Executors.newFixedThreadPool(1);
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                    new ScheduledThreadPoolExecutor(1);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            curator.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .inBackground((client,event) -> {
                            System.out.println(Thread.currentThread().getName()
                                    +"->resultCode:"+event.getResultCode()
                                    +"->option:"+event.getType());
                            countDownLatch.countDown();
                    },scheduledThreadPoolExecutor).forPath("/luo","895".getBytes());
            countDownLatch.await();
            scheduledThreadPoolExecutor.shutdown();

            //事物操作 curator独有
            //CuratorMultiTransaction transaction = curator.transaction();
            Collection<CuratorTransactionResult> curatorTransactionResults =
                    curator.inTransaction().create()
                    .forPath("/trans", "123".getBytes())
                    .and()
                    .setData()
                    .forPath("/magicLuo", "456".getBytes())
                    .and().commit();
            for (CuratorTransactionResult result : curatorTransactionResults){
                System.out.println(result.getForPath()+"->"+result.getType());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
