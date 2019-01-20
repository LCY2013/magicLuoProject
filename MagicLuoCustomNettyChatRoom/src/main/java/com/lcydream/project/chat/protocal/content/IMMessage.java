package com.lcydream.project.chat.protocal.content;

import org.msgpack.annotation.Message;

/**
 * IMMessage
 *  自定义消息封装
 * @author Luo Chun Yun
 * @date 2018/10/26 15:23
 */
@Message
public class IMMessage {

    /**
     * IP地址，端口
     */
    private String addr;

    /**
     * 自定义命令
     */
    private String cmd;

    /**
     * 命令发送时间，时间戳
     */
    private long time;

    /**
     * 在线人数
     */
    private int online;

    /**
     * 发送人
     */
    private String sender;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 消息内容
     */
    private String content;

    public IMMessage(){}

    public IMMessage(String cmd,long time,int online,String content){
        this.cmd = cmd;
        this.time = time;
        this.online = online;
        this.content = content;
    }
    public IMMessage(String cmd,long time,String sender){
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
    }
    public IMMessage(String cmd,long time,String sender,String content){
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
        this.content = content;
    }
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public int getOnline() {
        return online;
    }
    public void setOnline(int online) {
        this.online = online;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
}
