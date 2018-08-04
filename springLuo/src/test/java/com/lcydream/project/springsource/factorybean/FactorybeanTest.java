package com.lcydream.project.springsource.factorybean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FactorybeanTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/2 20:05
 */
public class FactorybeanTest {

	@Test
	public void testFactorybeanTest(){
		String configLocation = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        System.out.println("magicLuoFactory->"+applicationContext.getBean("magicLuoFactoryBean"));
        System.out.println("&magicLuoFactory->"+applicationContext.getBean("&magicLuoFactoryBean"));
	}
}
