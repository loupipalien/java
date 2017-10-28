package com.ltchen.java.javase.util;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" : MyTimerTask is executed...");
	}

}
