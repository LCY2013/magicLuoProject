package com.lcydream.project.designmode.factory.method;

import com.lcydream.project.designmode.factory.Bmw;
import com.lcydream.project.designmode.factory.Car;

/**
 * BenzFactory
 *
 * @author Luo Chun Yun
 * @date 2018/6/22 22:00
 */
public class BmwFactory implements Factory {

    @Override
    public Car getCar() {
        return new Bmw();
    }
    
}
