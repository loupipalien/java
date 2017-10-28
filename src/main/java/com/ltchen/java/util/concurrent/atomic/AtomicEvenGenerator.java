package com.ltchen.java.util.concurrent.atomic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import com.ltchen.java.lang.EvenChecker;
import com.ltchen.java.lang.IntGenerator;

/**
 * @file : AtomicEvenGenerator.java
 * @date : 2017年6月1日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用原子类作偶数生成器
 */
public class AtomicEvenGenerator extends IntGenerator {

	private AtomicInteger currentValue = new AtomicInteger(0);
	
	@Override
	public int next() {
		return currentValue.addAndGet(2);
	}
	
	public static void main(String[] args) {
		//定时
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.err.println("Aborting");
				System.exit(0);
			}
		}, 5000);
		EvenChecker.test(new AtomicEvenGenerator());
	}
}
