package com.lcydream.project.designmode.delegate;

/**
 * Dispatcher
 *  项目经理
 * @author Luo Chun Yun
 * @date 2018/6/24 16:09
 */
public class Dispatcher implements IExector {

	IExector iExector;

	public Dispatcher(IExector iExector) {
		this.iExector = iExector;
	}

	/**
	 * 项目经理，虽然也有执行方法
	 * 但是他的工作职责是不一样的
	 */
	@Override
	public void doing() {
		this.iExector.doing();
	}

}
