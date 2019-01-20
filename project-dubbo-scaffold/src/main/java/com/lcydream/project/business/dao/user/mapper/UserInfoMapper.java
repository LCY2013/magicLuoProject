package com.lcydream.project.business.dao.user.mapper;

import com.lcydream.project.business.dao.user.model.UserInfo;
import com.lcydream.project.business.dao.user.model.UserInfoExample;
import com.lcydream.project.framework.mybatis.AutoMapperInteger;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper extends AutoMapperInteger<UserInfo> {
    int countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    List<UserInfo> selectByExample(UserInfoExample example);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * 查询记录条数
     * @param params
     * @return
     */
    int selectUserInfoPageCount(Map<String, Object> params);

    /**
     * 分页查询用户数据
     * @param params
     */
    List<UserInfo> selectUserInfoListByPage(Map<String, Object> params);



    int incrementUserCredit(Map<String, Object> params);

    /**
     * 获取积分排行前10的用户
     * @return
     */
    List<UserInfo> selectTop10User();
}