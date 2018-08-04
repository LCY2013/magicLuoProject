package com.lcydream.project.designmode.factory.method;

import com.lcydream.project.designmode.factory.Car;

/**
 * Factory  工厂方法模式
 *  工厂接口，定义了所以工厂的执行标准
 * @author Luo Chun Yun
 * @date 2018/6/22 21:57
 */
public interface Factory {

    /**
     * 符合上路标准
     * 尾气排放标准
     * 电子设备安全系数
     * 必须配备安全带，安全气囊
     * 轮胎耐磨程度
     * @return
     */
    Car getCar();

}
