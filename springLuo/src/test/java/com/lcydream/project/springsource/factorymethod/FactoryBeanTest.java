package com.lcydream.project.springsource.factorymethod;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FactoryBeanTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/3 22:17
 */
public class FactoryBeanTest {

	@Test
	public void testFactoryBean(){
		String configLocation = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        System.out.println("staticMagicLuoFactory->"+applicationContext.getBean("staticMagiLuoFactory"));
	}

}
