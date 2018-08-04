package com.lcydream.project.springsource.lookupmethod;

/**
 * NewsProvider
 *我们通过 BeanFactory getBean 方法获取 bean 实例时，对于 singleton 类型的 bean，
 * BeanFactory 每次返回的都是同一个 bean。对于 prototype 类型的 bean，BeanFactory
 * 则会返回一个新的 bean。现在考虑这样一种情况，一个 singleton 类型的 bean 中
 * 有一个 prototype 类型的成员变量。BeanFactory 在实例化 singleton 类型的 bean 时，
 * 会向其注入一个 prototype 类型的实例。但是 singleton 类型的 bean 只会实例化一次，
 * 那么它内部的 prototype 类型的成员变量也就不会再被改变。但如果我们每次从 singleton bean
 * 中获取这个 prototype 成员变量时，都想获取一个新的对象。这个时候怎么办？
 * 举个例子（该例子源于《Spring 揭秘》一书），我们有一个新闻提供类（NewsProvider），
 * 这个类中有一个新闻类（News）成员变量。我们每次调用 getNews 方法都想获取一条新的新闻。
 * 这里我们有两种方式实现这个需求，一种方式是让 NewsProvider 类实现
 * ApplicationContextAware 接口（实现 BeanFactoryAware 接口也是可以的），
 * 每次调用 NewsProvider 的 getNews 方法时，都从 ApplicationContext 中获取一个新的 News 实例，
 * 返回给调用者。第二种方式就是这里的 lookup-method 了，Spring 会在运行时对 NewsProvider 进行增强，
 * 使其 getNews 可以每次都返回一个新的实例
 * @author Luo Chun Yun
 * @date 2018/7/3 22:25
 */
public class NewsProvider {

	private News news;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
}
