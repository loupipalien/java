package com.ltchen.java.util.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @file : MyBlockingQueue.java
 * @date : 2017年6月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : wait(),notify()和notifyAll()方法以一种非常低级的方式解决了任务互操作的问题,即每次交互时都握手.在许多情况下可以使用
 * 同步队列来解决任务协作问题,同步任务在任何时刻下都只允许一个任务插入或移除元素.在java.util.concurrent.BlockingQueue接口中提供了
 * 这个队列，着接口有不同的实现.当消费着任务试图从队列中获取对象,而该队列此时为空时,那么这些队列可以挂起消费者任务,并且当有更多的元素
 * 可用时恢复消费者任务,阻塞队列可以解决非常大量的同步问题,而其方式比使用wait(),notify()和notifyAll()方式相比简单可靠的多
 */
public class MyBlockingQueue {
	public void getKey(){
		//compensate for Windows/Linux difference in the length of the result produced by the Enter key.
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			throw new RuntimeException();
			//e.printStackTrace();
		}
	}
	
	public void getKey(String message){
		System.out.println(message);
		this.getKey();
	}
	
	public void test(String message,BlockingQueue<LiftOff> queue){
		System.out.println(message);
		LiftOffRunner runner = new LiftOffRunner(queue);
		Thread t = new Thread(runner);
		t.start();
		for (int i = 0; i < 5; i++) {
			runner.add(new LiftOff(10));
		}
		this.getKey("Press 'Enter' (" + message + ")");
		t.interrupt();
		System.out.println("Finished " + message + " test");
	}
	
	public static void main(String[] args) {
		MyBlockingQueue myBlockingQueue = new MyBlockingQueue();
		//Unlimited size
		myBlockingQueue.test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
		//Fixed size
		myBlockingQueue.test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
		//Size of one
		myBlockingQueue.test("SynchonizedBlocked", new SynchronousQueue<LiftOff>());
	}
}

class LiftOffRunner implements Runnable{

	private BlockingQueue<LiftOff> rocketsQueue;
	
	public LiftOffRunner(BlockingQueue<LiftOff> rocketsQueue) {
		this.rocketsQueue = rocketsQueue;
	}

	public void add(LiftOff liftOff){
		try {
			rocketsQueue.put(liftOff);
		} catch (InterruptedException e) {
			System.out.println("Interrupted during put().");
			//e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
					LiftOff rocket = rocketsQueue.take();
					//use this thread 
					rocket.run();
			}
		} catch (InterruptedException e) {
			System.out.println("Waking from take().");
			//e.printStackTrace();
		}
	}
}