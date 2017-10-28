package com.ltchen.java.lang;

public interface Factory<T> {

	/**
	 * 返回T类实例
	 * @return
	 */
	T create();
}
