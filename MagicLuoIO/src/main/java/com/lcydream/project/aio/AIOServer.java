package com.lcydream.project.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.concurrent.*;

/**
 * AIOServer
 *
 * @author Luo Chun Yun
 * @date 2018/10/18 17:51
 */
public class AIOServer {

    private final int port;

    /**
     * 初始化AIO的服务端程序
     * @param port 端口号
     */
    public AIOServer(int port) {
        this.port = port;
        listen();
    }

    /**
     * 监听方法
     */
    private void listen() {
        try {
            //线程缓冲池，体现异步
            ThreadPoolExecutor threadPoolExecutor =
                    new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                            60L, TimeUnit.SECONDS,
                            new SynchronousQueue<Runnable>());

            //给线程池初始化一个线程
            AsynchronousChannelGroup asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(threadPoolExecutor, 1);

            //Asynchronous异步
            //NIO   ServerSocketChannel
            //BIO   ServerSocket

            //设置异步的线程池
            AsynchronousServerSocketChannel serverSocketChannel =
                    AsynchronousServerSocketChannel.open(asynchronousChannelGroup);

            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            System.out.println("服务已启动，监听端口"+port);

            final Map<String,Integer> count =
                    new ConcurrentHashMap<>();
            count.put("count",0);

            //开始等待客户端的连接
            //实现一个CompletionHandler的接口，匿名的实现类
            serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

                final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

                //实现IO操作完成的方法
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    count.put("count",count.get("count")+1);

                    System.out.println("聊天室总人数是："+count.get("count"));
                    //这里我们等待操作系统的回调，IO操作就不用关心了
                    try {
                        buffer.clear();
                        result.read(buffer).get();
                        buffer.flip();
                        result.write(buffer);
                        buffer.flip();
                    }catch (Exception e){
                        System.out.println(e.toString());
                    }finally {
                        try {
                            result.close();
                            serverSocketChannel.accept(null,this);
                        }catch (Exception e){
                            System.out.println(e.toString());
                        }
                    }
                    System.out.println("操作完成");
                }

                //实现IO操作失败的回调
                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("AIO失败回调："+exc);
                }
            });

            try {
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new AIOServer(8080);
    }
}
