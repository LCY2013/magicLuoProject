package com.lcydream.project.jdbctemplate.demo.dao;

import com.lcydream.project.jdbctemplate.demo.entity.Member;
import com.lcydream.project.jdbctemplate.framework.BaseDaoSupport;
import com.lcydream.project.jdbctemplate.framework.QueryRule;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Repository(value = "memberDaos")
public class MemberDao extends BaseDaoSupport<Member, Long> {

	@Override
	protected String getPKColumn() { return "id"; }

	@Resource(name="dataSource")
	@Override
	protected void setDataSource(DataSource dataSource) {
		super.setDataSourceReadOnly(dataSource);
		super.setDataSourceWrite(dataSource);
	}
	
	public List<Member> selectByName(String name) throws Exception{
		QueryRule queryRule =
		QueryRule.getInstance()
		.andEqual("name", name)
		.addAscOrder("name")
		.addAscOrder("id");
		
		return super.find(queryRule);
	}
	
	public List<Member> selectAll() throws Exception{
		return super.getAll();
	}
	
	public boolean insterOne(Member m) throws Exception{
		Long id = super.insertAndReturnId(m);
		m.setId(id);
		return id > 0;
	}
	
	public boolean updataOne(Member m) throws Exception{
		long count = super.update(m);
		return count > 0;
	}
	
	public boolean deleteOne(Member m) throws Exception{
		long count = super.delete(m);
		return count > 0;
	}
	
}
