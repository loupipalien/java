package com.ltchen.java.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @file : BinaryFile.java
 * @date : 2017年6月28日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 简单读写二进制文件 
 */
public class BinaryFile {

	public static byte[] read(File bFile) throws IOException{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(bFile));
		try {
			byte[] bytes = new byte[bis.available()];
			bis.read(bytes);
			return bytes;
		} finally {
			bis.close();
		}
	}
	
	public static byte[] read(String bFileName){
		return read(new File(bFileName).getAbsolutePath());
	}
}
