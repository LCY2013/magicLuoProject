package com.lcydream.project.remoteinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IMagicLuo
 *
 * @author Luo Chun Yun
 * @date 2018/8/20 21:31
 */
public interface IMagicLuo extends Remote {

    String make(String thing) throws RemoteException;
}
