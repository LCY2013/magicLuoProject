package com.lcydream.project.framework.client.handler;

import com.lcydream.project.framework.server.handler.RegistryHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * RpcProxyHandler
 *
 * @author Luo Chun Yun
 * @date 2018/11/11 17:24
 */
public class RpcProxyHandler extends ChannelInboundHandlerAdapter {
    /**
     * 日志组件
     */
    private static Logger logger = Logger.getLogger(RpcProxyHandler.class);

    private Object result;

    public Object getResult(){
        return this.result;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info(cause.getMessage());
        ctx.close();
    }
}
