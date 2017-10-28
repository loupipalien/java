package com.ltchen.java.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @file : UsingBuffers.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 交换相邻字符(偶数个字符):对CharBuffer中的字符进行编码(scramble)和译码(unscramble)
 */
public class UsingBuffers {

	private static void symmetricScrable(CharBuffer buffer){
		while(buffer.hasRemaining()){
			buffer.mark();
			char c1 = buffer.get();
			char c2 = buffer.get();
			buffer.reset();
			buffer.put(c2).put(c1);
		}
	}
	
	public static void main(String[] args) {
		char[] data = "UsingBuffers".toCharArray();
		ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
		CharBuffer cb = bb.asCharBuffer();
		cb.put(data);
		System.out.println(cb.rewind());
		symmetricScrable(cb);
		System.out.println(cb.rewind());
		symmetricScrable(cb);
		System.out.println(cb.rewind());
	}
}
