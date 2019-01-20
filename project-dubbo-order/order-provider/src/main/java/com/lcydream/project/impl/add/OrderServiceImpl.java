package com.lcydream.project.impl.add;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.add.IOrderService;
import com.lcydream.project.dal.OrderDao;
import com.lcydream.project.entity.Request;
import com.lcydream.project.entity.Response;
import com.lcydream.project.enums.ResponseEnum;
import com.lcydream.project.login.IUserService;
import com.lcydream.project.request.OrderRequest;
import com.lcydream.project.vlid.VlidParam;
import com.lcydream.project.vlid.VlidResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * OrderServiceImpl
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 14:45
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    JtaTransactionManager springTransactionManager;

    @Autowired
    OrderDao orderDao;

    @Autowired
    IUserService userService;

    /**
     * 生成订单
     * @param orderRequest 请求参数
     * @return Response
     */
    @Override
    public Response addOrder(OrderRequest orderRequest) {
        Response response = new Response();

        //参数校验
        VlidResult vlidResult = VlidParam.check(orderRequest);
        if(!vlidResult.isSuccess()){
            response.setCode(ResponseEnum.VLIDPARAMFAILED.getCode());
            response.setMessage(vlidResult.getMassage());
            return response;
        }
        //order下单后调用user更新余额
        UserTransaction userTransaction=springTransactionManager.getUserTransaction();
        try {
            userTransaction.begin();
            orderDao.insertOrder();
            userService.debit(new Request());
            System.out.println(JSON.toJSONString(userService.debit(new Request())));
            userTransaction.commit();
        }catch(Exception e){
            try {
                userTransaction.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
        //下单成功
        System.out.println("下单成功："+ JSON.toJSONString(orderRequest));
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        return response;
    }
}
