package com.lcydream.project.chat.server.handler;

import com.lcydream.project.chat.protocal.processor.MsgProcessor;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;

/**
 * WebSocketHandler
 * netty支持webSocket协议
 * @author Luo Chun Yun
 * @date 2018/10/29 21:09
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 处理逻辑
     */
    private MsgProcessor msgProcessor = new MsgProcessor();
    /**
     * 日志处理工具
     */
    private final static Logger logger = Logger.getLogger(WebSocketHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        msgProcessor.sendMsg(ctx.channel(),msg.text());
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
        logger.info("webSocket client ："+address + " join");
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
        logger.info("webSocket client ：" + msgProcessor.getNickName(client) + " logout");
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
        logger.info("webSocket client ："+address + " online");
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
        logger.info("webSocket client ："+address + " downLine");
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
        logger.info("webSocket client ："+address + " exception");
    }
}
