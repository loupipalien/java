package com.ltchen.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @file : MyExecutors.java
 * @date : 2017年6月4日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用Executors创建各种线程池的ExecutorService
 */
public class MyExecutors {
	
	private static int number = 5;
	
	/*
	 * 创建一个可缓存的线程池,可灵活回收空闲线程,若无可回收则创建新的线程绑定任务
	 */
	public static void cachedThreadPool(){
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < number; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}
	
	/*
	 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
	 */
	public static void fixedThreadPool(int nThreads){
		ExecutorService exec = Executors.newFixedThreadPool(nThreads);
		for (int i = 0; i < nThreads; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}
	
	/*
	 * 创建一个定长线程池，支持定时及周期性任务执行
	 */
	public static void scheduledThreadPool(int corePoolSize){
		ScheduledExecutorService schExec = Executors.newScheduledThreadPool(corePoolSize);
		for (int i = 0; i < corePoolSize; i++) {
			schExec.schedule(new LiftOff(), corePoolSize + i, TimeUnit.SECONDS);
		}
		schExec.shutdown();
	}
	
	/*
	 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
	 */
	public static void singleThreadExecutor(){
		ExecutorService exec = Executors.newSingleThreadExecutor();
		for (int i = 0; i < number; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}

	public static void main(String[] args) {
//		MyExecutors.cachedThreadPool();
//		MyExecutors.fixedThreadPool(number);
//		MyExecutors.scheduledThreadPool(number);
//		MyExecutors.singleThreadExecutor();
	}
}
