package com.lcydream.project.designmode.template;

/**
 * Bevegrage
 * 冲饮料
 * @author Luo Chun Yun
 * @date 2018/6/24 21:12
 */
public abstract class Bevegrage {

	/**
	 * 不能被重写
	 */
	public final void create(){
		//1、把水
		boilWater();
		//2、把杯子准备好、原材料放到杯子中
		pourInCup();
		//3、用水冲泡
		brew();
		//4、添加辅料
		addCoundiments();
	}

	public abstract void pourInCup();

	public abstract void addCoundiments();

	public void brew(){
        System.out.println("用开水冲泡");
	}

	public void boilWater(){
        System.out.println("烧开水，烧到100度就可以起锅了");
	}
}
