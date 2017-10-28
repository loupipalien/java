package com.ltchen.java.nio.channels;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @file : MappedFilesLocking.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 对内存映射文件部分加锁
 */
public class MappedFilesLocking {

	//128MB
	private static final int LENGHT = 0x8FFFFFF; 
	private static FileChannel fc;
	
	public static void main(String[] args) throws IOException {
		fc = new RandomAccessFile("pom.out", "rw").getChannel();
		MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, LENGHT);
		for (int i = 0; i < LENGHT; i++) {
			mbb.put((byte)'x');
		}
		new LockAndModify(mbb, 0, 0 + LENGHT/3);
		new LockAndModify(mbb, 0 + LENGHT/2, LENGHT/2 + LENGHT/4);
	}
	
	private static class LockAndModify extends Thread{
		private ByteBuffer bb;
		private int start,end;
		
		public LockAndModify(ByteBuffer bb, int start, int end) {
			this.start = start;
			this.end = end;
			bb.limit(end);
			bb.position(start);
			this.bb = bb.slice();
			start();
		}
		
		public void run(){
			try {
				//获得独占锁
				FileLock fl = fc.lock(start, end, false);
				System.out.println("Locked: " + start + " to " + end);
				//修改文件
				while(bb.position() < bb.limit() - 1){
					bb.put((byte)(bb.get() + 1));
				}
				//释放锁
				fl.release();
				System.out.println("Released: " + start + " to " + end);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}