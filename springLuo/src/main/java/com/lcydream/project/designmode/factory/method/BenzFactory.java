package com.lcydream.project.designmode.factory.method;

import com.lcydream.project.designmode.factory.Benz;
import com.lcydream.project.designmode.factory.Car;

/**
 * BenzFactory
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 22:00
 */
public class BenzFactory implements Factory {

    @Override
    public Car getCar() {
        return new Benz();
    }

}
