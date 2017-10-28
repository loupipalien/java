package com.ltchen.java.lang.innerclass;

/**
 * @file : BigEggOne.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 导出类继承外围类后是否能覆盖父类中的内部类
 * 导出类中的同名内部类并不能覆盖父类中的内部类,因为两个内部类在不同的命名空间
 */
public class BigEggOne extends EggOne{

	public class Yolk{
		public Yolk() {
			System.out.println("BigEggOne.Yolk()");
		}
	}
	
	public static void main(String[] args) {
		new BigEggOne();
	}
}

class EggOne {

	private Yolk yolk;
	
	protected class Yolk{
		public Yolk() {
			System.out.println("EggOne.Yolk()");
		}
	}
	
	public EggOne(){
		System.out.println("new EggOne()");
		yolk = new Yolk();
	}
}
