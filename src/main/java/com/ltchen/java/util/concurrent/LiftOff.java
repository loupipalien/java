package com.ltchen.java.util.concurrent;

public class LiftOff implements Runnable {

	//默认倒数10
	private int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;

	public LiftOff() {}
	
	public LiftOff(int countDown){
		this.countDown = countDown;
	}
	
	public String status(){
		return String.format("#%s(%s).%s",id,countDown > 0 ? countDown : "LiftOff!","\n");
	}
	@Override
	public void run() {
		while(countDown-- > 0){
			String message = status();
			System.out.print(message);
			Thread.yield();
		}
	}

}
