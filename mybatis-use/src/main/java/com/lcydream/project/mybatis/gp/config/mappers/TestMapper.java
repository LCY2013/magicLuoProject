package com.lcydream.project.mybatis.gp.config.mappers;


import com.lcydream.project.mybatis.beans.Test;

public interface TestMapper {
    Test selectByPrimaryKey(Integer userId);
}