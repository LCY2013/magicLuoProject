package com.lcydream.project.catalina.netty.server.handler;

import com.lcydream.project.catalina.http.MagicRequest;
import com.lcydream.project.catalina.http.MagicResponse;
import com.lcydream.project.catalina.servlets.CommonServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * MagicWebServerHandler
 * 处理器
 * @author Luo Chun Yun
 * @date 2018/10/20 16:29
 */
public class MagicWebServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest) msg;

            //封装请求参数
            MagicRequest magicRequest = new MagicRequest(ctx,httpRequest);
            //封装返回参数
            MagicResponse magicResponse = new MagicResponse(ctx,httpRequest);

            //处理逻辑
            new CommonServlet().doGet(magicRequest,magicResponse);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
