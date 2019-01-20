package com.lcydream.project.zkapplication.curator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                    MasterCenter userCenter = new MasterCenter();
                    userCenter.setMac_id(id);
                    userCenter.setMac_name("客户端：" + id);
                    MasterSelector selector = new MasterSelector(userCenter);
                    selectorLists.add(selector);

                }).start();
            }
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
