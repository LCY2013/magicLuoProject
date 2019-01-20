package com.lcydream.project;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.lcydream.project.replyentity.MagicRequest;
import com.lcydream.project.replyentity.MagicResponse;
import com.lcydream.project.service.IOrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * AppClientMain
 *
 * @author Luo Chun Yun
 * @date 2018/9/11 21:56
 */
public class AppClientMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("order-consumer.xml");
        MagicRequest magicRequest = new MagicRequest();
        for(int i = 0;i<10;i++) {
            /*v1.0.0调用*/
            IOrderService orderService =
                    (IOrderService) context.getBean("orderServices");
            Map<String, Object> map = new HashMap<>(5);
            map.put("orderId", "iksji587746");
            map.put("createTime", new Date());
            magicRequest.setData(JSON.toJSONString(map));
            MagicResponse magicResponse = orderService.addOrder(magicRequest);
            System.out.println("v1.0服务器返回：" + JSON.toJSONString(magicResponse));
        }
        /*v2.0.0调用*/
        IOrderService orderServiceV2 =
                (IOrderService)context.getBean("orderServicesV2");
        MagicResponse magicResponseV2 = orderServiceV2.addOrder(magicRequest);
        System.out.println("v2.0服务器返回："+JSON.toJSONString(magicResponseV2));

        /*异步调用*/
        IOrderService orderServiceV2Asyc =
                (IOrderService)context.getBean("orderServicesV2Asyc");
        orderServiceV2Asyc.addOrder(magicRequest);
        Future<MagicResponse> future = RpcContext.getContext().getFuture();
        MagicResponse magicResponseAsyc = future.get();
        System.out.println("asyc v2.0服务器返回："+JSON.toJSONString(magicResponseAsyc));
    }
}
