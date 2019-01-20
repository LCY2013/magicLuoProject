package com.lcydream.project.business.services.support;


import com.lcydream.project.framework.mybatis.AutoMapper;
import com.lcydream.project.framework.service.impl.SuperServiceImpl;

public class BaseServiceImpl<M extends AutoMapper<T>, T> extends SuperServiceImpl<M,T> {

}
