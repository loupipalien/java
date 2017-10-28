package com.ltchen.java.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @file : SimpleDynamicProxy.java
 * @date : 2017年7月12日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 动态代理:Proxy.newProxyInstance()动态生成一个代理对象需要传递三个参数,
 * 类加载器,希望生成的代理实现的接口列表,以及InvocationHandler的一个实现
 */
public class SimpleDynamicProxy {

	public static void consumer(Interface iface){
		iface.doSomething();
		iface.doSomethingElse("hahaha");
	}
	
	public static void main(String[] args) {
		System.out.println("不使用代理:");
		SimpleProxy.consumer(new RealObject());
		System.out.println("使用代理:");
		//Proxy.newProxyInstance()动态生成一个代理对象
		SimpleDynamicProxy.consumer((Interface) Proxy.newProxyInstance(Interface.class.getClassLoader(), new Class[]{Interface.class}, new DynamicProxyHandler(new RealObject())));
	}
}

class DynamicProxyHandler implements InvocationHandler{

	private Object proxied;
	
	public DynamicProxyHandler(Object proxied) {
		this.proxied = proxied;
	}  
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("proxy:" + proxy.getClass() + ",method:" + method + ",args:" + args);
		if(args != null){
			for (Object arg : args) {
				System.out.println(arg.toString());
			}
		}
		System.out.println("ProxyObject can do something...");
//		if(method.getName().equals("doSomethingElse")){
//			System.out.println("There is can do select methods...");
//		}
		//执行的是被代理的真正的对象
		return method.invoke(proxied, args);
	}
}