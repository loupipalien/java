package com.ltchen.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class MyFuture {

	private static int number = 10;

	public static void callResult() {
		List<java.util.concurrent.Future<String>> futures = new ArrayList<java.util.concurrent.Future<String>>();
		ExecutorService exec = java.util.concurrent.Executors.newCachedThreadPool();
		for (int i = 0; i < number; i++) {
			futures.add(exec.submit(new CallableTask(i)));
		}
		try {
			for (java.util.concurrent.Future<String> future : futures) {
				System.out.println(future.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}finally {
			exec.shutdown();
		}
	}

	public static void main(String[] args) {
		MyFuture.callResult();
	}
}

class CallableTask implements Callable<String> {

	private int id;

	public CallableTask() {
	}

	public CallableTask(int id) {
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		return String.format("result of CallableTask %s", id);
	}

}