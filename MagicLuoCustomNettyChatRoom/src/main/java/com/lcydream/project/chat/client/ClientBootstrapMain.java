package com.lcydream.project.chat.client;

import com.lcydream.project.chat.client.handler.ChatClientHandler;
import com.lcydream.project.chat.protocal.coder.IMDeCoder;
import com.lcydream.project.chat.protocal.coder.IMEnCoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

/**
 * ClientBootstrapMain
 * 客户端启动类
 * @author Luo Chun Yun
 * @date 2018/10/30 15:03
 */
public class ClientBootstrapMain {

    private ChatClientHandler clientHandler;
    private String host;
    private int port;

    public ClientBootstrapMain(String nickName){
        this.clientHandler = new ChatClientHandler(nickName);
    }

    public void connect(String host,int port){
        this.host = host;
        this.port = port;

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IMDeCoder());
                    ch.pipeline().addLast(new IMEnCoder());
                    ch.pipeline().addLast(clientHandler);
                }
            });
            ChannelFuture f = b.connect(this.host, this.port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws IOException {
        new ClientBootstrapMain("magic").connect("127.0.0.1",80);
    }
}
