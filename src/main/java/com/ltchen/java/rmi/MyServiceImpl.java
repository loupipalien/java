package com.ltchen.java.javase.rmi;

import java.rmi.RemoteException;
/*
 * UnicastRemoteObject用于导出的远程对象(骨架)和获得与该远程对象通信的存根。 
 * JDK8中不在需要使用${JAVA_HOME}/bin中自带的rimc生成骨架和存根
 * 警告: 为 JRMP生成和使用骨架及静态存根已过时。骨架不再必要, 而静态存根已由动态生成的存根取代。建议用户不再使用rmic来生成骨架和静态存根。
 */
import java.rmi.server.UnicastRemoteObject;

public class MyServiceImpl extends UnicastRemoteObject implements MyService {

	private static final long serialVersionUID = 9158978425508113867L;

	protected MyServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		String message = "Hello " + name;
		return message;
	}

}
