package com.ltchen.java.io.util;

import java.util.Arrays;
import java.util.Collection;

/**
 * @file : PPrint.java
 * @date : 2017年6月26日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 打印工具类
 */
public class PPrint {

	public static String pformat(Collection<?> c){
		if(c.size() == 0){
			return "[]";
		}
		StringBuilder sb = new StringBuilder("[");
		for (Object obj : c) {
			if(c.size() != 1){
				sb.append("\n ");
			}
			sb.append(obj);
		}
		if(c.size() != 1){
			sb.append("\n ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void pprint(Collection<?> c){
		System.out.println(pformat(c));
	}
	
	public static void pprint(Object[] c) {
		System.out.println(pformat(Arrays.asList(c)));
	}
}
