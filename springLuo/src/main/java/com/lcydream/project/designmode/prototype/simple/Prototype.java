package com.lcydream.project.designmode.prototype.simple;

import java.util.ArrayList;

/**
 * Prototype
 *
 * @author Luo Chun Yun
 * @date 2018/6/24 17:14
 */
public class Prototype implements Cloneable {

	public ArrayList list = new ArrayList();

	@Override
	protected Object clone() throws CloneNotSupportedException {

		Prototype prototype = null;
		try{
			prototype = (Prototype)super.clone();
			prototype.list = (ArrayList) list.clone();

			//克隆是基于字节码的
			//用反射，或者循环
		}catch (Exception e){
			e.printStackTrace();
		}

		return prototype;
	}

}
