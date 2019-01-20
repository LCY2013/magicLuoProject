package com.lcydream.project.zkapplication.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * BootStrap
 *
 * @author Luo Chun Yun
 * @date 2018/9/3 22:38
 */
public class BootStrap {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";

    public static void main(String[] args) {
        List<MasterSelector> selectorLists=new ArrayList<>();
        try {
            for(int i=0;i<10;i++) {
                new Thread(()->{
                    Random random = new Random();
                    int id = random.nextInt(100);
                    ZkClient zkClient = new ZkClient(CONNECTSTRING, 30000,
                            30000,
                            new SerializableSerializer());
                    MasterCenter userCenter = new MasterCenter();
                    userCenter.setMac_id(id);
                    userCenter.setMac_name("客户端：" + id);
                    MasterSelector selector = new MasterSelector(zkClient,userCenter);
                    selectorLists.add(selector);
                    selector.start();//触发选举操作
                }).start();
                /*ZkClient zkClient = new ZkClient(CONNECTSTRING, 30000,
                        30000,
                        new SerializableSerializer());
                MasterCenter userCenter = new MasterCenter();
                userCenter.setMac_id(i);
                userCenter.setMac_name("客户端：" + i);

                MasterSelector selector = new MasterSelector(zkClient,userCenter);
                selectorLists.add(selector);
                selector.start();//触发选举操作
                TimeUnit.SECONDS.sleep(1);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for(MasterSelector selector:selectorLists){
                selector.stop();
            }
        }
    }

}
