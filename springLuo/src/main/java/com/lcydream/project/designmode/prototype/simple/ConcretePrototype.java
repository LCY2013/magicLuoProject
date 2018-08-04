package com.lcydream.project.designmode.prototype.simple;

/**
 * ConcretePrototype
 *
 * @author Luo Chun Yun
 * @date 2018/6/24 17:15
 */
public class ConcretePrototype extends Prototype {

	//如果存在上百个属性

	private int age;

	private String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
