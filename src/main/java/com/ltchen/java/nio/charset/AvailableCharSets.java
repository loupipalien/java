package com.ltchen.java.nio.charset;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * @file : AvailableCharSets.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 获取系统中可用的字符集
 */
public class AvailableCharSets {

	public static void main(String[] args) {
		SortedMap<String, Charset> charSets = Charset.availableCharsets();
		Iterator<String> it = charSets.keySet().iterator();
		while(it.hasNext()){
			String csName = it.next();
			System.out.print(csName);
			Iterator aliases = charSets.get(csName).aliases().iterator();
			if(aliases.hasNext()){
				System.out.print(": ");
			}
			while(aliases.hasNext()){
				System.out.print(aliases.next());
				if(aliases.hasNext()){
					System.out.print(", ");
				}
			}
			System.out.println();
		}
	}
}
