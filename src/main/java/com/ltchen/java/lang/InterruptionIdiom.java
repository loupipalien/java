package com.ltchen.java.lang;

/**
 * @file : InterruptionIdiom.java
 * @date : 2017年6月11日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 当线程在阻塞状态和非阻塞状态时被中断如何进行清理过程
 * 在阻塞状态被中断(在point1和point2之间被中断):线程在InterruptedException退出,即可以在catch语句块中进行清理操作
 * 在非阻塞状态被中断(在point2之后被中断)：线程在while退出,即可以在finally语句块中进行清理操作
 * 
 * 线程对象的interrupt()只是将用于设置线程的中断状态,资源的清理需要程序员在线程退出时进行处理
 */
public class InterruptionIdiom {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Blocked());
		t.start();
		Thread.sleep(1500);
		t.interrupt();
	}
}

class NeedsCleanUp {
	private final int id;

	public NeedsCleanUp(int id) {
		this.id = id;
		System.out.println("NeedsCleanUp " + id);
	}

	public void cleanUp() {
		System.out.println("cleaning up " + id);
	}
}

class Blocked implements Runnable {
	private volatile double d = 0.0;

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// point1
				NeedsCleanUp n1 = new NeedsCleanUp(1);
				try {
					System.out.println("Sleeping.");
					//阻塞操作
					Thread.sleep(1000);
					// point2
					NeedsCleanUp n2 = new NeedsCleanUp(2);
					try {
						System.out.println("Calculating.");
						// 非阻塞操作,仅仅为了消耗时间
						for (int i = 1; i < 2500000; i++) {
							d = d + (Math.PI + Math.E) / d;
						}
					} finally {
						n2.cleanUp();
					}
				} finally {
					n1.cleanUp();
				}
				System.out.println("Existing via while().");
			}
		} catch (InterruptedException e) {
			System.out.println("Existing via InterruptedException.");
		}
	}
}