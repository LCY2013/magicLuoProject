package com.lcydream.project.business.service.impl;

import com.lcydream.project.business.service.IMagicService;
import com.lcydream.project.business.service.IService;
import com.lcydream.project.framework.annotation.CustomAwtowired;
import com.lcydream.project.framework.annotation.CustomService;

/**
 * MagicServiceImpl
 *
 * @author Luo Chun Yun
 * @date 2018/7/18 21:42
 */
@CustomService("magicService")
public class MagicServiceImpl implements IMagicService {

  @CustomAwtowired private IService iService;

  @CustomAwtowired private ServiceImpl service;

  public IService getiService() {
    return iService;
  }

  public ServiceImpl getService() {
    return service;
  }

  @Override
  public void sayHello() {
    System.out.println("hello magic");
  }
}
