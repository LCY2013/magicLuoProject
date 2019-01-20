package com.lcydream.project;

import com.lcydream.project.chat.protocal.coder.IMDeCoder;
import com.lcydream.project.chat.protocal.coder.IMEnCoder;
import com.lcydream.project.chat.server.handler.HttpHandler;
import com.lcydream.project.chat.server.handler.SocketHandler;
import com.lcydream.project.chat.server.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.log4j.Logger;

/**
 * ServerBootstrapMain
 * 系统启动类
 * @author Luo Chun Yun
 * @date 2018/10/21 16:06
 */
public class ServerBootstrapMain {

    /**
     * 日志处理工具
     */
    private static Logger logger = Logger.getLogger(ServerBootstrapMain.class);

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
            //netty的链式编程，这里采用的主从模型，boss是主线程池：做权限控制等，worker是从线程池：做事件处理
            server.group(bossGroup, workerGroup)
                    //主线程处理类
                    .channel(NioServerSocketChannel.class)
                    //父线程处理，比如权限控制等
                    //.handler(null)
                    //子线程的处理，netty的handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            //无锁化串行编程
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //设置自定义编码器，解码器和处理器
                            /** 解析自定义协议 */
                            pipeline.addLast(new IMDeCoder());
                            pipeline.addLast(new IMEnCoder());
                            pipeline.addLast(new SocketHandler());

                            /** 解析Http请求 */
                            pipeline.addLast(new HttpServerCodec());
                            //主要是将同一个http请求或响应的多个消息对象变成一个 fullHttpRequest完整的消息对象
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                            //主要用于处理大数据流,比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的 ,加上这个handler我们就不用考虑这个问题了
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpHandler());

                            /** 解析WebSocket请求 */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/im"));
                            pipeline.addLast(new WebSocketHandler());
                        }
                    })
                    //主线程的配置,1024表示分配的线程最多1024个
                    .option(ChannelOption.SO_BACKLOG, 1024)
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
            start(80);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
