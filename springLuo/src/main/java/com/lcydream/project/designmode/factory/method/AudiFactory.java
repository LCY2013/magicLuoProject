package com.lcydream.project.designmode.factory.method;

import com.lcydream.project.designmode.factory.Audi;
import com.lcydream.project.designmode.factory.Car;

/**
 * AudiFactory
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 21:59
 */
public class AudiFactory implements Factory {

    @Override
    public Car getCar() {
        return new Audi();
    }

}
