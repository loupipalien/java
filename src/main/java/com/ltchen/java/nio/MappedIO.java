package com.ltchen.java.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @file : MappedIO.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 尽管IO流在使用nio后性能提高了,但是"映射文件访问"可以更显著的加快速度
 */
public class MappedIO {

	private static int numOfInts = 4000000;
	private static int numOfUnbufferInts = 2000000;
	
	private abstract static class Tester{
		private String name;
		public Tester(String name) {
			this.name = name;
		}
		public void runTest(){
			System.out.println(name + ": ");
			try {
				long start = System.nanoTime();
				this.test();
				double duration = System.nanoTime() - start;
				System.out.format("%.2f\n",duration/1.0e9);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//模板方法
		public abstract void test() throws IOException;
	}
	
	private static Tester[] tests = {
		new Tester("Stream Write"){
			@Override
			public void test() throws IOException {
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("pom.out")));
				for (int i = 0; i < numOfInts; i++) {
					dos.writeInt(i);
				}
				dos.close();
			}
		},
		new Tester("Mapped Write"){
			@Override
			public void test() throws IOException {
				FileChannel fc = new RandomAccessFile("pom.out","rw").getChannel();
				IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
				for (int i = 0; i < numOfInts; i++) {
					ib.put(i);
				}
				fc.close();
			}
		},
		new Tester("Stream Read"){
			@Override
			public void test() throws IOException {
				DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("pom.out")));
				for (int i = 0; i < numOfInts; i++) {
					dis.readInt();
				}
				dis.close();
			}
		},
		new Tester("Mapped Read"){
			@Override
			public void test() throws IOException {
				FileChannel fc = new FileInputStream("pom.out").getChannel();
				IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
				for (int i = 0; i < numOfInts; i++) {
					ib.get();
				}
				fc.close();
			}
		},
		new Tester("Stream Read/Write"){
			@Override
			public void test() throws IOException {
				RandomAccessFile raf = new RandomAccessFile("pom.out","rw");
				raf.writeInt(1);
				for (int i = 0; i < numOfUnbufferInts; i++) {
					raf.seek(raf.length() - 4);
					raf.writeInt(raf.read());
				}
				raf.close();
			}
		},
		new Tester("Mapped Read/Write"){
			@Override
			public void test() throws IOException {
				FileChannel fc = new RandomAccessFile("pom.out","rw").getChannel();
				IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
				ib.put(0);
				for (int i = 1; i < numOfUnbufferInts; i++) {
					ib.put(ib.get(i - 1));
				}
				fc.close();
			}
		}
	};
	
	public static void main(String[] args) {
		for (Tester tester : tests) {
			tester.runTest();
		}
	}
}
