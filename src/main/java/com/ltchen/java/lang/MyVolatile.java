package com.ltchen.java.lang;

/**
 * @file : MyVolatile.java
 * @date : 2017年5月30日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : volatile的使用,如果声明volatile关键字的变量,当此变量的所有操作是原子性可以避免加锁,
 * 如果变量的所有操作不是原子性的,则需要加锁进行同步
 */
public class MyVolatile {

	// 可将volatile变量修饰符去除运行观察,atomicOperation方法中的线程不会结束
	private volatile static boolean changed = false;

	/*
	 * boolean类型赋值时原子操作,当changed变量声明为volatile时,不会将值存到线程栈的本地缓存中
	 * 而是将变量刷入堆内存中,保持可视性。如果变量不声明为volatile,会将值存到本地缓存做优化,对
	 * 其他线程来讲不能保证堆内存的可视性,从而造成问题
	 */
	public static void atomicOperation() {

		new Thread() {
			@Override
			public void run() {
				for (;;) {
					if (changed == !changed) {
						System.out.println("!=");
						System.exit(0);
					}
				}
			}
		}.start();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread() {
			@Override
			public void run() {
				for (;;) {
					changed = !changed;
				}
			}
		}.start();
	}

	// 可将volatile变量修饰符去除运行观察,仍然和声明volatile一样,会有并发问题
	public volatile static int count = 0;

	/*
	 * 自增操作不是原子操作,需要经过加载,赋值,写回三步原子操作,未作同步写回引发并发的问题
	 */
	public static void nonAtomicOperation(int nThreads){
		//同时启动1000个线程，去进行i++计算，看看实际结果
		for (int i = 0; i < nThreads; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					count++;
				}
			}).start();
		}
		System.out.println(String.format("count=%s", count));
	}
	public static void main(String[] args) {
//		MyVolatile.atomicOperation();
		MyVolatile.nonAtomicOperation(100);
	}


}
