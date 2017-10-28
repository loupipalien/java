package com.ltchen.java.lang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @file : Restaurant.java
 * @date : 2017年6月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 生产者：Chef;消费者：WaitPerson;产品：Meal
 */
public class Restaurant {
	protected Meal meal;
	ExecutorService exec = Executors.newCachedThreadPool();
	WaitPerson waitPerson = new WaitPerson(this);
	Chef chef = new Chef(this);
	public Restaurant() {
		exec.execute(chef);
		exec.execute(waitPerson);
	}
	public static void main(String[] args) {
		new Restaurant();
	}
}
  
class Meal{
	private final int orderNumer;

	public Meal(int orderNumer) {
		this.orderNumer = orderNumer;
	}

	@Override
	public String toString() {
		return "Meal [orderNumer=" + orderNumer + "]";
	}
	
}

class WaitPerson implements Runnable{
	private Restaurant restaurant;

	public WaitPerson(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				synchronized (this) {
					while(restaurant.meal == null){
						//waiting for the chef to produce a meal.
						this.wait();
					}
				}
				System.out.println("WaitPerson got " + restaurant.meal);
				synchronized (restaurant.chef) {
					restaurant.meal = null;
					//ready for another
					restaurant.chef.notifyAll();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("WaitPerson interrupted.");
			//e.printStackTrace();
		}
	}
	
}

class Chef implements Runnable{
	
	private Restaurant restaurant;
	private int count = 0;

	public Chef(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				synchronized (this) {
					while(restaurant.meal != null){
						//waiting for the meal to be taken
						this.wait();
					}
				}
				if(++count == 10){
					System.out.println("Out of food,Closing.");
					restaurant.exec.shutdownNow();
				}
				System.out.println("Order up!");
				synchronized (restaurant.waitPerson) {
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.notifyAll();
				}
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Chef interrupted.");
			//e.printStackTrace();
		}
	}
	
}