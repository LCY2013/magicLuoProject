package com.lcydream.project.multicast.multicastclient;

import com.lcydream.project.multicast.Test;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * MulticastClient
 *
 * @author Luo Chun Yun
 * @date 2018/8/18 20:46
 */
public class MulticastClient {

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName("224.10.10.10");
            MulticastSocket multicastSocket = new MulticastSocket(8888);
            multicastSocket.joinGroup(group);
            byte[] bytes = new byte[256];
            while(true){
                DatagramPacket datagramPacket =
                        new DatagramPacket(bytes,bytes.length);
                multicastSocket.receive(datagramPacket);
                String serverData = new String(datagramPacket.getData());
                System.out.println("服务广播消息是："+serverData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
