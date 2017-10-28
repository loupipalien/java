package com.ltchen.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * @file : AnonymousImplementation.java
 * @date : 2017年7月15日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 即使将接口实现为一个匿名实现类,仍然可以通过反射调用
 */
public class AnonymousImplementation {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		InterfaceA ia = new InterfaceAAnonymousImpl().makeInterfaceA();
		ia.f();
		System.out.println(ia.getClass().getName());
		HiddenImplementation.callHiddenMethod(ia,"g");
		//即使以下包域,保护域,私有域的方法也可访问
		HiddenImplementation.callHiddenMethod(ia,"u");
		HiddenImplementation.callHiddenMethod(ia,"v");
		HiddenImplementation.callHiddenMethod(ia,"w");
		
	}
}

class InterfaceAAnonymousImpl{
	public static InterfaceA makeInterfaceA(){
		return new InterfaceA(){
			@Override
			public void f() {
				System.out.println("public InterfaceAImplE.f()");
			}
			
			public void g(){
				System.out.println("public InterfaceAImplE.g()");
			}
			
			void u(){
				System.out.println("package InterfaceAImplE.u()");
			}
			
			protected void v(){
				System.out.println("protected InterfaceAImplE.v()");
			}
			
			private void w(){
				System.out.println("private InterfaceAImplE.w()");
			}
		};
	}
}