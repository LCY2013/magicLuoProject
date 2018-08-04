package com.lcydream.project.designmode.template;

/**
 * Tea
 *
 * @author Luo Chun Yun
 * @date 2018/6/24 21:20
 */
public class Tea extends Bevegrage {

	/**
	 * 原材料放到杯子中
	 */
	@Override
	public void pourInCup() {
        System.out.println("杯子中放入茶叶进行冲泡");
	}

	/**
	 * 放辅料
	 */
	@Override
	public void addCoundiments() {
        System.out.println("添加蜂蜜");
	}
}
