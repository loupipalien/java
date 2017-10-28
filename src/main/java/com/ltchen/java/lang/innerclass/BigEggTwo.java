package com.ltchen.java.lang.innerclass;

/**
 * @file : BigEggTwo.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 导出类继承外围类后是否能覆盖父类中的内部类
 * 导出类中的同名内部类继承外部类.内部类则可以覆盖父类中的内部类
 */
public class BigEggTwo extends EggTwo{

	public class Yolk extends EggTwo.Yolk{
		public Yolk() {
			System.out.println("BigEggTwo.Yolk()");
		}
		public void f(){
			System.out.println("BigEggTwo.Yolk().f()");
		}
	}
	
	public BigEggTwo() {
		insertYolk(new Yolk());
	}
	
	public static void main(String[] args) {
		EggTwo et = new BigEggTwo();
		et.g();
	}
}

class EggTwo {

	private Yolk yolk = new Yolk();
	
	protected class Yolk{
		public Yolk() {
			System.out.println("EggTwo.Yolk()");
		}
		public void f(){
			System.out.println("EggTwo.Yolk().f()");
		}
	}
	
	public EggTwo(){
		System.out.println("new EggTwo()");
	}
	
	public void insertYolk(Yolk yolk){
		this.yolk = yolk;
	}
	
	public void g(){
		yolk.f();
	}
}