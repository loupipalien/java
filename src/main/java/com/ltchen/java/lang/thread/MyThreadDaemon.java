package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyThreadDaemon.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 后台线程主要是为其他线程（相对可以称之为前台线程）提供服务，或“守护线程”。如JVM中的垃圾回收线程。
 * 
 * 生命周期：后台线程的生命周期与前台线程生命周期有一定关联。主要体现在：当所有的前台线程都进入死亡状态时，后台线程会自动死亡，设置后台线程：调用Thread对象的setDaemon(true)方法可以将指定的线程设置为后台线程。
 * main线程默认是前台线程，前台线程创建中创建的子线程默认是前台线程，后台线程中创建的线程默认是后台线程。调用setDeamon(true)方法将前台线程设置为后台线程时，需要在start()方法调用之前。
 * 前台线程都死亡后，JVM将会退出并通知后台线程死亡，但从接收指令到作出响应，需要一定的时间。
 */
public class MyThreadDaemon extends Thread {

	public void run() {
	     for (int i = 0; i < 100; i++) {
	         System.out.println(Thread.currentThread().getName() + " " + i);
	         try {
	        	 //使得本线程休眠，其他(前台)线程得以运行结束
	             Thread.sleep(1);
	         } catch (InterruptedException e) {
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	         }
	     }
	 }
	
	public static void main(String[] args) {

		Thread thread = new MyThreadDaemon();
	    for (int i = 0; i < 100; i++) {
	        System.out.println(Thread.currentThread().getName() + " " + i);
	        if (i == 20) {
	        	thread.setDaemon(true);
	        	thread.start();
	        	Thread.yield();
	        }
	    }
	}

}
