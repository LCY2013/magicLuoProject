package com.lcydream.project.service;

import com.lcydream.project.replyentity.MagicRequest;
import com.lcydream.project.replyentity.MagicResponse;

/**
 * IOrderService
 *
 * @author Luo Chun Yun
 * @date 2018/9/11 21:16
 */
public interface IOrderService {

    MagicResponse addOrder(MagicRequest magicRequest);

}
