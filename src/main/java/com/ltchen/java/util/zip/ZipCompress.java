package com.ltchen.java.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @file : ZipCompress.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用Zip进行简单压缩(支持多文件)
 */
public class ZipCompress {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("pom.zip");
		CheckedOutputStream cos = new CheckedOutputStream(fos, new Adler32());
		ZipOutputStream zos = new ZipOutputStream(cos);
		BufferedOutputStream bos = new BufferedOutputStream(zos);
		//压缩包注释,ZipInputStream却没有对于方法拿到
		zos.setComment("A test of Java Zipping.");
		System.out.println("Writing file.");
		BufferedReader br = new BufferedReader(new FileReader("pom.xml"));
		ZipEntry ze = new ZipEntry("pom.xml");
		//文件注释
		ze.setComment("file comment");
		//可加入多个文件
		zos.putNextEntry(ze);
		int i;
		while((i = br.read()) != -1){
			bos.write(i);
		}
		br.close();
		bos.close();
		//当输入输出流关闭后才可校验
		System.out.println("CkeckSum: " + cos.getChecksum().getValue());
		
		System.out.println("Reading file.");
		FileInputStream fis = new FileInputStream("pom.zip");
		CheckedInputStream cis = new CheckedInputStream(fis, new Adler32());
		ZipInputStream zis = new ZipInputStream(cis);
		BufferedInputStream bis = new BufferedInputStream(zis);
		ZipEntry ze2;
		while((ze2 = zis.getNextEntry()) != null){
			System.out.println("File Comment: " + ze.getComment());
			System.out.println("Reading File: " + ze2);
			int j;
			while((j = bis.read()) != -1){
				System.out.write(j);
			}
		}
		System.out.println("CkeckSum: " + cis.getChecksum().getValue());
		bis.close();
		ZipFile zf = new ZipFile("pom.zip");
		Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) zf.entries();
		while(e.hasMoreElements()){
			ZipEntry ze3 = e.nextElement();
			System.out.println("File: " + ze3);
		}
	}
}
