package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyThreadPriority.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 每个线程在执行时都具有一定的优先级，优先级高的线程具有较多的执行机会,而非优先执行。每个线程默认的优先级都与创建它的线程的优先级相同,main线程默认具有普通优先级。
 * 设置线程优先级：setPriority(int priorityLevel)。参数priorityLevel范围在1-10之间，常用的有如下三个静态常量值：
 * MAX_PRIORITY:10
 * NORM_PRIORITY:5
 * MIN_PRIORITY:1
 */
public class MyThreadPriority extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}

	public static void main(String[] args) {
		
		Thread thread = new MyThreadPriority();
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 20) {
				//设置线程优先级
				thread.setPriority(Thread.MAX_PRIORITY);
				thread.start();
			}
		}
	}

}
