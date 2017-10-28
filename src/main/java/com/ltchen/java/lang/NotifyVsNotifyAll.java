package com.ltchen.java.lang;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @file : NotifyVsNotifyAll.java
 * @date : 2017年6月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用notify()在众多等待同一个锁的任务中只有一个会被唤醒，因此如果希望使用notify(),就必需保证被唤醒的是恰当的任务。另外，
 * 为了使用notify()，所有任务必需等待相同的条件，因为如果有多个不同的任务在等待不同的条件，那么就不知道是否唤醒了恰当的任务。这些限制
 * 队所有的可能存在的子类都必须总是起作用的。如果是需要唤醒所有任务，那么必须使用的是notifyAll()而不是notify()
 * 注意：当notifyAll()因某个特定的锁调用时,只有等待这个锁的任务会被唤醒。
 */ 
public class NotifyVsNotifyAll {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new TaskOne());
		}
		exec.execute(new TaskTwo());
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			boolean prod = true;
			@Override
			public void run() {
				if(prod){
					System.out.println("\nnotify().");
					TaskOne.blocker.prod();
					prod = false;
				}
				else{
					System.out.println("\nnotifyAll().");
					TaskOne.blocker.prodAll();
					prod = true;
				}
			}
		}, 400, 400);
		Thread.sleep(5000);
		timer.cancel();
		System.out.println("\nTimer cancleed.");
		Thread.sleep(500);
		System.out.println("TaskTwo.blocker.prodAll().");
		TaskTwo.blocker.prodAll();
		Thread.sleep(500);
		System.out.println("\nShutting down.");
		exec.shutdownNow();
	}
}

class Blocker{
	synchronized void waitingCall(){
		try {
			while(!Thread.interrupted()){
				this.wait();
				System.out.println(Thread.currentThread() + " ");
			}
		} catch (InterruptedException e) {
			//OK to exit this way
			//e.printStackTrace();
		}
	}
	
	synchronized void prod(){
		this.notify();
	}
	
	synchronized void prodAll(){
		this.notifyAll();
	}
}

class TaskOne implements Runnable{

	static Blocker blocker = new Blocker();
	
	@Override
	public void run() {
		blocker.waitingCall();
	}
	
}

class TaskTwo implements Runnable{

	static Blocker blocker = new Blocker();
	
	@Override
	public void run() {
		blocker.waitingCall();
	}
	
}
