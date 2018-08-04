package com.lcydream.project.designmode.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * TestFindLove
 *
 * @author Luo Chun Yun
 * @date 2018/6/18 21:42
 */
public class TestFindLove {

  public static void main(String[] args) throws Exception{
      /*MagicLuo magicLuo = new MagicLuo();
      magicLuo.findLove();*/
      Person obj = (Person)new Matchmaker().getInstance(new MagicLuo());
      //System.out.println("这里的这个代理对象是:"+obj.getClass()+"\n代理对象的名称是:"+obj.getClass().getName());
      obj.findLove();

      /**
       * 原理;
       * 1、拿到被代理对象最原始的引用，然后获取它的接口
       * 2、JDK代理重新生成一个类，同时实现我们给的代理对象的接口
       * 3、把被代理对象的引用也拿到了
       * 4、重新动态生成一个class字节码
       * 5、然后编译
       */

      /*
      * 生成代理类的内容
      * */
      byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
      FileOutputStream fileOutputStream = new FileOutputStream("$Proxy0.class");
      fileOutputStream.write(data);
      fileOutputStream.close();

      /**
       * 满足代理模式应用场景的三个必要条件，穷举法：
       * 1、两个角色，执行者，被代理对象
       * 2、注重过程，必须要做，被代理对象没有时间做或者不想做，不专业
       * 3、执行者必须拿到被代理对象的个人资料（执行者持有被代理对象引用）
       * */
  }
}
