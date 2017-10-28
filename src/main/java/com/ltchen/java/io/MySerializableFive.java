package com.ltchen.java.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @file : MySerializableFive.java
 * @date : 2017年7月5日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 看不懂...
 */
public class MySerializableFive {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		List<Class<? extends Shape>> shapeTypes = new ArrayList<Class<? extends Shape>>();
		shapeTypes.add(Circle.class);
		shapeTypes.add(Square.class);
		shapeTypes.add(Line.class);
		
		List<Shape> shapes = new ArrayList<Shape>();
		//创建一些shape
		for (int i = 0; i < 10; i++) {
			shapes.add(Shape.randomFactory());
		}
		//给所有的shape颜色设置为green
		for (int i = 0; i < 10; i++) {
			((Shape)shapes.get(i)).setColor(Shape.GREEN);
		}
		//序列化
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pom.out"));
		out.writeObject(shapeTypes);
		//将line的static域序列化
		Line.serializeStaticState(out);
		out.writeObject(shapes);
		System.out.println(shapes);
		
		//反序列化
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("pom.out"));
		List<Class<? extends Shape>> shapeTypes2 = (List<Class<? extends Shape>>)in.readObject();
		Line.deSerializeStaticState(in);
		List<Shape> shapes2 = (List<Shape>) in.readObject();
		System.out.println(shapes2);
	}
}

abstract class Shape implements Serializable{
	public static final int RED = 1, BLUE = 2, GREEN = 3;
	private int xPos, yPos, dimension;
	private static Random random = new Random(47); 
	private static int counter = 0;
	
	public abstract void setColor(int newColor);
	
	public abstract int getColor();
	
	public Shape(int xPos, int yPos, int dimension) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.dimension = dimension;
	}

	@Override
	public String toString() {
		return getClass() + "[color=" + this.getColor() +",xPos=" + xPos + ", yPos=" + yPos + ", dimension=" + dimension + "]\n";
	}
	
	public static Shape randomFactory(){
		int xPos = random.nextInt(100);
		int yPos = random.nextInt(100);
		int dimension = random.nextInt(100);
		switch (counter++ % 3) {
		default:
		case 0:
			return new Circle(xPos, yPos, dimension);
		case 1:
			return new Square(xPos, yPos, dimension);
		case 2:
			return new Line(xPos, yPos, dimension);
		}
	}
}

class Circle extends Shape{
	private static int color = RED;
	
	public Circle(int xPos, int yPos, int dimension) {
		super(xPos, yPos, dimension);
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void setColor(int color) {
		Circle.color = color;
	}
}

class Square extends Shape{
	private static int color;
	
	public Square(int xPos, int yPos, int dimension) {
		super(xPos, yPos, dimension);
		color = RED;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void setColor(int color) {
		Square.color = color;
	}
}

class Line extends Shape{
	private static int color = RED;
	
	public static void serializeStaticState(ObjectOutputStream out) throws IOException{
		out.writeInt(color);
	}
	public static void deSerializeStaticState(ObjectInputStream in) throws IOException, ClassNotFoundException{
		color = in.readInt();
	}
	public Line(int xPos, int yPos, int dimension) {
		super(xPos, yPos, dimension);
	}
	
	@Override
	public int getColor() {
		return color;
	}
	
	@Override
	public void setColor(int color) {
		Line.color = color;
	}

}