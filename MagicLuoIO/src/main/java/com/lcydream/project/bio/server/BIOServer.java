package com.lcydream.project.bio.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIOServer
 *
 * @author Luo Chun Yun
 * @date 2018/10/12 16:21
 */
public class BIOServer {

    ServerSocket serverSocket;

    /**
     * 服务器处理
     * @param port 端口号
     */
    public BIOServer(int port){
        try {
            //启动服务端
            serverSocket = new ServerSocket(port);
            System.out.println("BIO服务端应用已启动，启动端口号是："+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始监听，处理逻辑
     * @throws IOException 异常
     */
    public void listenner()throws IOException{
        //死循环监听
        while (true){
            //虽然写一个死循环，但是如果没有客户端来连接，这里就不会向下执行，阻塞，等待客户端链接
            Socket accept = serverSocket.accept();

            //获取输入流，也就是乡村公路
            InputStream is = accept.getInputStream();

            //缓冲区数组定义
            byte[] buff = new byte[1024];
            //获取缓冲区长度
            int len = is.read(buff);
            if(len > 0){
                //服务端收到的消息解码
                String msg = new String(buff, 0, len);
                System.out.println("服务端："+msg);
            }
        }
    }

    public static void main(String[] args) {
        try {
            //启动应用
            new BIOServer(8080).listenner();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
