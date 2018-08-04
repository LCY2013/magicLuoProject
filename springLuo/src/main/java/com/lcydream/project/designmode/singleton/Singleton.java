package com.lcydream.project.designmode.singleton;

/**
 * @author LuoCY
 */
public class Singleton implements java.io.Serializable {   
	public static Singleton INSTANCE = new Singleton();   
	protected Singleton() {  }   
	private Object readResolve() {   
		return INSTANCE;   
	}
}
