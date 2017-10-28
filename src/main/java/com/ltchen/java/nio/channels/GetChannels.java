package com.ltchen.java.nio.channels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @file : GetChannels.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : nio包中使用通道和缓冲器 类似操作系统执行I/O的方式提高读写速度。通道类似于包含数据的矿,
 * 而缓冲器则类似于派送到矿中的卡车,程序直接和缓冲器交互而不是通道.io包中有三个类被修改了用于产生FileChannel,
 * 这三个类分别时FileInputStream,FileOutputStram,RandomAccessFile
 */
public class GetChannels {

	private static final int BSIZE = 1024;
	
	public static void main(String[] args) throws IOException {
		//写文件
		FileChannel fc = new FileOutputStream("pom.out").getChannel();
		fc.write(ByteBuffer.wrap("Some text.".getBytes()));
		fc.close();
		//在文件最后追加内容
		fc = new RandomAccessFile("pom.out", "rw").getChannel();
		//移动到文件最后
		fc.position(fc.size());
		fc.write(ByteBuffer.wrap("Some append.".getBytes()));
		fc.close();
		fc = new FileInputStream("pom.out").getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		fc.read(buffer);
		buffer.flip();
		while(buffer.hasRemaining()){
			System.out.print((char)buffer.get());
		}
	}
}
