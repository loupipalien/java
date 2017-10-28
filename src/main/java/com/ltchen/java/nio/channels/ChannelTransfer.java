package com.ltchen.java.nio.channels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @file : ChannelTransfer.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 代替buffer读写的一种更好的方式 
 */
public class ChannelTransfer {

	public static void main(String[] args) throws IOException {
		FileChannel in = new FileInputStream("pom.xml").getChannel();
		FileChannel out = new FileOutputStream("pom.out").getChannel();
		//将读入流转换到写出流
		in.transferTo(0, in.size(), out);
		//写出流从读入流中读取
//		out.transferFrom(in, 0, in.size());
	}
}
