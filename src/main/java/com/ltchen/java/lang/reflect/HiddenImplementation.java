package com.ltchen.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @file : HiddenImplementation.java
 * @date : 2017年7月15日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用包访问权限,其他包则不能访问.但依然能够使用反射做到调用
 */
public class HiddenImplementation {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		InterfaceA ia = new HiddenImplC().makeInterfaceA();
		ia.f();
		System.out.println(ia.getClass().getName());
		//由于在同一个包中还是可以访问,当在不同包中以下代码则会报编译错误
		if(ia instanceof InterfaceAImplC){
			InterfaceAImplC iaic = (InterfaceAImplC)ia;
			iaic.g();
		}
		callHiddenMethod(ia,"g");
		//即使以下包域,保护域,私有域的方法也可访问
		callHiddenMethod(ia,"u");
		callHiddenMethod(ia,"v");
		callHiddenMethod(ia,"w");
		
	}
	public static void callHiddenMethod(Object obj, String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method method = obj.getClass().getDeclaredMethod(methodName);
		method.setAccessible(true);
		method.invoke(obj);
	}
}
