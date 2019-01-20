package com.lcydream.project.catalina.netty.server;

import com.lcydream.project.catalina.netty.server.handler.MagicWebServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * MagicWebServer
 * 服务容器启动入口
 * @author Luo Chun Yun
 * @date 2018/10/20 16:05
 */
public class MagicWebServer {

    /**
     * 启动方法实现
     * @param port 启动容器的端口号
     * @throws Exception 异常
     */
    private static void start(int port) throws Exception{

        //BIO实现 ServerSocket serverSocket = new ServerSocket(port);
        //NIO实现 ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); serverSocketChannel.bind(new InetSocketAddress(port));
        // netty实现，主从线程模型,这里是BOSS线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //Worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //netty服务启动
            ServerBootstrap server = new ServerBootstrap();
            //netty的链式编程
            server.group(bossGroup, workerGroup)
                    //主线程处理类
                    .channel(NioServerSocketChannel.class)
                    //子线程的处理，netty的handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            //无锁化串行编程

                            //业务逻辑链路，编码器
                            socketChannel.pipeline().addLast(new HttpResponseEncoder());

                            //解码器设置
                            socketChannel.pipeline().addLast(new HttpRequestDecoder());

                            //业务处理的handler
                            socketChannel.pipeline().addLast(new MagicWebServerHandler());
                        }
                    })
                    //主线程的配置,128表示分配的线程最多128个
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //子线程的配置,表示这是一个长连接服务
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //sync() 表明同步的阻塞，免得直接执行完成服务就挂掉
            ChannelFuture channelFuture = server.bind(port).sync();
            System.out.println("MagicLuoWebServer started -> on port:" + port);
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
