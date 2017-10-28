package com.ltchen.java.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @file : MySerializableTwo.java
 * @date : 2017年7月4日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : transient关键字可避免域在实现Serializable接口时序列化,可避免将一些敏感信息保存到磁盘
 * 因为Externalizable默认不保存任何域,通常transient关键字配合Serializable使用
 */
public class MySerializableTwo implements Serializable{

	private static final long serialVersionUID = -4286876530605150359L;

	private Date date = new Date();
	private String username;
	//避免域序列化
	private transient String password;
	
	public MySerializableTwo(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "MySerializableTwo [date=" + date + ", username=" + username + ", password=" + password + "]";
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		MySerializableTwo mst =  new MySerializableTwo("ltchen", "123456");
		System.out.println("mst:" + mst);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pom.out"));
		oos.writeObject(mst);
		oos.close();
		Thread.sleep(1000);
		System.out.println("Recovering objects at " + new Date());
		ObjectInputStream ois =  new ObjectInputStream(new FileInputStream("pom.out"));
		mst = (MySerializableTwo) ois.readObject();
		ois.close();
		System.out.println("mst:" + mst);
	}
}
