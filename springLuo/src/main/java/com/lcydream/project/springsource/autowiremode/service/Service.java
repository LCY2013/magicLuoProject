package com.lcydream.project.springsource.autowiremode.service;

import com.lcydream.project.springsource.autowiremode.MongoDao;
import com.lcydream.project.springsource.autowiremode.MySqlDao;

/**
 * Service
 *
 * @author Luo Chun Yun
 * @date 2018/7/2 19:29
 */
public class Service {

	private MySqlDao mysqlDao;
	private MongoDao mongoDao;

	public MySqlDao getMysqlDao() {
		return mysqlDao;
	}

	public void setMysqlDao(MySqlDao mysqlDao) {
		this.mysqlDao = mysqlDao;
	}

	public MongoDao getMongoDao() {
		return mongoDao;
	}

	public void setMongoDao(MongoDao mongoDao) {
		this.mongoDao = mongoDao;
	}

	@Override
	public String toString() {
		return super.toString()+"\n\t\t\t\t{"+
				"mysqlDao="+mysqlDao+
				"\r\nmongoDao="+mongoDao+"}";
	}
}
