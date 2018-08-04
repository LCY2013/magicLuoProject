package com.lcydream.project.designmode.factory.method;

/**
 * FactoryTest
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 22:03
 */
public class FactoryTest {

  public static void main(String[] args) {

      //工厂方法模式
      //各个产品的生产商，都有各自的工厂
      //生产工艺，生成的高科技程度都是不一样的
      Factory audifactory = new AudiFactory();
      System.out.println(audifactory.getCar());

      //需要用户关心，这个产品的生产商
      Factory factory = new BmwFactory();
      System.out.println(factory.getCar());

      //增加了代码的使用复杂度

      //抽象工厂模式
  }

}
