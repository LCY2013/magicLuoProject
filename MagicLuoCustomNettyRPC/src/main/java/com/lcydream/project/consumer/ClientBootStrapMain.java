package com.lcydream.project.consumer;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.consumer.service.BusinessService;
import com.lcydream.project.framework.client.ioc.context.HandlerApplicationContext;

/**
 * ClientBootStrapMain
 *
 * @author Luo Chun Yun
 * @date 2018/11/11 16:54
 */
public class ClientBootStrapMain {

    public static void main(String[] args) {

        HandlerApplicationContext handlerApplicationContext =
                new HandlerApplicationContext("application.properties");
        BusinessService businessService = (BusinessService)handlerApplicationContext.getBean(BusinessService.class);
        System.out.println(businessService.login("luo","luo"));
        System.out.println(JSON.toJSONString(businessService.queryGoodsList("luo","luo")));
        System.out.println(JSON.toJSONString(businessService.queryGoodsById("1")));
        System.out.println(JSON.toJSONString(businessService.queryGoodsByType("2","水果")));
    }
}
