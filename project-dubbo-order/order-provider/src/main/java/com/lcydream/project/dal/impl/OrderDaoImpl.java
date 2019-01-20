package com.lcydream.project.dal.impl;

import com.lcydream.project.dal.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * OrderDaoImpl
 *
 * @author Luo Chun Yun
 * @date 2018/9/22 17:49
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate orderDao;

    @Override
    public void insertOrder() {
        orderDao.execute("insert into `order`(status,price) values(1,10)");
    }

}
