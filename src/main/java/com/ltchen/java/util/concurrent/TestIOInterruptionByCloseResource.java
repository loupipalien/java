package com.ltchen.java.util.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @file : TestIOInterruptionByCloseResource.java
 * @date : 2017年6月10日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试通过关闭底层资源中断IO阻塞(不懂systemInput的中断输出)
 */
public class TestIOInterruptionByCloseResource {

	public static void main(String[] args) throws InterruptedException, IOException{
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(8080);
		InputStream socketInput = new Socket("localhost",8080).getInputStream();
		InputStream systemInput = System.in;
		exec.execute(new IOBlocked(socketInput));
		exec.execute(new IOBlocked(systemInput));
		Thread.sleep(100);
		System.out.println("Shutting down all threads.");
		exec.shutdownNow();

		Thread.sleep(1);
		System.out.println("Closing " + socketInput.getClass().getName());
		socketInput.close();
		Thread.sleep(1);
		System.out.println("Closing " + systemInput.getClass().getName());
		systemInput.close();
	}
}
