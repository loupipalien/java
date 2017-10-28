package com.ltchen.java.javase.lang.string;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @file : StringBufferVsStringBuilder.java
 * @date : 2017年4月9日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试StringBuffer和StringBuilder的性能
 * 
 */
public class StringBufferVsStringBuilder {

	public static String BASEINFO = "ltchen";  
    public static final int COUNT = 2000000;  
  
    /** 
     * 执行StringBuffer赋值测试 
     */  
    public static void doStringBuffer() {  
  
        StringBuffer sb = new StringBuffer(BASEINFO);  
        long starttime = System.currentTimeMillis();  
        for (int i = 0; i < COUNT; i++) {  
            sb = sb.append("miss");  
        }  
        long endtime = System.currentTimeMillis();  
        System.out.println((endtime - starttime) + " millis has costed when used StringBuffer.");  
    }  
  
    /** 
     * 执行StringBuilder赋值测试 
     */  
    public static void doStringBuilder() {  
  
        StringBuilder sb = new StringBuilder(BASEINFO);  
        long starttime = System.currentTimeMillis();  
        for (int i = 0; i < COUNT; i++) {  
            sb = sb.append("miss");  
        }  
        long endtime = System.currentTimeMillis();  
        System.out.println((endtime - starttime) + " millis has costed when used StringBuilder.");  
    }  
  
    /** 
     * 执行StringBuffer遍历赋值测试
     * @param list 
     */  
    public static void doStringBufferList(List<String> list) {  
        StringBuffer sb = new StringBuffer();  
        long starttime = System.currentTimeMillis();  
        for (String string : list) {  
            sb.append(string);  
        }  
        long endtime = System.currentTimeMillis();  
        System.out.println(sb.toString() + "buffer cost:" + (endtime - starttime) + " millis");  
    }  
  
    /** 
     * 执行tringBuilder遍历赋值测试
     * @param list 
     */  
    public static void doStringBuilderList(List<String> list) {  
        StringBuilder sb = new StringBuilder();  
        long starttime = System.currentTimeMillis();  
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {  
            sb.append(iterator.next());  
        }  
  
        long endtime = System.currentTimeMillis();  
        System.out.println(sb.toString() + "builder cost:" + (endtime - starttime) + " millis");  
    }  
  
    public static void main(String[] args) {  
        doStringBuffer();  
        doStringBuilder();  
  
        List<String> list = new ArrayList<String>();  
        list.add(" I ");  
        list.add(" like ");  
        list.add(" BeiJing ");  
        list.add(" tian ");  
        list.add(" an ");  
        list.add(" men ");  
        list.add(" . ");  
  
        doStringBufferList(list);  
        doStringBuilderList(list);  
    }  
}
