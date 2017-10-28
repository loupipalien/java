package com.ltchen.java.lang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @file : EvenChecker.java
 * @date : 2017年5月29日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 检查IntGenerator生成的int数字是否都是偶数,如果不是则停止生成
 */
public class EvenChecker implements Runnable {

	private final int id;
	private IntGenerator intGenerator;
	
	public EvenChecker(int id, IntGenerator intGenerator) {
		this.id = id;
		this.intGenerator = intGenerator;
	}
	
	@Override
	public void run() {
		Thread.currentThread().setName(String.format("Thread-%s", id));
		while(!intGenerator.isCanceled()){
			int value = intGenerator.next();
			if(value % 2 != 0){
				System.out.println(String.format("[%s] : current value [%s] is not even!", Thread.currentThread().getName(), value));
				intGenerator.cancel();
			}
		}
	}
	
	public static void test(IntGenerator intGenerator, int nThreads){
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < nThreads; i++) {
			exec.execute(new EvenChecker(i, intGenerator));
		}
		exec.shutdown();
	}
	
	public static void test(IntGenerator intGenerator){
		 test(intGenerator, 10);
	}
	
	public static void main(String[] args) {
		//测试EvenIntGenerator,当对共享资源currentValue没有同步时
//		EvenChecker.test(new EvenIntGenerator());
		//测试EvenIntGenerator,当对共享资源currentValue有同步时
		EvenChecker.test(new SynchronizedEvenIntGenerator());
	}
}
