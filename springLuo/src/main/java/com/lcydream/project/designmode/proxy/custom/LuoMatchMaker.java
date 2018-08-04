package com.lcydream.project.designmode.proxy.custom;

import com.lcydream.project.designmode.proxy.jdk.Person;

import java.lang.reflect.Method;

/**
 * LuoMatchMaker
 *
 * @author Luo Chun Yun
 * @date 2018/6/19 21:21
 */
public class LuoMatchMaker implements LuoInvocationHandler {

    private com.lcydream.project.designmode.proxy.jdk.Person target;

    /**
     *  获取被代理人的个人资料,执行代理过程
     * @param target
     * @return
     * @throws Exception
     */
    public Object getInstance(Person target) throws Exception{
        this.target = target;
        Class clazz = target.getClass();
        System.out.println("被代理对象是:"+clazz+"\n代理对象的名称是:"+clazz.getName());
        return LuoProxy.newProxyInstance(new LuoClassLoader(),clazz.getInterfaces(),this );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是媒人:你的性别是:"+this.target.getSex()+"得给你找个".toLowerCase()+(this.target.getSex()== "男"?"女":"男"));
        System.out.println("开始筛选...");
        //this.target.findLove();
        method.invoke(target,null);
        System.out.println("如果合适，就准备见面.");
        return null;
    }

}
