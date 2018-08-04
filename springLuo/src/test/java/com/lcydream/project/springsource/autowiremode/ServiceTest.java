package com.lcydream.project.springsource.autowiremode;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ServiceTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/2 19:44
 */
public class ServiceTest {

  /** 两种方式配置方式都能完成解决 bean 之间的依赖问题。
   * 只不过使用 autowire 会更加省力一些，配置文件也不会冗长
   * */
  @Test
  public void testAutowire() {
		String configLocation = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        System.out.println("service-without-autowire" + applicationContext.getBean("service-without-autowire"));
        System.out.println("service-with-autowire" + applicationContext.getBean("service-with-autowire"));
	}

}
