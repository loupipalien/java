package com.ltchen.java.io.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @file : Directory.java
 * @date : 2017年6月26日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : Directory的local方法返回指定目录下过滤的文件或文件夹列表
 * Directory包含一个内部静态类,walk方法返回指定路径下的文件树对象
 */
public final class Directory {

	public static File[] local(File dir, final String regex){
		//匿名内部类.将解决特定问题的代码隔离并聚拢于一点
		return dir.listFiles(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);
			@Override
			public boolean accept(File dir, String name) {
				return pattern.matcher(new File(name).getName()).matches();
			}
		});
	}
	
	public static File[] local(String dirPath, final String regex){
		return local(new File(dirPath), regex);
	}

	public static class TreeInfo implements Iterable<File>{
	
		public List<File> files = new ArrayList<File>();
		public List<File> dirs = new ArrayList<File>();
		
		@Override
		public Iterator<File> iterator() {
			return files.iterator();
		}
		
		void addAll(TreeInfo other){
			files.addAll(other.files);
			dirs.addAll(other.dirs);
		}
		
		public String toString(){
			return "dirs: " + PPrint.pformat(dirs) + 
					"\n\nfiles: " + PPrint.pformat(files);
		}
		
		public static TreeInfo walk(File start, String regex){
			return recuriseDirs(start, regex);
		}
		
		public static TreeInfo walk(String start, String regex){
			return recuriseDirs(new File(start), regex);
		}
		
		public static TreeInfo walk(File start){
			return recuriseDirs(start, ".*");
		}
		
		public static TreeInfo walk(String start){
			return recuriseDirs(new File(start), ".*");
		}
		
		public static TreeInfo recuriseDirs(File startDir, String regex){
			TreeInfo result = new TreeInfo();
			for (File file: startDir.listFiles()) {
				if(file.isDirectory()){
					result.dirs.add(file);
					result.addAll(recuriseDirs(file, regex));
				}
				else{
					if(file.getName().matches(regex)){
						result.files.add(file);
					}
				}
			}
			return result;
		} 
	}
	
	public static void main(String[] args) {
		//打印出当前目录下的所有目录
		PPrint.pprint(Directory.TreeInfo.walk(".").dirs);
		//打印出当前目录下p开头的文件
		for (File file : Directory.local(".", ".*")) {
			System.out.println(file);
		}
		//打印出当前目录下S开头的java文件                         
		for (File file : Directory.local(".", "S.*\\.java")) {
			System.out.println(file);
		}
		//打印出当前目录下包含Z或z的class文件
		for (File file : Directory.local(".", ".*[Zz].*\\.class")) {
			System.out.println(file);
		}
	}
}