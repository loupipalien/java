package com.ltchen.java.lang;

/**
 * @file : FamilyVsExactType.java
 * @date : 2017年7月12日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : instanceof与isInstance()生成的结果完全相同,并考虑了继承;
 * ==与equals()生成的结果相同,不考虑继承,只表示一个明确的类型
 */
public class FamilyVsExactType {

	public static void test(Object x){
		System.out.println("Testing x of type:" + x.getClass());
		
		System.out.println("x instanceof Base:" + (x instanceof Base));
		System.out.println("x instanceof Derived:" + (x instanceof Derived));
		
		System.out.println("Base.class.isInstance(x):" + Base.class.isInstance(x));
		System.out.println("Derived.class.isInstance(x):" + Derived.class.isInstance(x));
		
		System.out.println("x.getClass() == Base.getClass():" + (x.getClass() == Base.class));
		System.out.println("x.getClass() == Derived.getClass():" + (x.getClass() == Derived.class));
		
		System.out.println("x.getClass().equals(Base.class):" + x.getClass().equals(Base.class));
		System.out.println("x.getClass().equals(Derived.class):" + x.getClass().equals(Derived.class));
	}
	
	public static void main(String[] args) {
		FamilyVsExactType.test(new Base());
		System.out.println("===========================================");
		FamilyVsExactType.test(new Derived());
	}
}

class Base{};
class Derived extends Base{};