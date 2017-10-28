package com.ltchen.java.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @file : MySerializableThree.java
 * @date : 2017年7月4日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : Externalizable的替代方法:当类实现了Serializable接口,并添加writeObject(),readObject()时(方法签名必须相同),
 * 在序列化和反序列化时会调用,在方法内部可调用ObjectOutputStrea的defaultWriteObject(),ObjectInputStream.defaultReadObject()
 * 方法存储非transient字段.transient字段手动序列化和反序列化时,也能被存储和恢复.
 */
public class MySerializableThree implements Serializable{

	private static final long serialVersionUID = 4696106544226739292L;
	
	private String a;
	private transient String b;
	
	public MySerializableThree(String a, String b) {
		this.a = "Not Transient: " + a;
		this.b = "Transient: " + b;
	}

	@Override
	public String toString() {
		return "MySerializableThree [a=" + a + ", b=" + b + "]";
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		//序列化非transient态字段
		out.defaultWriteObject();
		//可注释掉观察
		out.writeObject(b);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		//反序列化非transient态字段
		in.defaultReadObject();
		//可注释掉观察
		b = (String) in.readObject();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		MySerializableThree mst = new MySerializableThree("testA", "testB");
		
		System.out.println("Before:\n" + mst);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(mst);
		
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
		MySerializableThree mst2 = (MySerializableThree) ois.readObject();
		System.out.println("After:\n" + mst2);
	}
}
