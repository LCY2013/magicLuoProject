package com.lcydream.project.designmode.prototype.greatestsage;

import java.util.Date;

/**
 * Monkey
 *   猴子
 * @author Luo Chun Yun
 * @date 2018/6/24 20:16
 */
public class Monkey {

	/**
	 * 身高   基本数据类型
	 */
	private int height;

	/**
	 * 体重   基本数据类型
	 */
	private int weight;

	/**
	 * 生日  不是基本数据类型
	 */
	private Date birthday;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
