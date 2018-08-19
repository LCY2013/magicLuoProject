package com.lcydream.project.bio.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerApp
 * bio同步阻塞例子
 * @author Luo Chun Yun
 * @date 2018/8/18 11:00
 */
public class ServerApp {

    public static void main(String[] args) {
        ServerSocket serverSocket=null;
        BufferedReader bufferedReader=null;
        try {
            //开启一个io服务
            serverSocket = new ServerSocket(8888);
            Socket accept = serverSocket.accept();
            do {
                bufferedReader =
                        new BufferedReader(new InputStreamReader(accept.getInputStream()));
                System.out.println("服务端收到消息是：" + bufferedReader.readLine());
            }while (accept.getInputStream().available()!=0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
