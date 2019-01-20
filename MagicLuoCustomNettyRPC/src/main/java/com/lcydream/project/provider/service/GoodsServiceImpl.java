package com.lcydream.project.provider.service;

import com.lcydream.project.framework.server.ioc.annotation.CustomAwtowired;
import com.lcydream.project.framework.server.ioc.annotation.CustomService;
import com.lcydream.project.provider.api.IGoodsService;
import com.lcydream.project.provider.api.IUserService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GoodsServiceImpl
 *
 * @author Luo Chun Yun
 * @date 2018/11/11 20:05
 */
@CustomService("goodsService")
public class GoodsServiceImpl implements IGoodsService {

    /**
     * 日志控件
     */
    private static Logger logger = Logger.getLogger(GoodsServiceImpl.class);

    @CustomAwtowired
    private IUserService userService;

    @Override
    public Object queryGoodsList(String name,String passWord) {
        String login = userService.login(name, passWord);
        logger.info("查询商品信息："+login);
        ArrayList<String> list = new ArrayList<>();
        list.add("苹果");
        list.add("香蕉");
        list.add("凤梨");
        list.add("柚子");
        list.add("李子");
        return list;
    }

    @Override
    public Object queryGoodsById(String id) {
        HashMap<String,String> goodMap = new HashMap<>(10);
        goodMap.put("1","苹果");
        goodMap.put("2","香蕉");
        goodMap.put("3","凤梨");
        goodMap.put("4","柚子");
        logger.info("queryGoodsByType : " + id);
        return goodMap.get(id);
    }

    @Override
    public Object queryGoodsByType(String id, String type) {
        HashMap<String,String> goodMap = new HashMap<>(10);
        goodMap.put("1","苹果");
        goodMap.put("2","香蕉");
        goodMap.put("3","凤梨");
        goodMap.put("4","柚子");
        goodMap.put("5","李子");
        logger.info("queryGoodsByType : " + id + "," + type);
        return goodMap.get(id);
    }
}
