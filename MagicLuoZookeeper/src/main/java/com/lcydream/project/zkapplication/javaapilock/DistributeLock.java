package com.lcydream.project.zkapplication.javaapilock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * DistributeLock
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 20:44
 */
public class DistributeLock {

    //锁的根节点
    private static final String ROOT_LOCKS="/LOCKS";

    private ZooKeeper zooKeeper;

    //会话超时时间
    private int sessionTimeout;

    //记录锁节点id
    private String lockID;

    //节点的数据
    private final static byte[] data = {1,2,3};

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistributeLock() {
        this.zooKeeper = ZookeeperClient.getInstance();
        this.sessionTimeout = ZookeeperClient.getSessionTimeout();
    }

    /**
     * 获取锁方法
     * @return
     */
    public boolean lock(){
        try {
            lockID = zooKeeper.create(ROOT_LOCKS+"/",data,
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+
                    "--->成功创建lock节点["+lockID+"],开始竞争锁");

            //获取根节点下面所有的子节点
            List<String> children =
                    zooKeeper.getChildren(ROOT_LOCKS, true);
            //从小到大排序
            SortedSet<String> sortedSet = new TreeSet<>();
            for(String child : children){
                sortedSet.add(ROOT_LOCKS+"/"+child);
            }
            //获取最小节点
            String first = sortedSet.first();
            if(lockID.equals(first)){
                //当前线程就是能够获取锁的最小节点
                System.out.println(Thread.currentThread().getName()+
                        "->成功获取到了锁,lock节点为["+lockID+"]");
                return true;
            }
            //找到小与当前锁ID的所有锁ID信息
            SortedSet<String> lessThanLockId =
                    sortedSet.headSet(lockID);
            //如果存在小于当前锁ID，就找出距离当前锁ID最近的那个锁ID
            if(!lessThanLockId.isEmpty()){
                //找出小于当前锁ID集合中最大的那个锁ID
                String prevLockID =
                        lessThanLockId.last();
                //当前锁ID节点所在进程去监听preLockID
                zooKeeper.exists(prevLockID,new LockWacher(countDownLatch));
                boolean await = countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                //判断是等待超时还是计数器归零，如果是计数器归零则代表上一个锁节点被删除，可以执行操作，否则不可以操作
                if(await){
                    System.out.println(Thread.currentThread().getName()+"->成功获取到锁：["+lockID+"]");
                }else {
                    //等待超时就释放该锁ID
                    unLock();
                }
                return await;
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unLock(){
        System.out.println(Thread.currentThread().getName()+"->开始释放锁：["+lockID+"]");
        try {
            zooKeeper.delete(lockID,-1);
            System.out.println("节点["+lockID+"]删除成功");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(10);

        Random random = new Random();

        for (int i=0;i<10;i++){
            new Thread(()->{
                DistributeLock lock = new DistributeLock();
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();
                    lock.lock();
                    Thread.sleep(random.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unLock();
                }
            }).start();
        }
    }

}
