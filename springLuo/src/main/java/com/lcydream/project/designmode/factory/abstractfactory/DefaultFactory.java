package com.lcydream.project.designmode.factory.abstractfactory;

import com.lcydream.project.designmode.factory.Car;

/**
 * DefaultFactory
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 22:16
 */
public class DefaultFactory extends AbstractFactory{

    private AudiFactory defaultAudiFactory = new AudiFactory();

    @Override
    public Car getCar() {
        return defaultAudiFactory.getCar();
    }

}
