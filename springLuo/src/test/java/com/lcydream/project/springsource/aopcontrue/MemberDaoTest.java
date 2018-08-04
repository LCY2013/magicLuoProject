package com.lcydream.project.springsource.aopcontrue;

import com.lcydream.project.aopcomtrue.aop.dao.MemberDao;
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
public class MemberDaoTest {

	@Autowired
	MemberDao memberDao;

	@Test
	public void testInsert(){
		memberDao.insert();
	}

}
