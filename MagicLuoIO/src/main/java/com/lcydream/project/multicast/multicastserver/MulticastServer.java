package com.lcydream.project.multicast.multicastserver;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.TimeUnit;

/**
 * MulticastServer
 *
 * @author Luo Chun Yun
 * @date 2018/8/18 20:29
 */
public class MulticastServer {

    public static void main(String[] args) {
        try {
            //地址段：224.0.0.0-239.255.255.255 属于广播
            InetAddress group = InetAddress.getByName("224.10.10.10");
            MulticastSocket multicastSocket = new MulticastSocket();
            while (true){
                String dataServer = "server data:"+System.currentTimeMillis();
                byte[] bytes = dataServer.getBytes();
                multicastSocket.send(
                        new DatagramPacket(bytes,bytes.length,group,8888));
                TimeUnit.SECONDS.sleep(3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
