package com.ltchen.java.lang.reflect;

/**
 * @file : InterfaceViolation.java
 * @date : 2017年7月15日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 虽然接口可降低构件之间的耦合,但并不能阻止程序员使用instanceof来探测到真正的实现类并使用,
 * 这时当服务端实现类改变了非接口中的方法,那么客户端程序员需要自己对此负责.多数情况下遵守这样的契约即可,但某些情况下可能并不够.
 * 开发时服务端提供api供客户端使用,当客户端没有服务端的实现类代码时,是可以避免这种情况的.
 */
public class InterfaceViolation {

	public static void main(String[] args) {
		InterfaceA  ia =  new InterfaceAImplB();
		ia.f();
		//会包编译错误
		//ia.g();
		System.out.println(ia.getClass().getName());
		if(ia instanceof InterfaceAImplB){
			InterfaceAImplB iain = (InterfaceAImplB)ia;
			iain.g();
		}
	}
}

class InterfaceAImplB implements InterfaceA{

	@Override
	public void f() {
	}

	public void g() {
	}
	
}
