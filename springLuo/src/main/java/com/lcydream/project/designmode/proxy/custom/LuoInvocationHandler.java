package com.lcydream.project.designmode.proxy.custom;

import java.lang.reflect.Method;

/**
 * LuoInvocationHandler
 *
 * @author Luo Chun Yun
 * @date 2018/6/19 21:14
 */
public interface LuoInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;

}
