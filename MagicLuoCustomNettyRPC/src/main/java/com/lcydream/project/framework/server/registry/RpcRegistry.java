package com.lcydream.project.framework.server.registry;

import com.lcydream.project.framework.server.handler.RegistryHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * RpcRegistry
 * rpc注册服务
 * @author Luo Chun Yun
 * @date 2018/11/11 15:41
 */
public class RpcRegistry {

    /**
     * 日志组件
     */
    private static Logger logger = Logger.getLogger(RpcRegistry.class);

    /**
     * 默认读取的文件
     */
    private static final String LOCATION = "application.properties";

    /**
     * 端口信息
     */
    private int port;

    public RpcRegistry() {
        InputStream is = null;
        try {
            Properties config = new Properties();
            is = this.getClass().getClassLoader().getResourceAsStream(LOCATION);
            if (is != null) {
                config.load(is);
            }
            // 获取端口号
            String serverPort = config.getProperty("service.port");
            this.port = Integer.parseInt(serverPort);
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

    public void start(){

        //开启服务端的主从模式
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //服务模式
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置业务
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            //设置netty的任务链
                            ChannelPipeline pipeline = ch.pipeline();

                            //设置定长的处理，用于粘包拆包
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(
                                    Integer.MAX_VALUE,0,4,
                                    0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));

                            //设置编码器,这里使用JDK的序列化
                            pipeline.addLast("encoder",new ObjectEncoder());
                            //设置解码器,这里使用JDK的序列化
                            pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));

                            //业务handler
                            pipeline.addLast(new RegistryHandler());
                        }
                    })
            .option(ChannelOption.SO_BACKLOG,128)
            .childOption(ChannelOption.SO_KEEPALIVE,true);
            //绑定端口，获取回调
            ChannelFuture future = serverBootstrap.bind(this.port).sync();
            logger.info("magicLuo RPC Service start, on port : "+this.port);
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
