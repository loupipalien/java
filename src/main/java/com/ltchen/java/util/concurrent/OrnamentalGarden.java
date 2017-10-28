package com.ltchen.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGarden {

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Entrance(i));
		}
		Thread.sleep(3000);
		Entrance.cancel();
		exec.shutdown();
		//在指定时间内等待所有任务结束,如果在指定时间内结束返回true,否则返会false.并使所有任务退出run方法
		if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS)){
			System.out.println("Some tasks were not terminated.");
		}
		System.out.println(String.format("Total : %s", Entrance.getTotalCount()));
		System.out.println(String.format("Sum of Entrances : %s", Entrance.sumEntrances()));
	}
}

class Counter{
	private int count = 0;
	private Random random =  new Random(47);
	
	public synchronized int increment(){
		int tmp = count;
		if(random.nextBoolean()){
			Thread.yield();
		}
		count = ++tmp;
		return count;
	}
	
	public synchronized int value(){
		return count;
	}
}

class Entrance implements Runnable{
	private static Counter counter = new Counter();
	private static List<Entrance> entrances = new ArrayList<Entrance>();
	private static volatile boolean canceled = false;
	private final int id;
	private int number = 0;
	
	public static void cancel(){
		canceled = true;
	}
	
	public Entrance(int id) {
		this.id = id;
		entrances.add(this);
	}
	
	@Override
	public void run() {
		while(!canceled){
			synchronized (this) {
				++number;
			}
			System.out.println(String.format("%s Total: %s", this, counter.increment()));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(String.format("Stopping : %s", this));
	}
	
	public synchronized int getValue(){
		return number;
	}
	
	@Override
	public String toString() {
		return String.format("Entrance %s : %s", id, this.getValue());
	}
	
	public static int getTotalCount(){
		return counter.value();
	}
	
	public static int sumEntrances(){
		int sum = 0;
		for (Entrance entrance : entrances) {
			sum += entrance.getValue();
		}
		return sum;
	}
}