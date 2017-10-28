package com.ltchen.java.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @file : Redirecting.java
 * @date : 2017年6月30日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 将标准输入输出流重定向,标准输入输出时字节流不是字符流
 */
public class Redirecting {

	public static void main(String[] args) throws IOException {
		PrintStream stdout = System.out;
		InputStream stdin = System.in;
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("pom.xml"));
		PrintStream ps = new PrintStream(new FileOutputStream("pom.out"));
		System.setIn(bis);
		System.setOut(ps);
		System.setErr(ps);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		while((str = br.readLine()) != null){
			System.out.println(str);
		}
		ps.close();
		System.setIn(stdin);
		System.setOut(stdout);
	}
}
