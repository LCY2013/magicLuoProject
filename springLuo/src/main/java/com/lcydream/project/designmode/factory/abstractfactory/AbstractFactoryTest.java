package com.lcydream.project.designmode.factory.abstractfactory;

/**
 * AbstractFactoryTest
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 22:15
 */
public class AbstractFactoryTest {

      public static void main(String[] args) {
        DefaultFactory factory = new DefaultFactory();
        System.out.println(factory.getCar("BMW"));

        //设计模式的经典之处，就在于，解决了编写代码的人和调用代码的人的双方的痛处
        // 解放我们的双手
      }

}
