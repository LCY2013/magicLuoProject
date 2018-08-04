package com.lcydream.project.designmode.factory.abstractfactory;

import com.lcydream.project.designmode.factory.Audi;
import com.lcydream.project.designmode.factory.Car;

/**
 * AudiFactory
 *  具体的业务逻辑封装
 * @author Luo Chun Yun
 * @date 2018/6/22 21:59
 */
public class AudiFactory extends AbstractFactory {

    @Override
    public Car getCar() {
        return new Audi();
    }

}
