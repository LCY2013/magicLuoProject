package com.lcydream.project.framework.client.proxy;

import com.lcydream.project.framework.client.handler.RpcProxyHandler;
import com.lcydream.project.framework.core.constant.RpcValues;
import com.lcydream.project.framework.core.exception.CustomException;
import com.lcydream.project.framework.core.message.InvokerMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * MethodProxy
 *
 * @author Luo Chun Yun
 * @date 2018/11/11 16:59
 */
public class MethodProxy implements InvocationHandler {

    /**
     * 日志主键
     */
    private static Logger logger = Logger.getLogger(MethodProxy.class);
    /**
     * 默认读取的文件
     */
    private static final String LOCATION = "application.properties";
    /**
     * 客户端的端口号
     */
    private static int port = 9502;
    /**
     * 远程调用的默认值
     */
    private static String ip = "127.0.0.1";
    private Class<?> clazz;
    private String serviceName;

    static {
        InputStream is = null;
        try {
            Properties config = new Properties();
            is = MethodProxy.class.getClassLoader().getResourceAsStream(LOCATION);
            if (is != null) {
                config.load(is);
            }
            // 获取端口号
            String clientPort = config.getProperty("client.port");
            port = Integer.parseInt(clientPort);
            // 获取端口号
            ip = config.getProperty("client.ip");
        }catch (Exception e){
            logger.error("异常："+e.getMessage());
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MethodProxy(Class<?> clazz,String serviceName) {
        this.clazz = clazz;
        this.serviceName = serviceName;
    }

    /**
     * 代理调用远程服务
     * @param proxy 代理
     * @param method 方法
     * @param args 参数
     * @return 返回值
     * @throws Throwable 异常参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //如果是一个已经实现的类，这里就不用做远程代理的调用
        if(Object.class.equals(method.getDeclaredAnnotations())){
            return method.invoke(this,args);
        }else {
            //如果这里是接口就用远程调用
            return rpcInvoke(method,args);
        }

    }

    /**
     * 远程调用
     * @return 返回调用结果
     */
    public Object rpcInvoke(Method method,Object[] args) throws CustomException {

        InvokerMsg msg = new InvokerMsg();
        msg.setClassName(this.serviceName);
        msg.setMethodName(method.getName());
        msg.setParams(method.getParameterTypes());
        msg.setValues(args);

        EventLoopGroup group = new NioEventLoopGroup();
        //handler处理器
        final RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //设置netty的任务链
                            ChannelPipeline pipeline = ch.pipeline();

                            //设置定长的处理，用于粘包拆包
                            pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(
                                    Integer.MAX_VALUE,0,4,
                                    0,4));
                            pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));

                            //设置编码器,这里使用JDK的序列化
                            pipeline.addLast("encoder",new ObjectEncoder());
                            //设置解码器,这里使用JDK的序列化
                            pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));

                            //业务handler
                            pipeline.addLast(rpcProxyHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            //发送信息
            future.channel().writeAndFlush(msg).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error("异常："+e.getMessage());
        }finally {
            group.shutdownGracefully();
        }
        Object result = rpcProxyHandler.getResult();
        //服务不存在
        if(RpcValues.NO_SERVICE.equalsIgnoreCase(result+"")){
            logger.error("不存在的服务："+this.serviceName);
            throw new CustomException("不存在的服务："+this.serviceName);
        }
        return result;
    }

}
