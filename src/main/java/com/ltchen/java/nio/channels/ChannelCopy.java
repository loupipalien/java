package com.ltchen.java.nio.channels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @file : ChannelCopy.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : nio的目标是快速大量的移动数据,因此缓冲器的大小便十分重要.通常经过经验或时间分配一个较好性能的大小
 */
public class ChannelCopy {

	private static final int BSIZE = 1024;
	
	public static void main(String[] args) throws IOException {
		FileChannel in = new FileInputStream("pom.xml").getChannel();
		FileChannel out = new FileOutputStream("pom.out").getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		//产生一个于操作系统具有更高耦合性的"直接"缓冲器,这样可能会达到更好的速度,但这种分配开支通常会更大
//		ByteBuffer buffer = ByteBuffer.allocateDirect(BSIZE);
		while(in.read(buffer) != -1){
			//调用read()方法告知FileChannel向ByteBuffer中存储数据时,必须调用flip()让其做好读取字节的准备
			buffer.flip();
			out.write(buffer);
			//write()后信息仍在buffer中,调用clear()方法对所有的内部指针重新安排,以便下一次向缓冲器中填充数据
			buffer.clear();
		}
	}
}
