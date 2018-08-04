package com.lcydream.project.designmode.prototype.greatestsage;

import java.io.Serializable;

/**
 * GoldRingedStaff
 *   金箍棒
 * @author Luo Chun Yun
 * @date 2018/6/24 20:21
 */
public class GoldRingedStaff implements Serializable {

	/**
	 * 长度
	 */
	private float height = 100;

	/**
	 * 直径
	 */
	private float diameter = 10;

	/**
	 * 金箍棒长大
	 */
	public void grow(){
		this.height *= 2;
		this.diameter *= 2;
	}

	/**
	 * 金箍棒缩小
	 */
	public void shrink(){
		this.height /= 2;
		this.diameter /= 2;
	}
}
