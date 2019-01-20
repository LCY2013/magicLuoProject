package com.lcydream.project.chat.protocal.command;

/**
 * IMP(Instant Messaging Protocol 及时通讯协议)
 *  及时通讯协议定义
 * @author Luo Chun Yun
 * @date 2018/10/26 14:55
 */
public enum IMP {

    /**
     * 系统消息
     */
    SYSTEM("SYSTEM"),
    /**
     * 登录命令
     */
    LOGIN("LOGIN"),
    /**
     * 登出命令
     */
    LOGOUT("LOGOUT"),
    /**
     * 聊天消息
     */
    CHAT("CHAT"),
    /**
     * 送花了
     */
    FLOWER("FLOWER"),
    /**
     * 离开命令
     */
    EXIT("EXIT");

    private String name;

    public static boolean isIMP(String content){
        return content.matches("^\\[(SYSTEM|LOGIN|LOGOUT|CHAT|FLOWER)\\]");
    }

    IMP(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
