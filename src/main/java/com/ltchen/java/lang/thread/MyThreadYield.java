package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyThreadYield.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : yield()方法还与线程优先级有关，当某个线程调用yiled()方法从运行状态转换到就绪状态后，CPU从就绪状态线程队列中会[建议]选择与该线程优先级相同或优先级更高的线程去执行。
 */
public class MyThreadYield extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}

	public static void main(String[] args) {

		Thread thread0 = new MyThreadYield();
		Thread thread1 = new MyThreadYield();
		//thread0设置为最大优先级
		thread0.setPriority(Thread.MAX_PRIORITY);
		//thread1设置为最小优先级
		thread1.setPriority(Thread.MIN_PRIORITY);
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 20) {
				thread0.start();
				thread1.start();
				Thread.yield();
			}
		}
	}

}
