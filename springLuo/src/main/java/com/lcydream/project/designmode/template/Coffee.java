package com.lcydream.project.designmode.template;

/**
 * Coffee
 *  咖啡
 * @author Luo Chun Yun
 * @date 2018/6/24 21:18
 */
public class Coffee extends Bevegrage {

	/**
	 * 原材料放到杯子中
	 */
	@Override
	public void pourInCup() {
        System.out.println("将咖啡导入到杯子中进行冲泡");
	}

	/**
	 * 放辅料
	 */
	@Override
	public void addCoundiments() {
        System.out.println("添加牛奶和糖");
	}

}
