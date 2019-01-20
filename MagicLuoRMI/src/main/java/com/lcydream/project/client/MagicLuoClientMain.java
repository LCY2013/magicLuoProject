package com.lcydream.project.client;

import com.lcydream.project.remoteinterface.IMagicLuo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * MagicLuoClientMain
 *
 * @author Luo Chun Yun
 * @date 2018/8/20 21:41
 */
public class MagicLuoClientMain {

    public static void main(String[] args) {
        try {
            IMagicLuo magicLuo =
                    (IMagicLuo)Naming.lookup("rmi://127.0.0.1:8888/make");
            String food = magicLuo.make("food");
            System.out.println("client get " + food);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
