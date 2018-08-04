package com.lcydream.project.designmode.factory.abstractfactory;

import com.lcydream.project.designmode.factory.Car;

/**
 * AbstractFactory
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 22:10
 */
public abstract class AbstractFactory {

    protected abstract Car getCar();

    /**
     * 动态配置代码
     * 固定模式的委派模式
     * @param carName
     * @return
     */
    public Car getCar(String carName){
        if("BMW".equalsIgnoreCase(carName)){
            return new BmwFactory().getCar();
        }else if("Benz".toLowerCase().equalsIgnoreCase(carName)){
            return new BenzFactory().getCar();
        }else if("Audi".equalsIgnoreCase(carName)){
            return new AudiFactory().getCar();
        }else {
            System.out.println("产品不存在");
            return null;
        }
    }
}
