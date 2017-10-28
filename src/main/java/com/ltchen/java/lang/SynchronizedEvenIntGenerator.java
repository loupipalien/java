package com.ltchen.java.lang;

/**
 * @file : SynchronizedEvenIntGenerator.java
 * @date : 2017年5月29日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 偶数int生成器,next方法中对currentValue进行自增时进行同步控制
 */
public class SynchronizedEvenIntGenerator extends IntGenerator {

	//共享资源
	private int currentValue = 0;
	
	/*
	 * 使用synchronized的方法,锁对象默认是this(non-Javadoc)
	 */
	@Override
	public synchronized int next() {
		//非原子性操作
		currentValue++;
		//即使这里调用线程的yield方法,也不会导致线程竞争发生错误
		Thread.yield();
		currentValue++;
		return currentValue;
	}
	
	/*
	 * 使用synchronized的代码块,使用代码块可以过滤一些不需要同步的操作,例如sysout...
	 */
	//使用synchronized的代码块时常常也会new一个Object对象当作锁
	//private Object object = new Object();
//	@Override
//	public int next() {
//		System.out.println(String.format("[%s] : hello", Thread.currentThread().getName()));
////		synchronized(object) {
//		synchronized(this) {
//			//非原子性操作
//			currentValue++;
//			//即使这里调用线程的yield方法,也不会导致线程竞争发生错误,因为调用yield的时候锁并没有被释放
//			Thread.yield();
//			currentValue++;
//			return currentValue;
//		}
//	}
	
	/*
	 * 使用显式的Lock对象,Lock对象必需显式的创建,锁定,释放
	 */
//	private Lock lock = new ReentrantLock();
//	@Override
//	public int next() {
//		lock.lock();
//		try {
//			//非原子性操作
//			currentValue++;
//			//即使这里调用线程的yield方法,也不会导致线程竞争发生错误,因为调用yield的时候锁并没有被释放
//			Thread.yield();
//			currentValue++;
//			return currentValue;
//		} finally {
//			//最后必须释放锁
//			lock.unlock();
//		}
//	}
}
