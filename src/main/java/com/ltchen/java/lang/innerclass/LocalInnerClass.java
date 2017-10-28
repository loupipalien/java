package com.ltchen.java.lang.innerclass;

/**
 * @file : LocalInnerClass.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 局部内部类和匿名内部类
 */
public class LocalInnerClass {

	private int count = 0;
	//局部内部类
	Counter getCounterOne(String name){
		class LocalCounter implements Counter{

			public LocalCounter() {
				System.out.println("LocalCounter()");
			}
			@Override
			public int next() {
				System.out.println(name);
				return count++;
			}
			
		}
		return new LocalCounter();
	}
	
	//匿名内部类
	Counter getCounterTwo(String name){
		return new Counter() {
			{
				System.out.println("AnonymousCounter()");
			}
			@Override
			public int next() {
				System.out.println(name);
				return count++;
			}
		};
	}
	
	public static void main(String[] args) {
		LocalInnerClass lic = new LocalInnerClass();
		Counter c1 = lic.getCounterOne("local inner.");
		Counter c2 = lic.getCounterTwo("anonymous inner.");
		for (int i = 0; i < 5; i++) {
			System.out.println(c1.next());
		}
		for (int i = 0; i < 5; i++) {
			System.out.println(c2.next());
		}
	}
	
}

interface Counter{
	int next();
}