package com.lcydream.project.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * AIOClient
 *
 * @author Luo Chun Yun
 * @date 2018/10/18 17:51
 */
public class AIOClient {

    private final AsynchronousSocketChannel client;

    public AIOClient() throws Exception{
        //Asynchronous
        //BIO  Socket
        //NIO  SocketChannel
        //AIO  AsynchronousSocketChannel
        //打开通道
        this.client = AsynchronousSocketChannel.open();
    }

    public void connect(String host,int port) throws Exception{

        //开始发车，连上高速路
        //这里是实现写操作
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {

            //实现IO操作完成的方法
            @Override
            public void completed(Void result, Object attachment) {
                try {
                    client.write(ByteBuffer.wrap(("AIOClient"+System.currentTimeMillis()).getBytes())).get();
                    System.out.println("已发送至服务器");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //AIO操作失败的方法回调
            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        //下面这段代码是只读数据
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        client.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {

            //AIO操作完成
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("AIOServer 反馈结果是："+new String(byteBuffer.array()));
            }

            //AIO操作失败
            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);

        for (int i=0;i<count;i++){
            latch.countDown();
            new Thread(){
                @Override
                public void run() {
                    try {
                        latch.await();
                        new AIOClient().connect("localhost",8080);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        try {
            TimeUnit.SECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
