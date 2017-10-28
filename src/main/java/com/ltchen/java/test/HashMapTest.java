package com.ltchen.java.javase.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapTest {

	public static void main(String[] args) {

		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		
		Iterator<Integer> it = map.keySet().iterator();
		while(it.hasNext()){
			Integer i = it.next();
			if(i == 2){
				it.remove();
				map.remove(i);
				System.out.println(map.size());
				System.out.println(map);
			}
		}
		
	}

}
