package com.ltchen.java.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * @file : ViewBuffers.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 基本数据类型的缓冲器是基于ByteBuffer的
 * bytes:  |0|0|0|0|0|0|0|97|
 * chars:  |   |   |   | a  |
 * shorts: | 0 | 0 | 0 | 97 |
 * ints:   |   0   |  97    |
 * floats: |  0.0  |1.36E-43|
 * longs:  |      97        | 
 * doubles:|   4.8E-322     |
 */
public class ViewBuffers {

	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[]{0,0,0,0,0,0,0,'a'});
		//ByteBuffer
		bb.rewind();
		System.out.println("ByteBuffer:");
		while(bb.hasRemaining()){
			System.out.print(bb.position() + " -> " + bb.get());
			if(bb.hasRemaining()){
				System.out.print(", ");
			}
		}
		System.out.println();
		//CharBuffer
		CharBuffer cb = ((ByteBuffer)bb.rewind()).asCharBuffer();
		System.out.println("CharBuffer:");
		while(cb.hasRemaining()){
			System.out.print(cb.position() + " -> " + cb.get());
			if(cb.hasRemaining()){
				System.out.print(", ");
			}
		}
		System.out.println();
		//ShortBuffer
		ShortBuffer sb = ((ByteBuffer)bb.rewind()).asShortBuffer();
		System.out.println("ShortBuffer:");
		while(sb.hasRemaining()){
			System.out.print(sb.position() + " -> " + sb.get());
			if(sb.hasRemaining()){
				System.out.print(", ");
			}
		}
		System.out.println();
		//IntBuffer
		IntBuffer ib = ((ByteBuffer)bb.rewind()).asIntBuffer();
		System.out.println("IntBuffer:");
		while(ib.hasRemaining()){
			System.out.print(ib.position() + " -> " + ib.get());
			if(sb.hasRemaining()){
				System.out.print(", ");
			}
		}
		System.out.println();
		//FloatBuffer
		FloatBuffer fb = ((ByteBuffer)bb.rewind()).asFloatBuffer();
		System.out.println("FloatBuffer:");
		while(fb.hasRemaining()){
			System.out.print(fb.position() + " -> " + fb.get());
			if(fb.hasRemaining()){
				System.out.print(", ");
			}
		}
		System.out.println();
		//LongBuffer
		LongBuffer lb = ((ByteBuffer)bb.rewind()).asLongBuffer();
		System.out.println("LongBuffer:");
		while(lb.hasRemaining()){
			System.out.print(lb.position() + " -> " + lb.get());
			if(lb.hasRemaining()){
				System.out.print(", ");
			}
		}
		System.out.println();
		//DoubleBuffer
		DoubleBuffer db = ((ByteBuffer)bb.rewind()).asDoubleBuffer();
		System.out.println("DoubleBuffer:");
		while(db.hasRemaining()){
			System.out.print(db.position() + " -> " + db.get());
			if(db.hasRemaining()){
				System.out.print(", ");
			}
		}
		System.out.println();
	}
}
