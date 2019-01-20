package com.lcydream.project.bio.client;

import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * BIOClient
 *
 * @author Luo Chun Yun
 * @date 2018/10/13 15:10
 */
public class BIOClient {

    public static void main(String[] args) {
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i=0;i<count;i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    Socket client = new Socket("127.0.0.1", 8080);
                    OutputStream outputStream = client.getOutputStream();

                    String clientName = UUID.randomUUID().toString();

                    outputStream.write(clientName.getBytes());
                    outputStream.close();
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }
    }

}
