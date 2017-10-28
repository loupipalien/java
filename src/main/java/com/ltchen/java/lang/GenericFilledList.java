package com.ltchen.java.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * @file : GenericFilledList.java
 * @date : 2017年7月11日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc :  通过泛型,使用Class.newInstance()创建对象,但T类必须有默认无参构造方法
 * @param <T>
 */
public class GenericFilledList<T> {

	private Class<T> type;
	
	public GenericFilledList(Class<T> type) {
		this.type = type;
	}
	
	public List<T> create(int nElements){
		List<T> result = new ArrayList<T>();
		for (int i = 0; i < nElements; i++) {
			try {
				result.add(type.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		GenericFilledList<CountedInteger> gfl = new GenericFilledList<>(CountedInteger.class);
		System.out.println(gfl.create(5));
	}
}

class CountedInteger{
	private static long counter;
	private final long id = counter++;
	
	@Override
	public String toString() {
		return "CountedInteger [id=" + id + "]";
	}
	
}
