package com.lcydream.project.nio.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServiceApp
 * 短连接，服务器端实现，同步非阻塞
 * @author Luo Chun Yun
 * @date 2018/8/18 16:59
 */
public class ServiceApp {

    private final static String CLOSE = "CLOSE";

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            while (true) {
                Socket accept = serverSocket.accept();
                new Thread(() -> {
                    BufferedReader reader=null;
                    PrintWriter printWriter=null;
                    try {
                        do {
                            reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                            String clientData = reader.readLine();
                            if(clientData==null){
                                //Thread.sleep(2000);
                                //continue;
                                break;
                            }
                            if(CLOSE.equals(clientData.toUpperCase())){
                                System.out.println("收到客户端关闭的指令:"+clientData);
                                break;
                            }
                            System.out.println("服务端收到客户端消息："+clientData);
                        }while (accept.getInputStream().available()!=0);
                        printWriter =
                                new PrintWriter(new OutputStreamWriter(accept.getOutputStream()),true);
                        if(System.currentTimeMillis()%2==0) {
                            printWriter.println("magic luo:" + System.currentTimeMillis());
                        }else {
                            printWriter.println("close2");
                        }
                        printWriter.flush();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        try {
                            if(printWriter!=null){
                                printWriter.close();
                            }
                            if(reader!=null){
                                reader.close();
                            }
                            Thread.currentThread().interrupt();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(serverSocket!=null){
                    serverSocket.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
