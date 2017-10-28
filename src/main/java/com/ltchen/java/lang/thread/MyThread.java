package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyThread.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 继承Thread类，重写该类的run()方法
 */
public class MyThread extends Thread {

	private int i = 0;

	public MyThread(){}
	
	public MyThread(Runnable runnable) {
		super(runnable);
	}
	
	@Override
	public void run() {
		for (i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			Thread.yield();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 30) {
				// 创建一个新的线程 myThread1,此线程进入新建状态
				Thread thread0 = new MyThread(); 
				// 创建一个新的线程 myThread2,此线程进入新建状态	
				Thread thread1 = new MyThread(); 
				// 调用start()方法使得线程进入就绪状态
				thread0.start(); 
				// 调用start()方法使得线程进入就绪状态
				thread1.start(); 
			}
		}
	}


}
