package com.lcydream.project.framework.server.handler;

import com.lcydream.project.framework.core.constant.RpcValues;
import com.lcydream.project.framework.core.message.InvokerMsg;
import com.lcydream.project.framework.server.ioc.context.HandlerApplicationContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * RegistryHandler
 * 注册的处理器，这里用来接收请求
 * @author Luo Chun Yun
 * @date 2018/11/11 16:12
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志组件
     */
    private static Logger logger = Logger.getLogger(RegistryHandler.class);
    /**
     * 默认读取的文件
     */
    private static final String LOCATION = "application.properties";

    private static HandlerApplicationContext handlerApplicationContext;

    public RegistryHandler() {
        if (handlerApplicationContext == null) {
            synchronized (RegistryHandler.class) {
                if (handlerApplicationContext == null) {
                    handlerApplicationContext =
                            new HandlerApplicationContext(LOCATION);
                }
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       Object result = new Object();
       //客户端的调用信息
       InvokerMsg request = (InvokerMsg)msg;
       //判断服务端是否存在该服务
       if(handlerApplicationContext.getAll().containsKey(request.getClassName())){
           Object clazz = handlerApplicationContext.getAll().get(request.getClassName());
           //服务存在就直接调用
           if(clazz != null){
               Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParams());
               result = method.invoke(clazz, request.getValues());
           }else {
               //不存在的服务就直接返回-1
               result = RpcValues.NO_SERVICE;
           }
       }
       ctx.writeAndFlush(result);
       ctx.flush();
       ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info(cause.getMessage());
        ctx.close();
    }

}
