package com.ltchen.java.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @file : MySerializableFour.java
 * @date : 2017年7月5日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 序列化对象时,对像中的引用也会被序列化到同一个流中,并在反序列化后恢复。
 * 重复将对象序列化到流中两次,写到同一个流反序列化后是地址相同;写到不同的流反序列化后则地址不同.
 */
public class MySerializableFour {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		House house = new House();
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(new Animal("Bosoc the dog", house));
		animals.add(new Animal("Ralph the hamster", house));
		animals.add(new Animal("Molly the cat", house));
		System.out.println("animals:" + animals);
		
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
		oos1.writeObject(animals);
		//写两遍
		oos1.writeObject(animals);
		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
		oos2.writeObject(animals);
		
		ObjectInputStream ois1 =  new ObjectInputStream(new ByteArrayInputStream(baos1.toByteArray()));
		ObjectInputStream ois2 =  new ObjectInputStream(new ByteArrayInputStream(baos2.toByteArray()));
		List<Animal> animals1 = (List<Animal>) ois1.readObject();
		List<Animal> animals2 = (List<Animal>) ois1.readObject();
		List<Animal> animals3 = (List<Animal>) ois2.readObject();
		System.out.println("animals1:" + animals1);
		System.out.println("animals2:" + animals2);
		System.out.println("animals3:" + animals3);
		ois1.close();
		ois2.close();
	}
	
}

class House implements Serializable{
	private static final long serialVersionUID = -4665467781276157662L;
}

class Animal implements Serializable{

	private static final long serialVersionUID = 7596879737583480212L;
	
	private String name;
	private House preferredHouse;
	
	public Animal(String name, House preferredHouse) {
		this.name = name;
		this.preferredHouse = preferredHouse;
	}

	@Override
	public String toString() {
		return "Animal [name=" + name + super.toString() +", preferredHouse=" + preferredHouse + "]";
	}
}