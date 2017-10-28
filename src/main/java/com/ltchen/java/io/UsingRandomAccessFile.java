package com.ltchen.java.io;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @file : UsingRandomAccessFile.java
 * @date : 2017年6月28日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : RandomAccessFile可随机读写文件,并具有读取基本数据类型和UTF-8的字符串
 */
public class UsingRandomAccessFile {

	private static String fileName = "pom.out";
	public static void dispaly() throws IOException{
		RandomAccessFile raf = new RandomAccessFile(fileName, "r");
		for (int i = 0; i < 7; i++) {
			System.out.println("Value "  + i + ": " + raf.readDouble());
		}
		System.out.println(raf.readUTF());
		raf.close();
	}
	
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		for (int i = 0; i < 7; i++) {
			raf.writeDouble(i * 1.414);
		}
		raf.writeUTF("The end of the file");
		raf.close();
		dispaly();
		raf = new RandomAccessFile(fileName, "rw");
		raf.seek(5 * 8);
		raf.writeDouble(47.0001);
		raf.close();
		dispaly();
	}
}
