package com.lcydream.project.designmode.template;

/**
 * TestTemplate
 *
 * @author Luo Chun Yun
 * @date 2018/6/24 21:21
 */
public class TestTemplate {

	  public static void main(String[] args) {

	  	  Coffee coffee = new Coffee();
	  	  coffee.create();
          System.out.println("----------------------");
	  	  Tea tea = new Tea();
	  	  tea.create();
	  }

	/**
	 * springJDBC采用的模板模式
	 * jdbc是java规范，各个数据库厂商自己去实现
	 * 1、加载驱动类DriverManager
	 * 2、建立连接
	 * 3、创建语句集（标准语句集，预处理语句集）（语句集？不确定，数据库不同Mysql，Oracle，SqlServer，Access）
	 * 4、执行语句集
	 * 5、结果集ResultSet 游标
	 * ORM（？）
	 */
}
