package com.ltchen.java.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @file : Endians.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : ByteBuffer默认大头优先
 */
public class Endians {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.wrap(new byte[12]);
		//默认
		buffer.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(buffer.array()));
		//大头读优先
		buffer.rewind();
		buffer.order(ByteOrder.BIG_ENDIAN);
		buffer.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(buffer.array()));
		//小头读优先
		buffer.rewind();
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(buffer.array()));
		
	}
}
