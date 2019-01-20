package com.lcydream.project.chat.client.handler;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.chat.protocal.command.IMP;
import com.lcydream.project.chat.protocal.content.IMMessage;
import com.lcydream.project.chat.thread.CustomThreadPoolExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

/**
 * ChatClientHandler
 *
 * @author Luo Chun Yun
 * @date 2018/10/30 15:08
 */
public class ChatClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志控件
     */
    private static Logger logger = Logger.getLogger(ChatClientHandler.class);

    /**
     * 客户端上下文内容
     */
    private ChannelHandlerContext ctx;

    private String nickName;

    public ChatClientHandler(String nickName){
        this.nickName = nickName;
    }

    /**
     * 会话逻辑
     */
    private void session(){
        //获取线程池
        ExecutorService customThreadPoolExecutor =
                CustomThreadPoolExecutor.getCustomThreadPoolExecutor();
        //实现逻辑
        customThreadPoolExecutor.execute(()->{
            logger.info(nickName + ": 欢迎来到idea控制台");
            IMMessage imMessage = null;
            //等待输入
            Scanner scanner = new Scanner(System.in);
            do {
                if(scanner.hasNext()){
                    //获取输入信息
                    String input = scanner.nextLine();
                    if(IMP.EXIT.getName().equals(input.toUpperCase())) {
                        //新建发送对象
                        imMessage = new IMMessage(IMP.LOGOUT.getName(),System.currentTimeMillis(),nickName);
                    }else {
                        imMessage = new IMMessage(IMP.CHAT.getName(),System.currentTimeMillis(),nickName,input);
                    }
                }
            }while (sendMessage(imMessage));
            scanner.close();
        });
    }

    /**
     * tcp链路建立成功后调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        IMMessage message = new IMMessage(IMP.LOGIN.getName(),System.currentTimeMillis(),this.nickName);
        sendMessage(message);
        logger.info("成功连接服务器,已执行登录动作");
        session();
    }
    /**
     * 收到消息后调用
     * @throws IOException
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        IMMessage m = (IMMessage)msg;
        logger.info(JSON.toJSONString(m));
    }
    /**
     * 发生异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("与服务器断开连接:"+cause.getMessage());
        ctx.close();
    }


    /**
     * 发送消息
     * @param imMessage 消息内容
     * @return 是否继续
     */
    private boolean sendMessage(IMMessage imMessage) {
        ctx.channel().writeAndFlush(imMessage);
        logger.info("已发送到聊天页面，继续输入!");
        return !imMessage.getCmd().equalsIgnoreCase(IMP.LOGOUT.getName());
    }
}
