package com.ltchen.java.javase.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

	private Timer timer;
	
	public MyTimer(){
		timer = new Timer();
	}
	
	public void schedule(TimerTask task, Date time){
		System.out.println("指定时间执行定时任务...");
		timer.schedule(task, time);
	}
	
	public void schedule(TimerTask task, Date firstTime, long period){
		System.out.println("指定第一次执行定时任务的时间并按一定的间隔重复执行...");
		timer.schedule(task, firstTime, period);
	}
	
	public void schedule(TimerTask task, long delay){
		System.out.println("指定延迟时间执行定时任务...");
		timer.schedule(task, delay);
	}
	
	public void schedule(TimerTask task, long delay, long period){
		System.out.println("指定延迟时间执行定时任务并按一定的间隔重复执行...");
		timer.schedule(task, delay, period);
	}
	
	public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period){
		System.out.println("指定第一次执行定时任务的时间并按一定的间隔重复执行,侧重按一定的执行频率...");
		timer.scheduleAtFixedRate(task, firstTime, period);
	}
	
	public void scheduleAtFixedRate(TimerTask task, long delay, long period){
		System.out.println("指定延迟时间执行定时任务并按一定的间隔重复执行,侧重按一定的执行频率...");
		timer.scheduleAtFixedRate(task, delay, period);
	}
	
	public static Date getTime(int sec, int min, int hour){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, sec);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}
	
	public static void main(String[] args) {
		Date firstTime = MyTimer.getTime(30, 26, 1);
		long delay = 5000L;
		long period = 10000L;
//		new MyTimer().schedule(new MyTimerTask(), firstTime);
//		new MyTimer().schedule(new MyTimerTask(), firstTime, period);
//		new MyTimer().schedule(new MyTimerTask(), delay);
		MyTimer myTimer = new MyTimer();
		myTimer.schedule(new MyTimerTask(), 3000);
		myTimer.schedule(new MyTimerTask(), 3000);
//		new MyTimer().schedule(new MyTimerTask(), delay, period);
//		new MyTimer().scheduleAtFixedRate(new MyTimerTask(), firstTime, period);
//		new MyTimer().scheduleAtFixedRate(new MyTimerTask(), delay, period);
	}

}
