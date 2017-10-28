package com.ltchen.java.util.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @file : TestReentrantLockInterruption.java
 * @date : 2017年6月11日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : ReentrantLock上阻塞的任务可以具备被中断的能力,这与synchronized方法或者临界区上的阻塞任务完全不同
 * 	BlockedMutex在初始化时获取锁并不释放,当在Blocked线程中调用interruptible()应因为不能获得锁而阻塞.但是在
 * 	interruptible()中调用ReentrantLock的lockInterruptibly()方法,当Blocked线程被主线程中断时获得锁并抛出
 * InterruptedException异常
 */
public class TestReentrantLockInterruption {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Blocked());
		t.start();
		Thread.sleep(50000);
		System.out.println("Issuing t.interrupt().");
		t.interrupt();
	}
}
class BlockedMutex{
	private Lock lock =  new ReentrantLock();
	
	public BlockedMutex(){
		//初始化时获取锁并不释放(可注释这一句比较区别)
		lock.lock();
	}
	
	public void interruptible(){
		try {
			lock.lockInterruptibly();
			System.out.println("lock acquired in interruptible().");
		} catch (InterruptedException e) {
			System.out.println("Interrupted from lock acquisition in interruptible().");
		}
	}
}
class Blocked implements Runnable{
	BlockedMutex blockedMutex = new BlockedMutex();
	@Override
	public void run() {
		System.out.println("Waiting for interruptible() in BlockedMutex");
		blockedMutex.interruptible();
		System.out.println("Broken out of blocked call.");
	}
}