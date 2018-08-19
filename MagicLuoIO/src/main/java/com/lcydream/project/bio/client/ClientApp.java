package com.lcydream.project.bio.client;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientApp
 *
 * @author Luo Chun Yun
 * @date 2018/8/18 11:01
 */
public class ClientApp {

    public static void main(String[] args) {
        Socket socket=null;
        PrintWriter printWriter=null;
        try {
            socket = new Socket("127.0.0.1",8888);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.write("hello magic");
            printWriter.flush();
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
            if(printWriter!=null){
                try {
                    printWriter.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
