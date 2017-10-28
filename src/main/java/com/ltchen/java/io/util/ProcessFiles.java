package com.ltchen.java.io.util;

import java.io.File;
import java.io.IOException;

/**
 * @file : ProcessFiles.java
 * @date : 2017年6月28日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 处理文件类,使用内部接口的方式达成策略
 */
public class ProcessFiles {

	//定义内部接口(策略模式)
	public interface Strategy{
		void process(File file);
	}
	
	private Strategy strategy;
	private String ext;
	
	public ProcessFiles(Strategy strategy, String ext){
		this.strategy = strategy;
		this.ext = ext;
	}
	
	public void start(String[] args){
		try {
			if(args.length == 0){
					processDirectoryTree(new File("."));
			}
			else{
				for (String arg : args) {
					File fileArg = new File(arg);
					if(fileArg.isDirectory()){
						processDirectoryTree(fileArg);
					}
					else{
						if(!arg.endsWith("." + ext)){
							arg += "." + ext;
						}
						strategy.process(new File(arg).getCanonicalFile());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processDirectoryTree(File root) throws IOException{
		for (File file : Directory.TreeInfo.walk(root.getAbsolutePath(),".*\\." + ext)) {
			strategy.process(file.getCanonicalFile());
		}
	}
	
	public static void main(String[] args) {
		String[] args1 = {"D:\\Git\\Repository\\javase\\src\\main\\java\\com\\ltchen\\java\\io\\MyFile"};
		args = args1;
		new ProcessFiles(new Strategy() {
			@Override
			public void process(File file) {
				System.out.println(file);
			}
		}, "java").start(args);
	}
}
