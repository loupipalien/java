package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyThreadJoin.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 让一个线程等待另一个线程完成才继续执行。如A线程线程执行体中调用B线程的join()方法，则A线程被阻塞，知道B线程执行完为止，A才能得以继续执行。
 */
public class MyThreadJoin extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}

	public static void main(String[] args) {
		Thread thread = new MyThreadJoin();
	
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 30) {
				thread.start();
				try {
					// main线程需要等待thread线程执行完后才能继续执行
					thread.join(); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
