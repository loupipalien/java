package com.ltchen.java.lang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadLocal {
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
    	@Override 
    	protected Integer initialValue() {
    	        return new Integer(8);
    	    }
    };
    
    public static void increment(){
    	value.set(value.get() + 1);
    }
    
    public static Integer get(){
    	return value.get();
    }
    
    public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Accessor(i));
		}
		try {
//			TimeUnit.SECONDS.sleep(3);
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exec.shutdownNow();
	}
}

class Accessor implements Runnable{
	private final int id;
	
	public Accessor(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			MyThreadLocal.increment();
			System.out.println(this);
			Thread.yield();
		}
	}
	
	@Override
	public String toString() {
		return String.format("#%s : %s", id, MyThreadLocal.get());
	}
}
