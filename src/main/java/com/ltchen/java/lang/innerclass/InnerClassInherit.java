package com.ltchen.java.lang.innerclass;

/**
 * @file : InnerClassInherit.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 内部类继承
 * 因为内部类的构造器必须连接到外围类对象的引用,而在导出类中不存在默认的可连接对象,所以指向外围类对象的引用必须得初始化
 */
public class InnerClassInherit extends WithInner.Inner{

	//不能被编译
	//public InnerClassInherit() {}
	
	public InnerClassInherit(WithInner withInner) {
		withInner.super();
	}
}

class WithInner{
	class Inner{}
}