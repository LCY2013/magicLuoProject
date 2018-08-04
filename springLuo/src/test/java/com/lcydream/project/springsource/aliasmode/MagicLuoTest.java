package com.lcydream.project.springsource.aliasmode;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MagicLuoTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/2 18:28
 */
public class MagicLuoTest {

	/**
	 * 测试别名
	 */
	@Test
	public void testAlias(){
		String configLocation = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        System.out.println("luo---->"+applicationContext.getBean("luo"));
        System.out.println("luochunyun---->"+applicationContext.getBean("luochunyun"));
	}


}
