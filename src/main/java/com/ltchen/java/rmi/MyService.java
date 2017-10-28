package com.ltchen.java.javase.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyService extends Remote {

	String sayHello(String name) throws RemoteException;
}
