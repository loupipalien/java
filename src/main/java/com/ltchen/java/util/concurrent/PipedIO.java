package com.ltchen.java.util.concurrent;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PipedIO {
	public static void main(String[] args) throws Exception {
		Sender sender =  new Sender();
		Receiver receiver = new Receiver(sender);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		Thread.sleep(3000);
		exec.shutdownNow();
	}
}
class Sender implements Runnable{
	private Random random = new Random(47);
	private PipedWriter out = new PipedWriter();
	
	public PipedWriter getOut() {
		return out;
	}
	@Override
	public void run() {
		try {
			while(true){
				for (char c = 'A'; c <= 'Z'; c++) {
					out.write(c);
					Thread.sleep(random.nextInt(500));
				}
			}
		} catch (IOException e) {
			System.out.println(e + " Sender write exception.");
			//e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println(e + " Sender sleep interrupted.");
			//e.printStackTrace();
		}
	}
}
class Receiver implements Runnable{
	private PipedReader in;
	public Receiver(Sender sender) throws IOException{
		in = new PipedReader(sender.getOut());
	}
	@Override
	public void run() {
		try {
			while(true){
				//Blocks until characters are there
				System.out.println("Read: " + (char)in.read() + ",");
			}
		} catch (IOException e) {
			System.out.println(e + " Receiver read exception.");
			//e.printStackTrace();
		}
	}
}