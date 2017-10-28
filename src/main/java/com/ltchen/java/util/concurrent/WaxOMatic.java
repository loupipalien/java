package com.ltchen.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @file : WaxOMatic.java
 * @date : 2017年6月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 显示使用Lock和Condition对象,使用Condition中的await(),signal()和signalAll()方法处理线程间的协调
 */
public class WaxOMatic {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOn(car));
		exec.execute(new WaxOff(car));
		Thread.sleep(5000);
		//中断所有线程
		exec.shutdownNow();
	}
}
class Car{
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private boolean waxOn = false;
	public void waxed(){
		lock.lock();
		try {
			//涂蜡后才可抛光
			waxOn = true;
			condition.signalAll();
		} finally{
			lock.unlock();
		}
	}
	public void buffed(){
		lock.lock();
		try {
			//抛光后才可再涂蜡
			waxOn = false;
			condition.signalAll();
		} finally{
			lock.unlock();
		}
	}
	public void waitForWaxing() throws InterruptedException{
		lock.lock();
		try {
			while(waxOn == false){
				condition.await();
			}
		} finally{
			lock.unlock();
		}
	}
	public void waitForBuffing() throws InterruptedException{
		lock.lock();
		try {
			while(waxOn == true){
				condition.await();
			}
		} finally{
			lock.unlock();
		}
	}
}

class WaxOn implements Runnable{
	private Car car;
	
	public WaxOn(Car car) {
		this.car = car;
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				System.out.println("Wax On!");
				Thread.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt.");
		}
		System.out.println("Exiting via WaxOn task.");
	}
}
class WaxOff implements Runnable{
	private Car car;
	
	public WaxOff(Car car) {
		this.car = car;
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				car.waitForWaxing();
				System.out.println("Wax Off!");
				Thread.sleep(200);
				car.buffed();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt.");
		}
		System.out.println("Exiting via WaxOff task.");
	}
}