package com.ltchen.java.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * @file : TypeCounter.java
 * @date : 2017年7月12日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 递归计算类以及其基类出现的次数
 */
public class TypeCounter extends HashMap<Class<?>,Integer>{
	
	private static final long serialVersionUID = 4384528679263052183L;

	private Class<?> baseType;
	
	public TypeCounter(Class<?> baseType) {
		this.baseType = baseType;
	}
	
	public void count(Object obj){
		Class<?> type = obj.getClass();
		if(!baseType.isAssignableFrom(type)){
			throw new RuntimeException(String.format("[%s] incorrect type: [%s] should be type or subtype of [%s]", obj,type,baseType));
		}
		this.countClass(type);
	}
	
	private void countClass(Class<?> type){
		Integer qunatity = this.get(type);
		this.put(type, qunatity == null ? 1 : qunatity + 1);
		Class<?> superClass = type.getSuperclass();
		if(superClass != null && baseType.isAssignableFrom(superClass)){
			this.countClass(superClass);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		for (Map.Entry<Class<?>, Integer> pair : this.entrySet()) {
			sb.append(pair.getKey().getSimpleName());
			sb.append("=");
			sb.append(pair.getValue());
			sb.append(",");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append("}");
		return sb.toString();
	}
}
