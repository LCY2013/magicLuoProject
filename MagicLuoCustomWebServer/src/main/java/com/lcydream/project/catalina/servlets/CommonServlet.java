package com.lcydream.project.catalina.servlets;

import com.lcydream.project.catalina.http.MagicRequest;
import com.lcydream.project.catalina.http.MagicResponse;
import com.lcydream.project.catalina.http.MagicServlet;

/**
 * CommonServlet
 * 通用的Servlet实现
 * @author Luo Chun Yun
 * @date 2018/10/20 16:44
 */
public class CommonServlet extends MagicServlet {

    @Override
    public void doGet(MagicRequest magicRequest, MagicResponse magicResponse) {
        doPost(magicRequest, magicResponse);
    }

    @Override
    public void doPost(MagicRequest magicRequest, MagicResponse magicResponse) {
        try {
            magicResponse.write(magicRequest.getParameter("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
