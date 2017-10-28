package com.ltchen.java.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * @file : BasicFileOutPut.java
 * @date : 2017年6月28日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 基础文件输出
 */
public class BasicFileOutPut {

	private static String fileName = "pom.out";
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
							new StringReader(BufferedInputFile.read("pom.xml")));
		PrintWriter pw = new PrintWriter(
						 new BufferedWriter(new FileWriter(fileName)));
		int lineCount = 1;
		String str;
		while((str = br.readLine()) != null){
			pw.println(lineCount++ + ":" + str);
		}
		pw.close();
		System.out.println(BufferedInputFile.read(fileName));
	}
}
