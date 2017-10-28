package com.ltchen.java.javase.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyServiceClient {

	public static void main(String[] args) {
		MyService myService = null;
		
		//使用java.rmi.Naming
		try {
			/*
			 * 在RMI服务注册表中查找名称为MyService的对象
			 * 其中协议名可以省略//host:port/name
			 * 主机名以及端口也可省略,省略后默认时host默认时本机,端口默认时1099,即只在本机查找服务
			 */
			myService = (MyService) Naming.lookup("rmi://192.168.0.110:1099/MyService");
//			myService = (MyService) Naming.lookup("//192.168.0.110:1099/MyService");
//			myService = (MyService) Naming.lookup("MyService");
			//调用远程对象的方法 
			System.out.println(myService.sayHello("World")); 
		} catch (RemoteException e) {
			System.out.println("远程调用异常.");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("url格式异常.");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("未绑定异常.");
			e.printStackTrace();
		} 

		//使用javax.naming.Context
//		Context namingContext = null;
//		try {
//			namingContext = new InitialContext();
//			// 检索指定的对象。 即找到服务器端相对应的服务对象存根 
//			myService = (MyService) namingContext.lookup("rmi://192.168.0.110:1099/MyService");
//			System.out.println(myService.sayHello("ltchen"));
//		} catch (NamingException e) {
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		} 

	}

}
