package com.lcydream.project.operation.zkclient;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

/**
 * ZkClientApi
 *
 * @author Luo Chun Yun
 * @date 2018/9/1 11:06
 */
public class ZkClientApi {

    private final static String CONNECTSTRING="192.168.21.160:2181,192.168.21.163:2181,192.168.21.164:2181,192.168.21.165:2181";

    public static void main(String[] args) throws Exception{
        ZkClient zkClient = new ZkClient(CONNECTSTRING, 30000);

        /*if(zkClient.exists("/luo/luo-1/luo-2/luo-3")) {
            zkClient.createPersistent("/luo/luo-1/luo-2/luo-3", true);
            System.out.println("成功");
        }

        zkClient.deleteRecursive("/luo");
        System.out.println("删除成功");*/

        /*List<String> children = zkClient.getChildren("/");
        System.out.println(JSON.toJSONString(children));*/

        //watcher订阅事件
        zkClient.subscribeDataChanges("/magicLuo", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点路径："+dataPath+"->修改后的值："+JSON.toJSONString(data));
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });

        zkClient.writeData("/magicLuo","456");
        TimeUnit.SECONDS.sleep(5);
    }

}
