package com.lcydream.project.springsource.lookupmethod;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * LookupmethodTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/3 22:33
 */
public class LookupmethodTest {

	/**
	 * newsProvider.getNews() 方法两次返回的news对象都是一样的
	 */
	@Test
	public void testLookupmethod(){
		String configLocation = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
		NewsProvider newsProvider = (NewsProvider)applicationContext.getBean("newsProvider");
        System.out.println(newsProvider.getNews());
        System.out.println(newsProvider.getNews());
        System.out.println(newsProvider);
	}

	/**
	 * 两次获取的 news 并就不是同一个 bean
	 */
	@Test
	public void testLookupmethodAware(){
		String configLocation = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
		NewsProviderAware newsProviderAware = (NewsProviderAware)applicationContext.getBean("newsProviderAware");
		System.out.println(newsProviderAware.getNews());
		System.out.println(newsProviderAware.getNews());
		System.out.println(newsProviderAware);
	}

	/**
	 *new1 和 new2 指向了不同的对象
	 * NewsProvider 被 CGLIB 增强
	 */
	@Test
	public void testLookupmethodLookupMethod(){
		String configLocation = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
		NewsProvider newsProvider = (NewsProvider)applicationContext.getBean("newsProviderLookupMethod");
		System.out.println(newsProvider.getNews());
		System.out.println(newsProvider.getNews());
		System.out.println(newsProvider);
	}
}
