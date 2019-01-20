package com.lcydream.project.add;


import com.lcydream.project.entity.Response;
import com.lcydream.project.request.OrderRequest;

/**
 * IOrderService
 *
 * @author Luo Chun Yun
 * @date 2018/9/11 21:16
 */
public interface IOrderService {

    /**
     * 生成订单信息
     * @param orderRequest 请求参数
     * @return Response
     */
    Response addOrder(OrderRequest orderRequest);

}
