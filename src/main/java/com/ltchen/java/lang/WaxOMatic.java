package com.ltchen.java.lang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @file : WaxOMatic.java
 * @date : 2017年6月11日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用wait(),notify(),notifyAll()方法处理线程间的协调
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
	private boolean waxOn = false;
	public synchronized void waxed(){
		//涂蜡后才可抛光
		waxOn = true;
		this.notifyAll();
	}
	public synchronized void buffed(){
		//抛光后才可再涂蜡
		waxOn = false;
		this.notifyAll();
	}
	public synchronized void waitForWaxing() throws InterruptedException{
		while(waxOn == false){
			this.wait();
		}
	}
	public synchronized void waitForBuffing() throws InterruptedException{
		while(waxOn == true){
			this.wait();
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