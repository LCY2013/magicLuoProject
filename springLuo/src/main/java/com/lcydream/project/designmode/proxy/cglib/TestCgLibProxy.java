package com.lcydream.project.designmode.proxy.cglib;

/**
 * TestCgLibProxy
 *
 * @author Luo Chun Yun
 * @date 2018/6/21 21:27
 */
public class TestCgLibProxy {

  public static void main(String[] args) throws Exception{

      /**
       * JDK的动态代理是通过接口来进行强制转换的
       * 生成以后的代理对象，可以进行强制转换为接口
       *
       * CGLIB的动态代理是通过生成一个被代理对象的子类，然后重写父类的方法
       * 生成以后的对象，可以强制转换为被代理对象(也就是自己写的类)
       * 子类引用赋值给父类
       */

      MagicLuo obj = (MagicLuo)new LuoMatchMaker().getInstance("com.lcydream.project.proxy.cglib.MagicLuo");
      obj.findLove();

  }

}
