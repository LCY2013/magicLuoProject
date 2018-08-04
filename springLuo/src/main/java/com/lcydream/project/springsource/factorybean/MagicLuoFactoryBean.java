package com.lcydream.project.springsource.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * MagicLuoFactoryBean
 * factoryBean是一种工厂bean，与普通bean不一样，FactoryBean可以生产bean的bean，
 * FactoryBean在Spring中是一个接口，用于定义
 * @author Luo Chun Yun
 * @date 2018/7/2 19:57
 */
public class MagicLuoFactoryBean implements FactoryBean<MagicLuo> {

	@Override
	public MagicLuo getObject() throws Exception {
		MagicLuo magicLuo = new MagicLuo();
		magicLuo.setContent("magicLuo");
		return magicLuo;
	}

	@Override
	public Class<?> getObjectType() {
		return MagicLuo.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
