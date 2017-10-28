package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyThreadAndMyRunnable.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : MyThread重写Thread类中的run方法，并创建带参(Runnable)的构造函数
 * ********Thread类中的run()方法实现**********
 * @Override
 *  public void run() {
 *       if (target != null) {
 *           target.run();
 *       }
 *   }
 * ******************************************
 * 当执行到Thread类中的run()方法时，会首先判断target是否存在，存在则执行target中的run()方法，也就是实现了Runnable接口并重写了run()方法的类中的run()方法。
 * 但是由于多态的存在，根本就没有执行到Thread类中的run()方法，而是直接先执行了运行时类型即MyThread类中的run()方法。
 */
public class MyThreadAndMyRunnable {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 30) {
				Runnable runnable = new MyRunnable();
				Thread thread = new MyThread(runnable);
				thread.start();
			}
		}
	}
}

