package com.lcydream.project.designmode.proxy.custom;

import com.lcydream.project.designmode.proxy.jdk.MagicLuo;
import com.lcydream.project.designmode.proxy.jdk.Person;

/**
 * TestFindLove
 *
 * @author Luo Chun Yun
 * @date 2018/6/18 21:42
 */
public class TestFindLove {

  public static void main(String[] args) throws Exception{
      com.lcydream.project.designmode.proxy.jdk.Person obj = (Person)new LuoMatchMaker().getInstance(new MagicLuo());
      System.out.println("这里的这个代理对象是:"+obj.getClass()+"\n代理对象的名称是:"+obj.getClass().getName());
      obj.findLove();

  }
}
