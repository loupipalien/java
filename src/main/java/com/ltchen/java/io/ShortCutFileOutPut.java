package com.ltchen.java.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * @file : ShortCutFileOutPut.java
 * @date : 2017年6月28日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 基础文件输出简化版
 */
public class ShortCutFileOutPut {

	private static String fileName = "pom.out";
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(BufferedInputFile.read("pom.xml")));
		//简化版,这里仍然是使用的缓冲,具体见PrintWriter的构造函数
		PrintWriter pw = new PrintWriter(fileName);
		int lineCount = 1;
		String str;
		while ((str = br.readLine()) != null) {
			pw.println(lineCount++ + ":" + str);
		}
		pw.close();
		System.out.println(BufferedInputFile.read(fileName));
	}
}
