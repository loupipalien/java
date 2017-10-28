package com.ltchen.java.util.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @file : ToastOMatic.java
 * @date : 2017年6月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 这个示例没有使用任何显示的同步(即使用Lock和Synchronized关键字同步),而是使用同步队列和代码设计隐式的管理---每片Toast在
 * 任何时候都只由一个任务在操作。因为队列阻塞,使得处理过程被自动的挂起和恢复.可以看到使用BlcokingQueue产生的简化非常明显,在使用显示
 *  wait(),notify()和notifyAll()时存在的任务类和共享资源类之间的耦合消除了,因为每个类都只和它相关的BlcokingQueue通信即可
 */
public class ToastOMatic {
	public static void main(String[] args) throws InterruptedException {
		ToastQueue dryQueue = new ToastQueue(),butteredQueue = new ToastQueue(),jammedQueue = new ToastQueue();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butterer(dryQueue, butteredQueue));
		exec.execute(new Jammer(butteredQueue, jammedQueue));
		exec.execute(new Eater(jammedQueue));
		Thread.sleep(3000);
		exec.shutdownNow();
	}
}

class Toast{
	public enum Status{DRY,BUTTERED,JAMMED};
	private Status status = Status.DRY;
	private final int id;
	public Toast(int id) {
		this.id = id;
	}
	public void butter(){
		status = Status.BUTTERED;
	}
	public void jam(){
		status = Status.JAMMED;
	}
	public Status getStatus(){
		return status;
	}
	public int getId(){
		return id;
	}
	
	@Override
	public String toString() {
		return "Toast [status=" + status + ", id=" + id + "]";
	}
}

class ToastQueue extends LinkedBlockingQueue<Toast>{
	private static final long serialVersionUID = 3584746305295022909L;
}

class Toaster implements Runnable{

	private ToastQueue dryQueue;
	private int count = 0;
	private Random random = new Random(47);
	public Toaster(ToastQueue dryQueue) {
		this.dryQueue = dryQueue;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				Thread.sleep(100 + random.nextInt(500));
				//Make toast
				Toast toast = new Toast(count++);
				System.out.println(toast);
				//Insert into the queue
				dryQueue.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Toaster interrupted.");
			//e.printStackTrace();
		}
		System.out.println("Toaster off.");
	}
}

class Butterer implements Runnable{

	private ToastQueue dryQueue,butteredQueue;
	public Butterer(ToastQueue dryQueue,ToastQueue butteredQueue) {
		this.dryQueue = dryQueue;
		this.butteredQueue = butteredQueue;
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				//Blocks until next piece of toast is available.
				Toast toast = dryQueue.take();
				toast.butter();
				System.out.println(toast);
				butteredQueue.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Butterer interrupted.");
			//e.printStackTrace();
		}
		System.out.println("Butterer off.");
	}
}

class Jammer implements Runnable{

	private ToastQueue butteredQueue,jammedQueue;
	public Jammer(ToastQueue butteredQueue,ToastQueue jammedQueue) {
		this.butteredQueue = butteredQueue;
		this.jammedQueue = jammedQueue;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				Toast toast = butteredQueue.take();
				toast.jam();
				System.out.println(toast);
				jammedQueue.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Jammer interrupted.");
			//e.printStackTrace();
		}
		System.out.println("Jammer off.");
	}
}

class Eater implements Runnable{

	private ToastQueue jammedQueue;
	private int counter = 0;
	public Eater(ToastQueue jammedQueue) {
		this.jammedQueue = jammedQueue;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				//Blocks until next piece of toast is available.
				Toast toast = jammedQueue.take();
				//Verify that the toast is coming in order and that all pieces are getting jammed.
				if(toast.getId() != counter++ || toast.getStatus() != Toast.Status.JAMMED){
					System.out.println(">>>ERROR： " + toast);
					System.exit(1);
				}
				else{
					System.out.println("Chomp! " + toast);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Eater interrupted.");
			//e.printStackTrace();
		}
		System.out.println("Eater off.");
	}
}