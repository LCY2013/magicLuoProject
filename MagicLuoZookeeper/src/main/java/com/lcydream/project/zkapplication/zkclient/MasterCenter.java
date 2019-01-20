package com.lcydream.project.zkapplication.zkclient;

import java.io.Serializable;

/**
 * MasterCenter
 *
 * @author Luo Chun Yun
 * @date 2018/9/2 11:12
 */
public class MasterCenter implements Serializable {

    private static final long serialVersionUID = -1776114173857775665L;

    /**
     * 机器编号
     */
    private int mac_id;

    /**
     * 机器名称
     */
    private String mac_name;

    public int getMac_id() {
        return mac_id;
    }

    public void setMac_id(int mac_id) {
        this.mac_id = mac_id;
    }

    public String getMac_name() {
        return mac_name;
    }

    public void setMac_name(String mac_name) {
        this.mac_name = mac_name;
    }

    @Override
    public String toString() {
        return "MasterCenter{" +
                "mac_id=" + mac_id +
                ", mac_name='" + mac_name + '\'' +
                '}';
    }
}
