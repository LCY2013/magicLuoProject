package com.lcydream.project.consumer.service;

import com.lcydream.project.framework.client.ioc.annotation.CustomAwtowired;
import com.lcydream.project.framework.client.ioc.annotation.CustomService;
import com.lcydream.project.provider.api.IGoodsService;
import com.lcydream.project.provider.api.IUserService;

/**
 * BusinessService
 *
 * @author Luo Chun Yun
 * @date 2018/11/11 19:15
 */
@CustomService
public class BusinessService {

    @CustomAwtowired("userService")
    private IUserService userService;
    @CustomAwtowired("goodsService")
    private IGoodsService goodsService;

    public String login(String name,String passWord){
        return userService.login(name, passWord);
    }

    public Object queryGoodsList(String name,String passWord) {
        return goodsService.queryGoodsList(name, passWord);
    }

    public Object queryGoodsById(String id) {
       return goodsService.queryGoodsById(id);
    }

    public Object queryGoodsByType(String id, String type) {
       return goodsService.queryGoodsByType(id, type);
    }

}
