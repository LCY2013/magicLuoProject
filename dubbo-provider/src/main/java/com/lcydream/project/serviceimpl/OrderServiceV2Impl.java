package com.lcydream.project.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.replyentity.MagicRequest;
import com.lcydream.project.replyentity.MagicResponse;
import com.lcydream.project.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * OrderServiceV2Impl
 *
 * @author Luo Chun Yun
 * @date 2018/9/15 10:38
 */
@Service("orderServiceV2")
public class OrderServiceV2Impl implements IOrderService {

    @Override
    public MagicResponse addOrder(MagicRequest magicRequest) {
        System.out.println("v2.0下订单参数是："+ JSON.toJSONString(magicRequest));
        return new MagicResponse("200","v2.0.0下订单成功");
    }

}
