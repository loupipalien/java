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
 * @file : MyExternalizableOne.java
 * @date : 2017年7月4日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 实现Externalizable反序列化时,会调用readExternal()方法,这个方法会调用类的默认构造器
 */
public class MyExternalizableOne {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("Constructing objects:");
		ExternalizableOne eo = new ExternalizableOne();
		ExternalizableTwo et = new ExternalizableTwo();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pom.out"));
		System.out.println("Savng objects:");
		oos.writeObject(eo);
		oos.writeObject(et);
		oos.close();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pom.out"));
		System.out.println("Recovering eo:");
		eo = (ExternalizableOne) ois.readObject();
		System.out.println("Recovering et:");
		et = (ExternalizableTwo) ois.readObject();
		ois.close();
	}
	
}

class ExternalizableOne implements Externalizable{

	public ExternalizableOne() {
		System.out.println("ExternalizableOne Constructor");
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("ExternalizableOne.writeExternal");
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("ExternalizableOne.readExternal");
	}
}

class ExternalizableTwo implements Externalizable{

	//非public构造器,反序列化时会报java.io.InvalidClassException:no valid constructor
	ExternalizableTwo() {
		System.out.println("ExternalizableTwo Constructor");
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("ExternalizableTwo.writeExternal");
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("ExternalizableTwo.readExternal");
	}
}