package com.ltchen.java.javase.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;

public class MyReadProperties {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		MyReadProperties mrp = new MyReadProperties();
		Properties prop = null;
		ResourceBundle rb = null;
		prop = MyReadProperties.getPropertyOne("/prop.properties",MyReadProperties.class);
		System.out.println("getPropertyOne:"+prop.getProperty("propName"));
		prop = MyReadProperties.getPropertyTwo("prop.properties",MyReadProperties.class);
		System.out.println("getPropertyTwo:"+prop.getProperty("propName"));
		prop = MyReadProperties.getPropertyThree("prop.properties",MyReadProperties.class);
		System.out.println("getPropertyThree:"+prop.getProperty("propName"));
		rb = MyReadProperties.getResourceBundle("prop");
		System.out.println(rb.getString("propName"));
		

	}

	public static Properties getPropertyOne(String propPath, Class<?> clazz){
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(clazz.getResourceAsStream(propPath));
		try {
			prop.load(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	public static Properties getPropertyTwo(String propPath, Class<?> clazz){
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(clazz.getClassLoader().getResourceAsStream(propPath));
		try {
			prop.load(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	public static Properties getPropertyThree(String propPath, Class<?> clazz){
		Properties prop = new Properties();
//		InputStream in = new BufferedInputStream(clazz.getClassLoader().getResourceAsStream(propPath));
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/bin/"+propPath));
			prop.load(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	public static ResourceBundle getResourceBundle(String propPath){
		ResourceBundle rb = ResourceBundle.getBundle(propPath);
		return rb;
	}
}
