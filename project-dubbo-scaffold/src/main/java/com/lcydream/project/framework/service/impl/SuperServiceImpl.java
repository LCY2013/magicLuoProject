package com.lcydream.project.framework.service.impl;


import com.lcydream.project.framework.mybatis.AutoMapper;

/**
 * <p>
 * 主键 Long 类型 IService 实现类（ 泛型：M 是 mapper 对象， T 是实体 ）
 * </p>
 * 
 * @author magicLuo
 */
public class SuperServiceImpl<M extends AutoMapper<T>, T> extends ServiceImpl<M, T, Long> {

}
