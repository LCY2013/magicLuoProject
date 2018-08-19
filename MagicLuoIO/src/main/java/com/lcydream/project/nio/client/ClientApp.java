package com.lcydream.project.nio.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientApp
 * 短连接，客户端实现
 * @author Luo Chun Yun
 * @date 2018/8/18 17:00
 */
public class ClientApp {

    private final static String CLOSE = "CLOSE";

    public static void main(String[] args) {
        Socket socket=null;
        try {
            socket = new Socket("localhost",8888);
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("magic client:"+Math.random()*100);
            while (true){
                String serverData = reader.readLine();
                if(serverData==null){
                    //Thread.sleep(2000);
                    //continue;
                    break;
                }
                if(CLOSE.equals(serverData.toUpperCase())){
                    break;
                }
                System.out.println("收到服务端消息是："+serverData);
                if(System.currentTimeMillis()%2==0){
                    printWriter.println("magic client：" + System.currentTimeMillis());
                }else {
                    printWriter.println("close");
                }
            }
            printWriter.close();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


}
