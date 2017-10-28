package com.ltchen.java.util;

import java.util.Iterator;
import java.util.Random;

/**
 * @file : CoffeeGenerator.java
 * @date : 2017年7月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 泛型接口
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {

	private Class<?>[] types = {Latte.class, Mocha.class, Cappuccino.class, Americano.class, Breve.class};
	private static Random random = new Random(47);
	//for Iteration
	private int size = 0;
	
	public CoffeeGenerator() {}
	
	public CoffeeGenerator(int size) {
		this.size = size;
	}
	
	@Override
	public Coffee next() {
		Coffee coffee = null;
		try {
			coffee = (Coffee)types[random.nextInt(types.length)].newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return coffee;
	}
	
	@Override
	public Iterator<Coffee> iterator() {
		return new CoffeeIterator();
	}

	class CoffeeIterator implements Iterator<Coffee>{
		private int count = size;
		
		@Override
		public boolean hasNext(){
			return count > 0;
		}
		
		@Override
		public Coffee next(){
			count--;
			return CoffeeGenerator.this.next();
		}
		
	}
	
	public static void main(String[] args) {
		CoffeeGenerator cg = new CoffeeGenerator();
		for (int i = 0; i < 5; i++) {
			System.out.println(cg.next());
		}
		System.out.println("=======================");
		for (Coffee coffee : new CoffeeGenerator(5)) {
			System.out.println(coffee);
		}
	}
}


class Coffee{
	private static int counter = 0;
	private final int id = counter++;
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "-" + id;
	}
}

class Latte extends Coffee{}

class Mocha extends Coffee{}

class Cappuccino extends Coffee{}

class Americano extends Coffee{}

class Breve extends Coffee{}

