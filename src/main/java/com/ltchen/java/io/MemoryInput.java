package com.ltchen.java.io;

import java.io.IOException;
import java.io.StringReader;

/**
 * @file : MemoryInput.java
 * @date : 2017年6月26日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 将文件内容读入内存中,输出到控制台
 */
public class MemoryInput {

	public static void main(String[] args) throws IOException {
		StringReader sr = new StringReader(BufferedInputFile.read("pom.xml"));
		int c; 
		while((c = sr.read()) != -1){
			System.out.print((char)c);
		}
		sr.close();
	}
}
