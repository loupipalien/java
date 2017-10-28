package com.ltchen.java.lang;

/**
 * @file : EvenIntGenerator.java
 * @date : 2017年5月29日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 偶数int生成器
 */
public class EvenIntGenerator extends IntGenerator {

	//共享资源
	private int currentValue = 0;
	
	@Override
	public int next() {
		//非原子性操作
		currentValue++;
		//由于线程之间竞争发生错误率较低,这里调用线程的yield方法增加线程竞争错误发生的概率
		Thread.yield();
		currentValue++;
		return currentValue;
	}

}
