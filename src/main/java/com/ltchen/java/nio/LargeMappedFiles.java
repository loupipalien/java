package com.ltchen.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @file : LargeMappedFiles.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : MappedByteBuffer由ByteBuffer继承而来,用于做内存映射.内存映射文件允许创建和修改
 * 那些因为太大而不能放到内存中的文件,有了内存映射文件就可以假定整个文件都在内存当中,
 * 而且可以完全将其当作一个非常大的数组访问,这种方法极大的简化了修改文件的代码.
 */ 
public class LargeMappedFiles {

	//128MB
	private static int length = 0x8FFFFFF;
	
	public static void main(String[] args) throws IOException{
		MappedByteBuffer out = new RandomAccessFile("pom.out", "rw").getChannel()
								.map(FileChannel.MapMode.READ_WRITE, 0, length);
		for (int i = 0; i < length; i++) {
			out.put((byte)'x');
		}
		System.out.println("Finished writing.");
		for (int i = length/2; i < length/2 + 6; i++) {
			System.out.print((char)out.get(i));
		}
	}
}
