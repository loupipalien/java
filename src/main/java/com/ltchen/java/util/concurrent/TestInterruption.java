package com.ltchen.java.util.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @file : Interrupting.java
 * @date : 2017年6月7日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试中断：
 * 				sleep阻塞可中断
 * 				io阻塞不可中断
 * 				同步锁阻塞不可中断
 */ 
public class TestInterruption {
	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	public static void testInterrupt(Runnable r) throws InterruptedException{
		Future<?> f = exec.submit(r);
		Thread.sleep(100);
		System.out.println("Interrupting: " + r.getClass().getName());
		//如果线程处于running状态则中断
		f.cancel(true);
		System.out.println("Interrupt send to " + r.getClass().getName());
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestInterruption.testInterrupt(new SleepBlocked());
		TestInterruption.testInterrupt(new IOBlocked(System.in));
		TestInterruption.testInterrupt(new SynchonizedBlocked());
		Thread.sleep(3000);
		System.out.println("Aborting with System.exit(0).");
		System.exit(0);
	}
}

//sleep阻塞
class SleepBlocked implements Runnable{
	
	@Override
	public void run() {
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException.");
		}
		System.out.println("Exiting SleepBlocked.run().");
	}
}
//io阻塞
class IOBlocked implements Runnable{

	private InputStream in;
	
	public IOBlocked(InputStream in){
		this.in = in;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Waiting for read...");
			while(true){
				in.read();
			}
		} catch (IOException e) {
			if(Thread.currentThread().isInterrupted()){
				System.out.println("interrupted from blocked I/O.");
			}
			else {
				throw new RuntimeException();
			}
		}
		System.out.println("Exiting IOBlocked.run().");
	}
}
//同步锁阻塞
class SynchonizedBlocked implements Runnable{
	
	public SynchonizedBlocked() {
		//构造一个匿名线程对象获得锁
		new Thread(){
			@Override
			public void run(){
				getLockAndNeverRelease();
			}
		};
	}
	
	//获得锁不释放
	public synchronized void getLockAndNeverRelease(){
		while (true) {
			Thread.yield();
		}
	}
	
	@Override
	public void run() {
		System.out.println("Trying to call getLockAndNeverRelease().");
		this.getLockAndNeverRelease();
		System.out.println("Exiting SynchonizedBlocked.run().");
	}
}