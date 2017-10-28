package com.ltchen.java.lang.reflect;

import java.lang.reflect.Field;

/**
 * @file : ModifyingPrivateFields.java
 * @date : 2017年7月15日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 似乎没有任何方法可以阻止反射访问非公共访问权限的方法,但当反射修改final域时,final域时安全的.
 * 运行时系统会在不抛出任何异常的情况下接受任何修改尝试,但实际上并不会发生任何修改
 */
public class ModifyingPrivateFields {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		WithPrivateFinalField wpff = new WithPrivateFinalField();
		System.out.println(wpff);
		
		Field field = wpff.getClass().getDeclaredField("i");
		field.setAccessible(true);
		System.out.println("field.getInt(wpff):" + field.getInt(wpff));
		field.setInt(wpff, 47);
		System.out.println(wpff);
		
		field = wpff.getClass().getDeclaredField("i2");
		field.setAccessible(true);
		System.out.println("field.getInt(wpff):" + field.getInt(wpff));
		field.setInt(wpff, 74);
		System.out.println(wpff);
		
		field = wpff.getClass().getDeclaredField("str");
		field.setAccessible(true);
		System.out.println("field.get(wpff):" + field.get(wpff));
		field.set(wpff, "No, you're not!");                                                         
		System.out.println(wpff);
		
		field = wpff.getClass().getDeclaredField("str2");
		field.setAccessible(true);
		System.out.println("field.get(wpff):" + field.get(wpff));
		field.set(wpff, "No, you're not!");                                                         
		System.out.println(wpff);
	}
}

class WithPrivateFinalField{
	private int i = 1;
	private final int i2 = 2;
	private String str = "Am i safe.";
	private final String str2 = "I'm totally safe.";
	
	@Override
	public String toString() {
		return "WithPrivateFinalField [i=" + i + ", i2=" + i2 + ", str=" + str + ", str2=" + str2 + "]";
	}
	
}