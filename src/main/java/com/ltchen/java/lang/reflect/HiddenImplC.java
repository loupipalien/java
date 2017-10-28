package com.ltchen.java.lang.reflect;

public class HiddenImplC {

	public static InterfaceA makeInterfaceA(){
		return new InterfaceAImplC();
	}
}

class InterfaceAImplC implements InterfaceA{

	@Override
	public void f() {
		System.out.println("public InterfaceAImplC.f()");
	}
	
	public void g(){
		System.out.println("public InterfaceAImplC.g()");
	}
	
	void u(){
		System.out.println("package InterfaceAImplC.u()");
	}
	
	protected void v(){
		System.out.println("protected InterfaceAImplC.v()");
	}
	
	private void w(){
		System.out.println("private InterfaceAImplC.w()");
	}
}