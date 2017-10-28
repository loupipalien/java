package com.ltchen.java.lang;

/**
 * @file : LoadClass.java
 * @date : 2017年7月9日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 类的Class对象何时被加载：当创建一个对类的静态成员引用时(这证明了构造器方法时静态方法)
 */
public class LoadClass {
	
	public static void main(String[] args) {
		System.out.println("main starting.");
		
		//静态方法
		new Candy();
		System.out.println("After new a Candy.");
		
		//静态变量
		Gum.i = 47;
		System.out.println("After Class.forname(\"Gum\").");
		
		//Class加载
		try {
			Class.forName("com.ltchen.java.lang.Cookie");
			System.out.println("After Class.forname(\"Cookie\").");
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't fing Cookie.");
		}
		
		System.out.println("main ending.");
	}
}

class Candy{
	static{
		System.out.println("Loading Candy.");
	}
}

class Gum{
	public static int i;
	static{
		System.out.println("Loading Gum.");
	}
}

class Cookie{
	static{
		System.out.println("Loading Cookie.");
	}
}
