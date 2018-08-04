package com.lcydream.project.designmode.factory.simple;

import com.lcydream.project.designmode.factory.Car;

/**
 * SimpleFactoryTest
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 21:16
 */
public class SimpleFactoryTest {

  public static void main(String[] args) {
      //这里是我们的消费者
      Car car = new SimpleFactory().getCar("Benz");
      if(car != null){
        System.out.println(car.getName());
      }
  }
}
