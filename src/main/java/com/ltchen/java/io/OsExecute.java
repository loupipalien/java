package com.ltchen.java.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @file : OsExecute.java
 * @date : 2017年7月2日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 在java内部执行其他操作系统的程序,并将标准输出和标准错误重定向到另一个文件
 */
public class OsExecute {

	public static void command(String command){
		boolean hasError = false;
		try {
			Process process = new ProcessBuilder(command.split(" ")).start();
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String str;
			while((str = in.readLine()) != null){
				System.out.println(str);
			}
			BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			//当调用有问题则会返回非零值并报告错误
			while((str = err.readLine()) != null){
				System.out.println(str);
				hasError = true;
			}
		} catch (IOException e) {
			if(!command.startsWith("CMD /C")){
				command("CMD /C" + command);
			}
			else{
				throw  new RuntimeException(e);
			}
		}
		if(hasError){
			System.out.println("Errors executing " + command);
		}
	}
	
	public static void main(String[] args) {
		OsExecute.command("javap OsExecute");
	}
}
