package com.lcydream.project.dal.persistence;

import com.lcydream.project.dal.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author LuoCY
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserInfo(@Param("username") String username,
                          @Param("password") String password);
}
