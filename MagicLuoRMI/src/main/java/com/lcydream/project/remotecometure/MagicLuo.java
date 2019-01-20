package com.lcydream.project.remotecometure;

import com.lcydream.project.remoteinterface.IMagicLuo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * MagicLuo
 *
 * @author Luo Chun Yun
 * @date 2018/8/20 21:32
 */
public class MagicLuo extends UnicastRemoteObject implements IMagicLuo {

    public MagicLuo() throws RemoteException {
    }

    @Override
    public String make(String thing) throws RemoteException {
        System.out.println("execute " + thing);
        return "server make ->" + thing;
    }

}
