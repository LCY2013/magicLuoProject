package com.lcydream.project.designmode.factory.simple;

import com.lcydream.project.designmode.factory.Audi;
import com.lcydream.project.designmode.factory.Benz;
import com.lcydream.project.designmode.factory.Bmw;
import com.lcydream.project.designmode.factory.Car;

/**
 * SimpleFactory  简单工厂模式
 * 工厂能力强
 * 为啥？
 * 这个工厂啥都能干(不符合现实)
 * 编码也是一种艺术(融会贯通)，艺术源于生活，回归于生活
 * @author Luo Chun Yun
 * @date 2018/6/22 21:15
 */
public class SimpleFactory {

    /**
     * 实现统一管理，专业化管理
     * 如果没有工厂模式，小作坊，没有执行标准
     * 如果买到三无产品(没有标准)
     * 卫生监督局工作难度会大大减轻
     * @param carName
     * @return
     */
    public Car getCar(String carName){
        if("BMW".equalsIgnoreCase(carName)){
            /*
                Spring中的工厂模式
                Bean
                BeanFactory（生成Bean）
                单例的bean
                被代理的Bean
                最原始的Bean（原型）
                List类型的Bean
                Map类型的Bean
                作用域不同的Bean

                getBean
                非常的紊乱，维护困难
                解耦（松耦合开发）
             */
            return new Bmw();
        }else if("Benz".equalsIgnoreCase(carName)){
            return new Benz();
        }else if("Audi".equalsIgnoreCase(carName)){
            return new Audi();
        }else {
            System.out.println("产品不存在");
            return null;
        }
    }
}
