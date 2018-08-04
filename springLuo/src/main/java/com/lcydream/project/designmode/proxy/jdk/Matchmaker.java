package com.lcydream.project.designmode.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Matchmaker 媒人类
 *
 * @author Luo Chun Yun
 * @date 2018/6/18 21:45
 */
public class Matchmaker implements InvocationHandler {

    private Person target;

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
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    /**
     *  执行找爱人的过程
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是媒人:你的性别是:"+this.target.getSex()+"得给你找个"+(this.target.getSex()== "男"?"女":"男"));
        System.out.println("开始筛选...");
        //this.target.findLove();
        Object invoke = method.invoke(target, null);
        System.out.println("如果合适，就准备见面.");
        return invoke;
    }

}
