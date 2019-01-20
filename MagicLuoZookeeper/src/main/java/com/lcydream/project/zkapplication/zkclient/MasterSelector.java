package com.lcydream.project.zkapplication.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * MasterSelector
 *
 * @author Luo Chun Yun
 * @date 2018/9/3 21:57
 */
public class MasterSelector {

    /**
     * zk客户端
     */
    private ZkClient zkClient;

    /**
     * master注册节点，只存在一个客户端可以争抢成功
     */
    private static final String MASTER_PATH="/master";

    /**
     *  zk监听器
     */
    private IZkDataListener zkDataListener;

    /**
     * 从节点
     */
    private MasterCenter slaverCenter;

    /**
     * 主节点
     */
    private MasterCenter masterCenter;

    /**
     * 是否启动
     */
    private boolean isRunning=false;

    /**
     * 线程池
     */
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            new ScheduledThreadPoolExecutor(1);

    public MasterSelector(ZkClient zkClient, MasterCenter clientCenter) {
        System.out.println(clientCenter+":去争抢master节点");
        this.zkClient = zkClient;
        this.slaverCenter = clientCenter;

        //注册客户端的监听器
        this.zkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                //如果master节点销毁掉或者异常掉线
                chooseMaster();
            }
        };
    }

    /**
     *开始选举
     */
    public void start(){
        //服务已经启动
        if(isRunning){
            System.out.println("master选举服务已经启动，不需要重复启动");
        }else {
            //标识已经启动
            isRunning = true;
            //注册监听事件
            zkClient.subscribeDataChanges(MASTER_PATH,zkDataListener);
            //开始选择master节点
            chooseMaster();
            //System.out.println("master选举服务已经启动...");
        }
    }

    /**
     * 结束选举
     */
    public void stop(){
        //服务已经启动
        if(isRunning){
            //标识已经停止
            isRunning = false;
            //取消监听
            zkClient.unsubscribeDataChanges(MASTER_PATH,zkDataListener);
            //释放节点信息
            releaseMaster();
        }else {
            System.out.println("master选举服务已经停止，不需要重复停止");
        }
    }

    /**
     * 选举master(客户端去争抢)
     */
    private void chooseMaster(){
        if(!isRunning){
            System.out.println("服务未初始化，请先启动服务...");
        }

        try {
            System.out.println(slaverCenter+":去争抢master节点");
            //尝试去创建一个临时节点(尝试成为master)
            zkClient.createEphemeral(MASTER_PATH,slaverCenter);
            //自己成为master节点
            masterCenter = slaverCenter;
            System.out.println(slaverCenter+":成为master节点");

            //模拟master丢失
            scheduledThreadPoolExecutor.schedule(()->
                releaseMaster(),5, TimeUnit.SECONDS);
        }catch (Exception e){
            //发生异常说明存在或者网络错误，这时先去查询是否存在master节点
            MasterCenter masterCenter = zkClient.readData(MASTER_PATH);
            //如果不存在master节点就重新选举
            if(masterCenter==null){
                chooseMaster();
            }else {
                //设置master节点
                this.masterCenter = masterCenter;
            }
        }

    }

    /**
     * 丢掉master节点
     */
    private void releaseMaster(){
        //判断是否是master节点，如果是就释放
        if(checkMaster()){
            zkClient.deleteRecursive(MASTER_PATH);
            masterCenter = null;
        }
    }

    /**
     * 判断该客户端是否master
     * @return
     */
    private boolean checkMaster(){
        MasterCenter masterCenter =
                zkClient.readData(MASTER_PATH);
        //判断master是否是这个节点
        if(slaverCenter.getMac_name().equals(masterCenter.getMac_name())){
            return true;
        }
        return false;
    }
}
