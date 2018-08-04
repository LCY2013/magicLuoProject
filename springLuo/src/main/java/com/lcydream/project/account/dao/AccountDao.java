package com.lcydream.project.account.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * AcountDao
 *
 * @author Luo Chun Yun
 * @date 2018/7/29 15:38
 */
@Repository
public class AccountDao {

    private JdbcTemplate template;

    /**
     * 注入一个数据源
     * @param dataSource 数据源配置
     */
    @Resource(name="dataSource")
    private void setDataSource(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
    }

    /**
     * 查询账户余额
     * @param name 账户名称
     * @return 返回金额
     */
    public Double selectAccount(final String name){
        String sql = "select money from t_account where name = ?";
        Double money = template.queryForObject(sql, new Object[]{name}, Double.class);
        return money;
    }

    /**
     * 账户钱转出
     * @param out 转出账号
     * @param money 转出金额
     */
    public int updateForOut(final String out,final Double money){
        String sql = "update t_account set money = money-? where name = ? and money>=?";
        //String sql = "update t_account set money = money-? where name = ?";
        return template.update(sql,money,out,money);
    }

    /**
     * 账户钱转入
     * @param in 转入账号
     * @param money 转出金额
     */
    public int updateForIn(final String in,final Double money){
        String sql = "update t_account set money = money+? where name = ?";
        int update = template.update(sql, money, in);
        return update;
    }
}
