package com.lcydream.project.dal.persistence;

import com.lcydream.project.dal.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author LuoCY
 */
public interface UserMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(@Param("record") User record);

    int insertSelective(@Param("record") User record);

    User selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(@Param("record") User record);

    int updateByPrimaryKey(@Param("record") User record);

    User selectByUserInfo(@Param("username") String username,
                          @Param("password") String password);
}
