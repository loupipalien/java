package com.ltchen.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @file : RandomList.java
 * @date : 2017年7月18日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 简单泛型练习
 * @param <T>
 */
public class RandomList<T> {

	private List<T> list = new ArrayList<T>();
	private Random random = new Random(47);
	
	public void add(T item){
		list.add(item);
	}
	
	public T select(){
		return list.get(random.nextInt(list.size()));
	}
	
	public static void main(String[] args) {
		RandomList<String> rs = new RandomList<String>();
		for (String str : "The quick brown fox jumped over the lazy brown dog".split(" ")) {
			rs.add(str);
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(rs.select());
		}
	}
}
