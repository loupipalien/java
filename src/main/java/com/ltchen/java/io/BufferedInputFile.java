package com.ltchen.java.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @file : BufferedInputFile.java
 * @date : 2017年6月26日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 缓冲输入文件,使用Reader按字符读入文件
 */
public class BufferedInputFile {

	public static String read(String fileName) throws IOException{
		//按行读入
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String str;
		StringBuilder sb = new StringBuilder();
		while((str =  br.readLine()) != null){
			//readline()方法会去除换行符
			sb.append(str).append("\n");
		}
		br.close();
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(BufferedInputFile.read("pom.xml"));
	}
}
