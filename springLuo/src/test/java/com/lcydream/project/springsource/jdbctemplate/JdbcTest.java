package com.lcydream.project.springsource.jdbctemplate;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/9 20:52
 */
public class JdbcTest {

	public static void main(String[] args) throws Exception{
		//原生的JDBC如何操作？

		/**
		 * 被封装成了DataSource，放入到了连接池
		 * 目的是为了提高程序响应速度
		 */
		//1、加载驱动类
		Class.forName("com.mysql.jdbc.Driver");


		//2、建立连接
		Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/magic?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");
		//3、创建语句集
		PreparedStatement preparedStatement = connection.prepareStatement("select * from member");
		//4、执行
		ResultSet resultSet = preparedStatement.executeQuery();

		/**
		 * 封装了，做成一个ORM的过程
		 * Object Relation Mapping 对象关系映射
		 * 自动变成一个我们显而易见的普通的自己写的java对象（实体类）
		 */
		int len = resultSet.getMetaData().getColumnCount();
		List list = new ArrayList();
		//5、获取结果集
		while(resultSet.next()){
			Class clazz = Member.class;
			Object obj = clazz.newInstance();
			for(int i=1;i<=len;i++){
				String columnName = resultSet.getMetaData().getColumnName(i);
				Field field = clazz.getDeclaredField(columnName);
				field.setAccessible(true);
				Object object = resultSet.getObject(i);
                //System.out.println(field.getType());
				if(object instanceof Integer){
					field.set(obj,resultSet.getInt(i));
				}else if(object instanceof String){
					field.set(obj,resultSet.getString(i));
				}
			}
			list.add(obj);
			//resultSet.getMetaData().getColumnName()
            //System.out.println(resultSet.getInt("id"));
            //System.out.println(resultSet.getString("name"));
            //System.out.println(JSON.toJSONString(obj));
		}
		System.out.println(JSON.toJSONString(list));
		resultSet.close();
		preparedStatement.close();
		connection.close();
	}

}
