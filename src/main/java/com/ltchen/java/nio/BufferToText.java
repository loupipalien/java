package com.ltchen.java.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @file : BufferToText.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 缓冲器容纳的是普通的字节,为了将其转换为字符,要么在写入时进行指定编码,
 * 要么在输出时进行指定解码
 */
public class BufferToText {

	private static final int BSIZE = 1024;
	
	public static void main(String[] args) throws IOException {
		FileChannel fc = new FileOutputStream("pom.out").getChannel();
		fc.write(ByteBuffer.wrap("Some text.".getBytes()));
		fc.close();
		fc = new FileInputStream("pom.out").getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		fc.read(buffer);
		buffer.flip();
		
		//并不能正常读出,而是出现了乱码
		System.out.println(buffer.asCharBuffer());
		
		//使用系统默认的字符集解码,可正常工作(因为写入是使用了系统默认编码)
		buffer.rewind();
		String encoding = System.getProperty("file.encoding");
		System.out.println("Decoded using " + encoding + ": " + Charset.forName(encoding).decode(buffer));
		
		//在写入是使用指定编码
		fc = new FileOutputStream("pom.out").getChannel();
		//UTF-16BE可把Unicode字符集的抽象码位映射为16位长的整数的序列
		fc.write(ByteBuffer.wrap("Some text.".getBytes("UTF-16BE")));
		fc.close();
		//再次尝试读取,可正常工作
		fc = new FileInputStream("pom.out").getChannel();
		buffer.clear();
		fc.read(buffer);
		buffer.flip();
		System.out.println(buffer.asCharBuffer());
		
		//使用CharBuffer写入
		fc = new FileOutputStream("pom.out").getChannel();
		//可尝试改变分配的长度输出
		buffer = ByteBuffer.allocate(20);
		buffer.asCharBuffer().put("Some text.");
		fc.write(buffer);
		fc.close();
		fc = new FileInputStream("pom.out").getChannel();
		buffer.clear();
		fc.read(buffer);
		buffer.flip();
		System.out.println(buffer.asCharBuffer());
	}
}
