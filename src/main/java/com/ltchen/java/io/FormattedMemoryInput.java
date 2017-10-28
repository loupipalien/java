package com.ltchen.java.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @file : FormattedMemoryInput.java
 * @date : 2017年6月26日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 读取格式化的数据可以使用DataInputStream,这是一个面向字节的I/O类
 */
public class FormattedMemoryInput {

	public static void main(String[] args) throws IOException {
		DataInputStream dis = new DataInputStream(
								new ByteArrayInputStream(
								BufferedInputFile.read("pom.xml").getBytes()));
		/*
		 * 用DataInputStream的readByte()方法读取,因为任何字节的值都是合法的结果,
		 * 因此不能根据返回值判定输入是否结束,可使用available()方法查看还有多少可读取的字节
		 */
		while(dis.available() != 0){
			System.out.print((char)dis.readByte());
		}
	}
}
