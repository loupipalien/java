package com.ltchen.java.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * @file : MySerializableOne.java
 * @date : 2017年7月4日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 简单的序列化及反序列化过程,对象完全存储为二进制,反序列化时不需要用到构造器
 */
public class MySerializableOne implements Serializable{

	private static final long serialVersionUID = -8896490795204750574L;
	
	private static Random random = new Random(47);
	private Data[] datas = {new Data(random.nextInt(10)),new Data(random.nextInt(10)),new Data(random.nextInt(10))};
	
	private MySerializableOne next = null;
	private char c;
	
	public MySerializableOne(){
		System.out.println("Default constructor");
	}
	
	public MySerializableOne(int i,char c){
		System.out.println("MySerializableOne constructor: " + i);
		this.c = c;
		if(--i > 0){
			next = new MySerializableOne(i, (char)(c+1));
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("");
		sb.append(c);
		sb.append("(");
		for (Data data : datas) {
			sb.append(data);
		}
		sb.append(")");
		if(next != null){
			sb.append(next);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		MySerializableOne mySerializableOne = new MySerializableOne(6, 'a');
		System.out.println("mySerializableOne:" + mySerializableOne);
		//序列化到文件
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pom.out"));
		oos.writeObject("Worm storage\n");
		oos.writeObject(mySerializableOne);
		oos.close();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pom.out"));
		String str = (String) ois.readObject();
		MySerializableOne mySerializableOne2 = (MySerializableOne) ois.readObject();
		ois.close();
		System.out.println("str:" + str + "mySerializableOne2:" + mySerializableOne2);
		//序列化到字节数组
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos2 = new ObjectOutputStream(baos);
		oos2.writeObject("Worm storage\n");
		oos2.writeObject(mySerializableOne);
		oos2.close();
		ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
		String str2 = (String) ois2.readObject();
		MySerializableOne mySerializableOne3 = (MySerializableOne) ois2.readObject();
		ois2.close();
		System.out.println("str2:" + str2 + "mySerializableOne3:" + mySerializableOne3);
		
	}
}

class Data implements Serializable{

	private static final long serialVersionUID = 6031206578947535350L;
	
	private int id;
	
	public Data(int id){
		this.id = id;
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}
}