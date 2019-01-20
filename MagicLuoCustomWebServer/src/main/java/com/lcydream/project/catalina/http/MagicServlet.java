package com.lcydream.project.catalina.http;

/**
 * MagicServlet
 * 规范服务定义
 * @author Luo Chun Yun
 * @date 2018/10/20 16:40
 */
public abstract class MagicServlet {

    public abstract void doGet(MagicRequest magicRequest,MagicResponse magicResponse);

    public abstract void doPost(MagicRequest magicRequest,MagicResponse magicResponse);

}
