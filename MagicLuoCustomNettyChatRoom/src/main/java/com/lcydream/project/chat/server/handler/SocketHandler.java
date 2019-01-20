package com.lcydream.project.chat.server.handler;

import com.lcydream.project.chat.protocal.content.IMMessage;
import com.lcydream.project.chat.protocal.processor.MsgProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

/**
 * SocketHandler
 * netty支持的socket
 * @author Luo Chun Yun
 * @date 2018/10/30 14:01
 */
public class SocketHandler extends SimpleChannelInboundHandler<IMMessage> {

    /**
     * 消息处理器
     */
    private MsgProcessor msgProcessor = new MsgProcessor();
    /**
     * 日志工具
     */
    private final static Logger logger = Logger.getLogger(SocketHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessage msg) throws Exception {
        msgProcessor.sendMsg(ctx.channel(),msg);
    }

    /**
     * 客户端加入的事件通知
     * @param ctx 客户端上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        String address = msgProcessor.getAddress(client);
        logger.info("socket client ："+ address + " join");
        super.handlerAdded(ctx);
    }

    /**
     * 客户端离开的事件通知
     * @param ctx 客户端上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        msgProcessor.logout(client);
        logger.info("socket client ：" + msgProcessor.getNickName(client) + " logout");
    }

    /**
     * 客户端上线的事件通知
     * @param ctx 客户端上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        String address = msgProcessor.getAddress(client);
        logger.info("socket client ："+address + " online");
    }

    /**
     * 客户端掉线的事件通知
     * @param ctx 客户端上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        String address = msgProcessor.getAddress(client);
        logger.info("socket client ："+address + " downLine");
        super.channelInactive(ctx);
    }

    /**
     * 客户端异常连接的事件通知
     * @param ctx 客户端上下文
     * @param cause 异常信息
     * @throws Exception 抛出异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel client = ctx.channel();
        String address = msgProcessor.getAddress(client);
        logger.info("socket client ："+address + " exception");
        cause.printStackTrace();
        ctx.close();
    }


}
