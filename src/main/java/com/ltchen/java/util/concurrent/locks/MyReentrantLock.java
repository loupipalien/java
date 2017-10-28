package com.ltchen.java.util.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @file : MyReentrantLock.java
 * @date : 2017年5月29日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 可重入锁
 */
public class MyReentrantLock {

	private static Lock lock = new ReentrantLock();
	
	public static void tryLock(){
		boolean captured = false;
		captured = lock.tryLock();
		try {
			System.out.println(String.format("method is [%s], the captured is [%s].", "tryLock()", captured));
		} finally {
			if(captured){
				lock.unlock();
			}
		}
	}
	
	public static void tryLock(long timeout, TimeUnit unit){
		boolean captured = false;
		try {
			captured = lock.tryLock(timeout, unit);
			System.out.println(String.format("method is [%s], the captured is [%s].", "tryLock(long timeout, TimeUnit unit)", captured));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(captured){
				lock.unlock();
			}
		}
	}
	
	public static void lock(){
		boolean captured = false;
		lock.lock();
		try {
			captured = true;
			System.out.println(String.format("method is [%s], the captured is [%s].", "lock()", captured));
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 尝试获取锁,失败时可尝试多次
	 * @param tryTimes
	 */
	public static void tryLock(int tryTimes){
		boolean captured = false;
		for (int i = 1; i <= tryTimes; i++) {
			captured = lock.tryLock();
			try {
				System.out.println(String.format("method is [%s], the captured is [%s],try times=[%s].", "tryLock(int tryTimes)", captured,i));
				if(captured){
					break;
				}
			} finally {
				if(captured){
					lock.unlock();
				}
			}
		}
	}
	
	/**
	 * 尝试获取锁,失败时可按照时间间隔尝试多次
	 * @param tryTimes
	 */
	public static void tryLock(int tryTimes, long interval, TimeUnit unit){
		boolean captured = false;
		for (int i = 1; i <= tryTimes; i++) {
			captured = lock.tryLock();
			try {
				System.out.println(String.format("method is [%s], the captured is [%s],try times=[%s].", "tryLock(int tryTimes, long interval, TimeUnit unit)", captured,i));
				if(captured){
					break;
				}
				try {
					Thread.sleep(TimeUnit.MILLISECONDS.convert(interval, unit));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				if(captured){
					lock.unlock();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		MyReentrantLock.tryLock();
		MyReentrantLock.tryLock(2, TimeUnit.SECONDS);
		MyReentrantLock.lock();
		MyReentrantLock.tryLock(5);
		MyReentrantLock.tryLock(5,3,TimeUnit.SECONDS);
		new Thread(){
			//匿名线程对象调用方法
			{setDaemon(true);}
			@Override
			public void run(){
				MyReentrantLock.lock.lock();
				System.out.println("the daemon thread hold lock.");
			}
		}.start();
		try {
			//将主线程sleep,确保后台线程拿到锁
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MyReentrantLock.tryLock();
		MyReentrantLock.tryLock(2, TimeUnit.SECONDS);
		//lock方法会一直尝试拿到锁,但因为后台线程拿到锁不释放,导致线程阻塞
		//MyReentrantLock.lock();
		MyReentrantLock.tryLock(5);
		MyReentrantLock.tryLock(5,3,TimeUnit.SECONDS);
	}
}
