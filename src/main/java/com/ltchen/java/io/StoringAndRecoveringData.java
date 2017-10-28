package com.ltchen.java.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @file : StoringAndRecoveringData.java
 * @date : 2017年6月28日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : DataOutputStream在写入int,double等数字类型时使用ASCII码写到流中,并可时候用writeUTF方法写
 * UTF-8编码的字符串写入文件,并只有readUTF()可以正确的恢复java-utf字符串。但正确恢复之前需要知道流中数据项正确的位置,
 * 使用正确的read方法读取出来
 */
public class StoringAndRecoveringData {

	private static String fileName = "pom.out";
	public static void main(String[] args) throws IOException {
		DataOutputStream dos = new DataOutputStream(
				               new BufferedOutputStream(
				               new FileOutputStream(fileName)));
		dos.writeDouble(3.14159);
		//只有readUTF()可以正确的恢复java-utf字符串
		dos.writeUTF("That is Pi");
		dos.writeDouble(1.41413);
		dos.writeUTF("2的平方时4");
		dos.close();
		DataInputStream dis = new DataInputStream(
				              new BufferedInputStream(
				              new FileInputStream(fileName)));
		System.out.println(dis.readDouble());
		System.out.println(dis.readUTF());
		System.out.println(dis.readDouble());
		System.out.println(dis.readUTF());
		dis.close();
	}
}
