package com.ltchen.java.nio;

import java.nio.ByteBuffer;

/**
 * @file : GetBasicObject.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : ByteBuffer虽然只能保存字节类型数据,但是它也具有从其所容纳的字节中产生出各种基本类型值的方法
 */
public class GetBasicObject {

	private static final int BSIZE = 1024;
	
	public static void main(String[] args) {

		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		
		int i = 0;
		while(i++ < buffer.limit()){
			if(buffer.get() != 0){
				System.out.println("nonzero");
			}
		}
		System.out.println("i = " + i);
		buffer.rewind();
		//存储和读取char类型
		buffer.asCharBuffer().put("Howdy!");
		char c;
		while((c = buffer.getChar()) != 0 ){
			System.out.print(c + " ");
		}
		System.out.println();
		buffer.rewind();
		//存储和读取short类型
		buffer.asShortBuffer().put((short)2132133);
		System.out.println(buffer.getShort());
		buffer.rewind();
		//存储和读取int类型
		buffer.asIntBuffer().put(212334123);
		System.out.println(buffer.getInt());
		buffer.rewind();
		//存储和读取long类型
		buffer.asLongBuffer().put(2131231231L);
		System.out.println(buffer.getLong());
		buffer.rewind();
		//存储和读取float类型
		buffer.asFloatBuffer().put((float) 123213.21);
		System.out.println(buffer.getFloat());
		buffer.rewind();
		//存储和读取double类型
		buffer.asDoubleBuffer().put(123213.21);
		System.out.println(buffer.getDouble());
		buffer.rewind();
	}
}
