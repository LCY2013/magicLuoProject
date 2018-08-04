package com.lcydream.project.springsource.aopcontrue;

import com.lcydream.project.aopcomtrue.aop.service.MemberManagerService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * AopTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/6 22:14
 */
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberManagerServiceTest {

	@Autowired
	MemberManagerService memberManagerService;

	@Test
	@Ignore
	public void testAdd(){
		memberManagerService.add(null);
	}

	//做事物代理的时候
	//TracationManage来管理事物操作（切面）
	//DataSource,SessionFactory(DataSource)
	//DataSource,包含了连接信息，事物的提交或者回滚一些基础功能
	//通过连接点过去到方法（切点）具体操作那个DataSource
	//通过切面通知类型，去执行DataSource的功能方法
	//完全裸露，一丝不挂
	@Test
	public void testRemove(){
		try{
			memberManagerService.remove(null);
		}catch (Exception e){
			//e.printStackTrace();
		}
	}

	@Test
	public void testModify(){
		memberManagerService.modify(null);
	}

	@Test
	public void testQuery(){
		memberManagerService.query(null);
	}
}
