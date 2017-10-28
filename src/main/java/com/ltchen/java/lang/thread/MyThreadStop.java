package com.ltchen.java.javase.lang.thread;

/**
 * 
 * @file : MyStopThread.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 由于实际的业务需要，常常会遇到需要在特定时机终止某一线程的运行，使其进入到死亡状态。目前最通用的做法是设置一boolean型的变量，当条件满足时，使线程执行体快速执行完毕。
 */
public class MyThreadStop extends Thread{

	private boolean stop = false;
	
	public void stopThread() {
		this.stop = true;
	}

	@Override
	public void run() {
		//根据stop的值决定是否"快速"结束进程
		for (int i = 0; i < 100 && !stop; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}

	public static void main(String[] args) {

		Thread thread = new MyThreadStop();

		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 30) {
				thread.start();
			}
			if (i == 80) {
				((MyThreadStop) thread).stopThread();
			}
		}
	}

}
