package com.lcydream.project.aopcomtrue.aop.service;

import com.lcydream.project.jdbctemplate.demo.dao.MemberDao;
import com.lcydream.project.jdbctemplate.demo.entity.Member;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MemberManagerService
 *
 * @author Luo Chun Yun
 * @date 2018/7/6 21:40
 */
@Service
public class MemberManagerService {

	private final static Logger LOG = Logger.getLogger(MemberManagerService.class);
	@Autowired
	private MemberDao memberDao;

	/**
	 *
	 * @param member 成员
	 * @return 添加
	 */
	public boolean add(Member member){
		LOG.error("增加用户");
		try {
			memberDao.insterOne(member);
			//return true;
			throw new RuntimeException("自定义异常");
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("自定义异常");
		}
	}

	public boolean remove(Member member) throws Exception{
		LOG.info("删除用户");
		memberDao.deleteOne(member);
		//throw new Exception("自定义异常");
		return true;
	}

	public Member modify(Member member){
		LOG.info("修改用户");
		return new Member();
	}

	public final boolean query(Member member){
		LOG.info("查询用户");
		return true;
	}

}
