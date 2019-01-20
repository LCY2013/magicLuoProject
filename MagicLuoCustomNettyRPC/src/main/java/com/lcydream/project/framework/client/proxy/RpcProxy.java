package com.lcydream.project.framework.client.proxy;

import java.lang.reflect.Proxy;

/**
 * RpcProxy
 * 客户端的代理
 * @author Luo Chun Yun
 * @date 2018/11/11 16:55
 */
public class RpcProxy {

    public static <T> T create(Class<?> clazz,String serviceName){
        MethodProxy methodProxy = new MethodProxy(clazz,serviceName);
        T result=null;
        try {
            result  = (T)Proxy.newProxyInstance(clazz.getClassLoader(),
                    new Class[]{clazz},methodProxy);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
