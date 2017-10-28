package com.ltchen.java.lang.innerclass;

/**
 * @file : Callbacks.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 回调(老实讲,没看懂神奇之处)。
 * 但是可以看到当继承的类与接口的api有冲突时,可以使用内部类来实现接口,而不用改动,或者可以用于实现多重继承
 */
public class Callbacks {
	
	public static void main(String[] args) {
		CallbacksOne callbacksOne = new CallbacksOne();
		CallbacksTwo callbacksTwo = new CallbacksTwo();
		MyIncrement.f(callbacksTwo);
		Caller c1 = new Caller(callbacksOne);
		Caller c2 = new Caller(callbacksTwo.getCallbackReference());
		c1.go();
		c1.go();
		c2.go();
		c2.go();
	}
	
}

interface Incrementable{
	void increment();
}

class CallbacksOne implements Incrementable{
	private int i = 0;
	
	@Override
	public void increment() {
		i++;
		System.out.println(i);
	}
	
}

class MyIncrement{
	public void increment(){
		System.out.println("ohter operation.");
	}
	
	static void f(MyIncrement mi){
		mi.increment();
	}
}

class CallbacksTwo extends MyIncrement{
	private int i = 0;
	
	public void increment(){
		super.increment();
		i++;
		System.out.println(i);
	}
	
	private class Closure implements Incrementable{
		@Override
		public void increment() {
			//调用外围类的方法
			CallbacksTwo.this.increment();
		}
	}
	
	Incrementable getCallbackReference(){
		return new Closure();
	}
	
}

class Caller{
	private Incrementable callbackReference;
	
	Caller(Incrementable incrementable){
		this.callbackReference = incrementable;
	}
	void go(){
		callbackReference.increment();
	}
}
