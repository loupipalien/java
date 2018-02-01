package com.ltchen.java.lang;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author: ltchen
 * @date: 2018/2/1
 * @reference: https://www.cnblogs.com/slyfox/p/7272048.html
 */
public class RuntimeDemo {

    public static void main(String[] args) {
        // getFreeMemory();
        // getAvailableProcessors();
        // command("ipconfig");
        // 添加关闭 jvm 时的钩子
        addShutdownHook();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: " + Runtime.getRuntime().toString());
        System.out.println("Main: " + Runtime.getRuntime().hashCode());
        System.out.println("Main: I am exit!");

    }

    /**
     * 获取当前jvm的内存信息,返回的值是 字节为单位
     */
    public static void getFreeMemory() {
        // 获取可用内存
        long value = Runtime.getRuntime().freeMemory();
        System.out.println("可用内存为: "+value/1024/1024+"MB");
        // 获取 jvm 的内存总数量，该值会不断的变化
        long  totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("全部内存为: "+totalMemory/1024/1024+"MB");
        // 获取 jvm 可以最大使用的内存数量，如果没有被限制 返回 Long.MAX_VALUE;
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("可用最大内存为: "+maxMemory/1024/1024+"MB");
    }

    /**
     * 获取 jvm 可用的处理器核心的数量
     */
    public static void getAvailableProcessors() {
        int value = Runtime.getRuntime().availableProcessors();
        System.out.println("处理器个数: " + value);
    }

    /**
     * 执行系统命令
     */
    public static void command(String command) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加关闭 jvm 时的钩子
     * http://blog.csdn.net/xichenguan/article/details/40895719
     */
    public static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Hook: I am still alive!");
                try {
                    Thread.sleep(1000);
                    System.out.println("Hook: " + Runtime.getRuntime().toString());
                    System.out.println("Hook: " + Runtime.getRuntime().hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
