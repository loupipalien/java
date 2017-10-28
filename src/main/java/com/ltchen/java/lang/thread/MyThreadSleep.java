package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyThreadSleep.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc :  让当前的正在执行的线程暂停指定的时间，并进入阻塞状态。在其睡眠的时间段内，该线程由于不是处于就绪状态，因此不会得到执行的机会。即使此时系统中没有任何其他可执行的线程，出于sleep()中的线程也不会执行。
 * 因此sleep()方法常用来暂停线程执行。当调用了新建的线程的start()方法后，线程进入到就绪状态，可能会在接下来的某个时间获取CPU时间片得以执行，
 * 如果希望这个新线程必然性的立即执行，直接调用Thread.sleep(1)使得正在执行的线程休眠，新线程得以执行。
 */
public class MyThreadSleep extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}

	public static void main(String[] args) {

		Thread thread = new MyThreadSleep();

		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 30) {
				thread.start();
				try {
					// 使得当前main线程休眠,thread线程得以能够马上得以执行
					Thread.sleep(1); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
