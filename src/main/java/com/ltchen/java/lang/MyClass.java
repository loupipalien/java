package com.ltchen.java.lang;

/**
 * @file : MyClass.java
 * @date : 2017年7月9日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : Class类
 */
public class MyClass {

	public static void printInfo(Class<?> clazz){
		System.out.println("ClassName:" + clazz.getName() + " is interface?:" + clazz.isInterface());
		System.out.println("SimpleName:" + clazz.getSimpleName());
		System.out.println("CanonicalName:" + clazz.getCanonicalName());
	}
	
	public static void main(String[] args) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName("com.ltchen.java.lang.FancyToy");
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find FancyToy.");
			System.exit(1);
		}
		MyClass.printInfo(clazz);
		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			MyClass.printInfo(interfaceClass);
		}
		Class<?> upClazz = clazz.getSuperclass();
		Object obj = null;
		try {
			MyClass.printInfo(upClazz);
			//实例化,必须有默认无参的构造函数
			obj = upClazz.newInstance();
		} catch (InstantiationException e) {
			System.out.println("Can't instantiate.");
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.out.println("Can't access.");
			System.exit(1);
		}
	}
}


interface HasBatteries{};
interface Waterproof{};
interface Shoots{};

class Toy{
	public Toy() {}
	
	public Toy(int i){}
}

class FancyToy extends Toy implements HasBatteries,Waterproof,Shoots{
	public FancyToy() {
		super(1);
	}
}
