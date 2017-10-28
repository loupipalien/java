package com.ltchen.java.lang;

/**
 * @file : IntGenerator.java
 * @date : 2017年5月29日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 一个int生成器抽象类,包括了一个next抽象方法和一个cancel方法
 */
public abstract class IntGenerator {
	
	//volatile保证可视性
	private volatile boolean canceled = false;
	
	public boolean isCanceled(){
		return canceled;
	}
	
	//由于canceled类型视boolean,此方法视原子性的
	public void cancel(){
		canceled = true;
	}
	
	public abstract int next();
}
