package com.lcydream.project.mybatis.my;


import com.lcydream.project.mybatis.beans.Test;

public interface TestMapper {
    Test selectByPrimaryKey(Integer userId);
}