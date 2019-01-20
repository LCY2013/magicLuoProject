package com.lcydream.project.server;

import com.lcydream.project.remotecometure.MagicLuo;
import com.lcydream.project.remoteinterface.IMagicLuo;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * MagicLuoMain
 *
 * @author Luo Chun Yun
 * @date 2018/8/20 21:34
 */
public class MagicLuoServerMain {

    public static void main(String[] args) {
        try {
            IMagicLuo magicLuo = new MagicLuo();
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://127.0.0.1:8888/make",magicLuo);
            System.out.println("rmi server running");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

}
