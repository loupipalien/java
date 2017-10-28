package com.ltchen.java.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MyFile {
	
	public static void main(String[] args) {
		MyFile myFile = new MyFile();
		String[] list = myFile.list(".", filter("\\w*\\.xml"));
		for (String str : list) {
			System.out.println(str);
		}
	}
	
	public String[] list(String path, FilenameFilter filter){
		String[] list = null;
		File filePath = new File(path);
		if(filter == null){
			list = filePath.list();
		}
		else{
			list = filePath.list(filter);
		}
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list;
	}
	
	public static FilenameFilter filter(final String regex){
		return new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);
			@Override
			public boolean accept(File dir, String name) {
				return pattern.matcher(name).matches();
			}
		};
	}
}
