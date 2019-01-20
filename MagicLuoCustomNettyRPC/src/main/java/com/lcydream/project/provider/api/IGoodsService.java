package com.lcydream.project.provider.api;

/**
 * CalculatorService
 *
 * @author Luo Chun Yun
 * @date 2018/11/11 20:00
 */
public interface IGoodsService {

    Object queryGoodsList(String name,String passWord);

    Object queryGoodsById(String id);

    Object queryGoodsByType(String id,String type);
}
