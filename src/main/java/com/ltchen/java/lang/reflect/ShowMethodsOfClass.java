package com.ltchen.java.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @file : ShowMethodsOfClass.java
 * @date : 2017年7月12日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 通过反射打印出某个类或者接口中定义的方法，反射出的方法中可以看到JVM默认创建的构造方法
 */
public class ShowMethodsOfClass {

	private static final String usage = 
			"usage:\n" + 
			"ShowMethodsOfClass qualified.class.name\n" +
			"To show all methods of class or:\n" +
			"ShowMethodsOfClass qualified.class.name keyword\n" +
			"To search for methods involving 'keyword'";
	//用于匹配全限定名前缀
	private static Pattern pattern = Pattern.compile("\\w+\\.");
	
	public static void showMethods(String qualifiedClassName,String keyword){
		if(qualifiedClassName == null || qualifiedClassName.equals("")){
			System.out.println(usage);
			System.exit(0);
		}
		
		int lines = 0;
		try {
			Class<?> clazz = Class.forName(qualifiedClassName);
			Method[] methods = clazz.getMethods();
			Constructor[] constructors = clazz.getConstructors();
			if(keyword == null || keyword.equals("")){
				for (Method method : methods) {
					System.out.println(pattern.matcher(method.toString()).replaceAll(""));
				}
				for (Constructor constructor : constructors) {
					System.out.println(pattern.matcher(constructor.toString()).replaceAll(""));
				}
				lines = methods.length + constructors.length;
			}
			else{
				for (Method method : methods) {
					if(method.toString().indexOf(keyword) != -1){
						System.out.println(pattern.matcher(method.toString()).replaceAll(""));
						lines++;
					}
				}
				for (Constructor constructor : constructors) {
					if(constructor.toString().indexOf(keyword) != -1){
						System.out.println(pattern.matcher(constructor.toString()).replaceAll(""));
						lines++;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ShowMethodsOfClass.showMethods("com.ltchen.java.lang.reflect.ShowMethodsOfClass",null);
		System.out.println("==================================");
		ShowMethodsOfClass.showMethods("java.util.List",null);
		System.out.println("==================================");
		ShowMethodsOfClass.showMethods("java.util.LinkedList","add");
	}
	
}
