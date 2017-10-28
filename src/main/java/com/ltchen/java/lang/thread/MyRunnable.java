package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyRunnable.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 实现Runnable接口，并重写该接口的run()方法，该run()方法同样是线程执行体，创建Runnable实现类的实例，并以此实例作为Thread类的target来创建Thread对象，该Thread对象才是真正的线程对象。
 */
public class MyRunnable implements Runnable{

	private int i = 0;

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
            	// 创建一个Runnable实现类的对象
                Runnable runnable = new MyRunnable(); 
                // 将myRunnable作为Thread target创建新的线程
                Thread thread0 = new Thread(runnable);
                Thread thread1 = new Thread(runnable);
                // 调用start()方法使得线程进入就绪状态
                thread0.start(); 
                thread1.start();
            }
        }
    }
}
