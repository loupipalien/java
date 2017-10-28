package com.ltchen.java.lang.innerclass;

/**
 * @file : Event.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 事件类
 */
public abstract class Event {

	private long eventTime;
	protected final long delayTime;
	
	public Event(long delayTime) {
		this.delayTime = delayTime;
		start();
	}
	
	public void start(){
		eventTime = System.nanoTime() + delayTime;
	}
	
	public boolean ready(){
		return System.nanoTime() >= eventTime;
	}
	
	public abstract void action();
}
