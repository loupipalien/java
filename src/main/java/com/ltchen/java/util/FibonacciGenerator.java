package com.ltchen.java.util;

import java.util.Iterator;

public class FibonacciGenerator implements Generator<Integer>, Iterable<Integer> {

	//for iteration
	private int n = 0;
	
	public FibonacciGenerator() {}
	
	public FibonacciGenerator(int n) {
		this.n = n;
	}
	
	@Override
	public Integer next() {
		return fibonacci(n++);
	}
	
	private int fibonacci(int n){
		if(n < 2 ){ 
			return 1;
		}
		return fibonacci(n-2) + fibonacci(n-1);
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new FibonacciIterator();
	}

	class FibonacciIterator implements Iterator<Integer>{
		private int count = n;
		
		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Integer next() {
			count--;
			return FibonacciGenerator.this.next();
		}
	}
	
	public static void main(String[] args) {
		FibonacciGenerator fg = new FibonacciGenerator();
		for (int i = 0; i < 10; i++) {
			System.out.println(fg.next());
		}
		System.out.println("=======================");
		for (Integer i : new FibonacciGenerator(10)) {
			System.out.println(i);
		}
	}
}
