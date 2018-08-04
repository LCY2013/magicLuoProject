package com.lcydream.project.aopcomtrue.aop.dao;

import org.springframework.stereotype.Repository;

/**
 * MemberDao
 *
 * @author Luo Chun Yun
 * @date 2018/7/7 22:13
 */
@Repository
public class MemberDao {

	public void insert(){
        System.out.println("插入用户");
	}
}
