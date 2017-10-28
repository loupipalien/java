package com.ltchen.java.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * @file : TextFile.java
 * @date : 2017年6月28日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 简单读写文本
 */
public class TextFile extends ArrayList<String>{

	private static final long serialVersionUID = 3705816101364294658L;

	//讲文件内容读成一个单行文本
	public static String read(String fileName){
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName).getAbsolutePath()));
			try {
				String str;
				while((str = br.readLine()) != null){
					sb.append(str).append("\n");
				}
			} finally {
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	//写单个文件
	public static void write(String fileName, String text){
		try {
			PrintWriter pw = new PrintWriter(new File(fileName).getAbsolutePath());
			try {
				pw.print(text);
			} finally {
				pw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TextFile(String fileName, String splitter){
		super(Arrays.asList(read(fileName).split(splitter)));
		//切割时往往导致第一位置为空
		if(get(0).equals("")){
			remove(0);
		}
	}
	
	public TextFile(String fileName){
		this(fileName, "\n");
	}
	
	public void write(String fileName){
		try {
			PrintWriter pw = new PrintWriter(new File(fileName).getAbsolutePath());
			try {
				for (String str : this) {
					pw.println(str);
				}
			} finally {
				pw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String fileContent = read("pom.xml");
		write("pom.out", fileContent);
		TextFile textFile = new TextFile("pom.out");
		textFile.write("pom.out2");
		//将所有元素加入到TreeSet自然排序
		TreeSet<String> words = new TreeSet<String>(new TextFile("pom.xml", "\\W+"));
		//返回此 set 的部分视图，其元素严格小于b
		System.out.println(words.headSet("b"));
	}
}
