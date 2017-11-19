package com.ltchen.java.util;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc :
 */
public interface Generator<T> {

	/**
	 * 获取下一个实例
	 * @return T
	 */
	T next();
}
