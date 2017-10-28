package com.ltchen.java.lang;

import java.util.Random;

/**
 * @file : InitializeClass.java
 * @date : 2017年7月9日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用类字面量和Class.forName()方法初始化class
 * JVM为了使用类做的准备工作包含了三个步骤
 * 1、加载：这是由类加载器执行的,该步骤通常在classpath中将查找字节码,并从字节码中创建一个Class对象
 * 2、链接：在链接阶段将验证类中的字节码,为静态域分配存储空间,并且如果必需的话,将解析这个类创建的对其他类的引用
 * 3、初始化：如果该类有超类(好像只有Object类例外),则对其初始化,执行初始化器和静态代码块
 * 初始化被延迟到了对静态方法(构造器是隐式的静态方法)或者非常数静态域进行首次引用才执行
 * 
 * 使用类字面量获取Class引用则可能不进行类的初始化
 * 使用Class.forName()方法获取Class引用则一定进行类的初始化
 */
public class InitializeClass {

	public static Random random = new Random(47);
	public static void main(String[] args) throws ClassNotFoundException {
		Class initableOne = InitableOne.class;
		System.out.println("After creating InitableOne ref.");
		//这是一个是编译期变量,不需要类初始化就可读取.不会触发Class对象初始化
		System.out.println(InitableOne.staticFinalOne);
		//将一个变量声明为static final并不能保证是编译期变量,对staticFinalTwo变量的访问将强制进行类初始化
		System.out.println(InitableOne.staticFinalTwo);
		//如果一个域是static但不是final,在被访问前总是要先进行链接和初始化.以下操作都会触发类初始化
		System.out.println(InitableTwo.staticNoFinalOne);
		Class initableThree = Class.forName("com.ltchen.java.lang.InitableThree");
		System.out.println("After creating InitableThree ref.");
		System.out.println(InitableThree.staticNoFinalOne);
	}
}

class InitableOne{
	public static final int staticFinalOne = 47;
	public static final int staticFinalTwo = InitializeClass.random.nextInt(1000);
	static{
		System.out.println("InitableOne.");
	}
}

class InitableTwo{
	public static int staticNoFinalOne = 147;
	static{
		System.out.println("InitableTwo.");
	}
}

class InitableThree{
	public static int staticNoFinalOne = 74;
	static{
		System.out.println("InitableThree.");
	}
}