package com.ltchen.java.nio.channels;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

/**
 * @file : FileLocking.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 将文件加锁.
 * 竞争同一文件的两个线程可能不在同一个java虚拟机中;或者是一个是java进程,另一个是操作系统的其他本地线程
 * 文件锁对其他操作系统进程是可见的,因为java的文件加锁直接映射到了本地操作系统的加锁工具
 */
public class FileLocking {

	public static void main(String[] args) throws IOException, InterruptedException {
		FileOutputStream fos = new FileOutputStream("pom.out");
		//试图获取文件锁
		FileLock fl = fos.getChannel().tryLock();
		if(fl != null){
			System.out.println("Locked File!");
			Thread.sleep(100);
			//释放锁
			fl.release();
			System.out.println("Released Lock!");
		}
		fos.close();
	}
}
