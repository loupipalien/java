package com.ltchen.java.javase.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyServiceServer {

	public static void main(String[] args) {
		MyService myService = null;
		
		//使用java.rmi.Naming
		try {
			//创建一个远程MyService对象 
			myService = new MyServiceImpl();
			//创建本地主机上的远程对象注册表Registry的实例,(Java默认端口是1099),缺少注册表创建,则无法绑定对象到远程注册表上 
			//也可不在此处创建注册,而是使用${JAVA_HOME}/bin中自带的rmiregistry创建注册
			LocateRegistry.createRegistry(1099);
			/*
			 * 把远程对象注册到RMI注册服务器上，并命名为MyService,绑定的URL标准格式为：rmi://host:port/name
			 * 其中协议名可以省略//host:port/name
			 * 主机名以及端口也可省略,省略后默认时host默认时本机,端口默认时1099
			 */
			Naming.bind("rmi://192.168.0.110:1099/MyService",myService);
//			Naming.bind("//192.168.0.110:1099/MyService",myService); 
//			Naming.bind("MyService",myService);
		} catch (RemoteException e) {
			System.out.println("远程调用异常.");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("url格式异常.");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.out.println("重复绑定异常.");
			e.printStackTrace();
		} 
		
		//使用javax.naming.Context
//		try {
//			//创建一个远程MyService对象 
//			myService = new MyServiceImpl();
//			//创建本地主机上的远程对象注册表Registry的实例,(Java默认端口是1099),缺少注册表创建,则无法绑定对象到远程注册表上 
//			LocateRegistry.createRegistry(1099);
//			//初始化命名空间 
//			Context namingContext;
//			namingContext = new InitialContext();
//			//将名称绑定到对象,即向命名空间注册已经实例化的远程服务对象 
//			namingContext.bind("rmi://localhost:1099/MyService", myService); 
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		} catch (NamingException e) {
//			e.printStackTrace();
//		} 
		
	    System.out.println(">>>INFO:远程MyService对象绑定成功！"); 
	}
	
}
