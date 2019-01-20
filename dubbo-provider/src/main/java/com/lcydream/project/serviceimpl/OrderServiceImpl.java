package com.lcydream.project.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.replyentity.MagicRequest;
import com.lcydream.project.replyentity.MagicResponse;
import com.lcydream.project.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * OrderServiceImpl
 *
 * @author Luo Chun Yun
 * @date 2018/9/11 21:22
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Override
    public MagicResponse addOrder(MagicRequest magicRequest) {
        System.out.println("v1.0下订单参数是："+ JSON.toJSONString(magicRequest));
        return new MagicResponse("200","v1.0.0下订单成功");
    }

}
