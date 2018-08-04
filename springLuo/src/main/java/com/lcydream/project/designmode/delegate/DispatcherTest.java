package com.lcydream.project.designmode.delegate;

/**
 * DispatcherTest
 *
 * @author Luo Chun Yun
 * @date 2018/6/24 16:11
 */
public class DispatcherTest {

	  public static void main(String[] args) {
		  Dispatcher dispatcher = new Dispatcher(new ExectorMagicLuo());
		  /**
		   * 看上去是我们的项目经理在干活
		   * 实际干活的人是普通员工
		   * 这就是典型的，干活是我的，功劳是你的
		   */
		  dispatcher.doing();
	  }
}
