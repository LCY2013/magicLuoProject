package com.lcydream.project.designmode.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * LuoMatchMaker
 *
 * @author Luo Chun Yun
 * @date 2018/6/21 21:18
 */
public class LuoMatchMaker implements MethodInterceptor {

    /**
     *  获取被代理人的个人资料,执行代理过程
     *  好像并没有持有被代理对象的引用？
     * @param className
     * @return
     * @throws Exception
     */
    public Object getInstance(String className) throws Exception{
        //反射实例化对象
        Class<?> name = Class.forName(className);
        Object magicLuo = name.newInstance();
        Enhancer enhancer = new Enhancer();
        //把父类设置为谁？
        //这一步就是告诉cglib生成的子类需要继承那个类
        enhancer.setSuperclass(magicLuo.getClass());
        //设置回调
        enhancer.setCallback(this);
        //第一步、生成源代码
        //第二步、编译成class文件
        //第三步、加载到jvm中，并返回代理对象
        return enhancer.create();
    }

    /**
     *  CGLIB
     *   也是利用了字节码重组来实现的
     *   对使用API的用户来说是无感知的，比JDK的动态代理少了一个接口
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("我是luo媒人:");
        System.out.println("开始筛选...");
        //这里的这个o，这个引用是由CGLIB帮我们new出来的，不像JDK动态代理需要对象引用
        //cglib new出来以后的对象，是被代理对象的子类(继承了我们自己写的那个类)
        //oop，在new子类之前，实际上默认是先调用了我们super()方法的
        //new子类的同时，必须先new出父类，这就相当于间接的持有了我们父类的引用
        //子类重写了父类的所有方法
        //我们改变了子类对象的某些属性，是可以间接操作父类的某些属性
        methodProxy.invokeSuper(o,objects);
        //methodProxy.invoke(o,objects);  这里是调用子类自己的方法，如果是这样就会出现死循环结构如下:子类某个方法->intercept->子类某个方法
        System.out.println("如果合适，就准备见面.");
        return null;
    }

}
