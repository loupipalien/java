package com.ltchen.java.util;

/**
 * @file : LinkedStack.java
 * @date : 2017年7月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc :  自定义实现栈的内部链式存储机制
 * @param <T>
 */
public class LinkedStack<T> {

	private static class Node<U>{
		private U item;
		private Node<U> next;
		public Node() {
			item = null;
			next = null;
		}
		public Node(U item, Node<U> next){
			this.item = item;
			this.next = next;
		}
		public boolean end(){
			return item == null && next == null;
		}
	}
	
	//末端哨兵
	private Node<T> top = new Node<T>();  
	
	public void push(T item){
		top = new Node<T>(item, top);
	}
	
	public T pop(){
		T result = top.item;
		if(!top.end()){
			top = top.next; 
		}
		return result;
	}
	
	public static void main(String[] args) {
		LinkedStack<String> ls = new LinkedStack<String>();
		for (String str : "Phasers on stun!".split(" ")) {
			ls.push(str);
		}
		String str;
		while((str = ls.pop()) != null){
			System.out.println(str);
		}
	}
}
