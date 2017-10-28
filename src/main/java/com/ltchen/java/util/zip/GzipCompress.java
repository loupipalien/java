package com.ltchen.java.util.zip;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @file : GzipCompress.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用Gzip进行简单压缩
 */
public class GzipCompress {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("pom.xml"));
		BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("pom.gz")));
		System.out.println("Writing file.");
		int i;
		while((i = br.read()) != -1){
			bos.write(i);
		}
		br.close();
		bos.close();
		System.out.println("Reading file.");
		BufferedReader br2 = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("pom.gz"))));
		String str;
		while((str = br2.readLine()) != null){
			System.out.println(str);
		}
	}
}
