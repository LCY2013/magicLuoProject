package com.lcydream.project.springsource.factorymethod;

/**
 * StaticMagiLuoFactory
 * factory-method 可用于标识静态工厂的工厂方法（工厂方法是静态的）
 * 非静态工厂，需要使用 factory-bean 和 factory-method 两个属性配合
 * @author Luo Chun Yun
 * @date 2018/7/3 22:12
 */
public class StaticMagiLuoFactory {

	public static MagicLuo getMagicLuo(){
		MagicLuo magicLuo = new MagicLuo();
		magicLuo.setContent("create by MagicFactory");
		return magicLuo;
	}
}
