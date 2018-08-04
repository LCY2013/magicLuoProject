package com.lcydream.project.designmode.prototype.simple;

/**
 * CloneTest
 *
 * @author Luo Chun Yun
 * @date 2018/6/24 17:16
 */
public class CloneTest {

	  public static void main(String[] args) {

	  	ConcretePrototype cp = new ConcretePrototype();
	  	cp.setAge(20);
	  	cp.setName("magicLuo");
	  	/*ArrayList list = new ArrayList();
	  	list.add("luo");*/
	  	cp.list.add("luo");
		  try {
			  ConcretePrototype copy = (ConcretePrototype) cp.clone();
              System.out.println(copy.list == cp.list);
			  System.out.println(copy.getAge()+":"+copy.getName()+":"+copy.list.get(0));
			  System.out.println(cp == copy);
		  } catch (CloneNotSupportedException e) {
			  e.printStackTrace();
		  }

		  /**
		   * 就是有一个现成的对象，这个对象里面有已经设置好的值
		   * 当我要新建一个对象的时候，并且要给新建的对象赋值，而且赋值内容要跟之前一模一样
		   *
		   * ConcretePrototype cp = new ConcretePrototype();
		   * cp.setAge(18);
		   *
		   * ConcretePrototype copy = new ConcretePrototype();
		   * copy.setAge(cp.getAge());
		   * copy.setName(cp.getName());
		   * 用循环，用反射，确实可以的（反射性能并不高）
		   * clone的原理是字节码复制newInstance()
		   *
		   * ORM的时候经常用到的
		   *
		   *
		   * 能够直接拷贝其实内容的数据类型/只支持9种：八大基本数据类型+String  浅拷贝
		   * 深拷贝 需要我们自己去将其他类型重新克隆
		   *
		   */

	  }
}
