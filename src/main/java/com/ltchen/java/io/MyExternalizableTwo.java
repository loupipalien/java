package com.ltchen.java.io;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @file : MyExternalizableTwo.java
 * @date : 2017年7月4日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : Externalizable实现简单的序列化及反序列化,writeExternal(),readExternal()方法可定制对象如何序列化和反序列化
 */
public class MyExternalizableTwo implements Externalizable{

	private int i;
	private String str;
	
	public MyExternalizableTwo() {
		System.out.println("MyExternalizableTwo Default Constructor");
	}
	public MyExternalizableTwo(int i, String str) {
		System.out.println("MyExternalizableTwo Constructor");
		this.i = i;
		this.str = str;
	}
	
	@Override
	public String toString() {
		return "MyExternalizableTwo [i=" + i + ", str=" + str + "]";
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("MyExternalizableTwo.writeExternal");
		//序列化对象,
		out.writeInt(i);
		out.writeObject(str);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("MyExternalizableTwo.readExternal");
		//反序列化对象
		i = in.readInt();
		str = (String) in.readObject();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("Constructing objects");
		MyExternalizableTwo met =  new MyExternalizableTwo(47, "A String");
		System.out.println(met);
		System.out.println("Saving objects");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pom.out"));
		oos.writeObject(met);
		oos.close();
		System.out.println("Recovering objects");
		ObjectInputStream ois =  new ObjectInputStream(new FileInputStream("pom.out"));
		met = (MyExternalizableTwo) ois.readObject();
		ois.close();
		System.out.println(met);
	}

	
}
