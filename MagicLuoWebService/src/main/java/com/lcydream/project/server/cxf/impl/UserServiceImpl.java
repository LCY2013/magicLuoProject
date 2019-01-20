package com.lcydream.project.server.cxf.impl;

import com.lcydream.project.server.cxf.dao.CacheDB;
import com.lcydream.project.server.cxf.entity.User;
import com.lcydream.project.server.cxf.response.ResponseResult;
import com.lcydream.project.server.cxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author Luo Chun Yun
 * @date 2018/8/25 20:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CacheDB cacheDB;

    @Override
    public ResponseResult getUserList() {
        return new ResponseResult(1,"成功",cacheDB.getUserList());
    }

    /**
     * 查询用户
     * @param id {@link int}用户id
     * @return ResponseResult
     */
    @Override
    public ResponseResult getUserById(int id) {
        return new ResponseResult(1,"成功",cacheDB.getUserById(id));
    }

    @Override
    public ResponseResult addUser(User user) {
        return new ResponseResult(1,"成功",cacheDB.addUser(user));
    }

    @Override
    public ResponseResult updateUser(User user) {
        return new ResponseResult(1,"成功",cacheDB.updateUser(user));
    }

    /**
     *  用户id
     * @param id 用户id
     * @return ResponseResult
     */
    @Override
    public ResponseResult deleteUserById(int id) {
        return new ResponseResult(1,"成功",cacheDB.deleteUserById(id));
    }

    @Override
    public ResponseResult index() {
        return new ResponseResult(1,"成功","欢迎使用magicLuo服务");
    }
}
