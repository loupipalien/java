package com.ltchen.java.lang.reflect;

/**
 * @file : SimpleProxy.java
 * @date : 2017年7月12日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用简单代理,关键在于封装修改,可以在代理对象中添加一些额外的操作,而不用修改真实对象
 */
public class SimpleProxy {

	public static void consumer(Interface iface){
		iface.doSomething();
		iface.doSomethingElse("hahaha");
	}
	
	public static void main(String[] args) {
		System.out.println("不使用代理:");
		SimpleProxy.consumer(new RealObject());
		System.out.println("使用代理:");
		SimpleProxy.consumer(new ProxyObject(new RealObject()));
	}
}

interface Interface{
	void doSomething();
	void doSomethingElse(String str);
}

class RealObject implements Interface{

	@Override
	public void doSomething() {
		System.out.println("doSomething");
	}

	@Override
	public void doSomethingElse(String str) {
		System.out.println("doSomethingElse " + str);
	}
	
}

class ProxyObject implements Interface{

	private Interface proxied;
	
	public ProxyObject(Interface proxied) {
		this.proxied = proxied;
	}
	
	@Override
	public void doSomething() {
		System.out.println("ProxyObject doSomething");
		proxied.doSomething();
	}

	@Override
	public void doSomethingElse(String str) {
		System.out.println("ProxyObject doSomethingElse " + str);
		proxied.doSomethingElse(str);
	}
	
}