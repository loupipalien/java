package com.ltchen.java.jvm.chapter8;

/**
 * @author : ltchen
 * @date : 2018/4/6
 * @desc : 方法静态解析演示
 * 使用 javap -verbose StaticResolution 命令查看编译的字节码文件
 * ...
 *    public static void main(java.lang.String[]);
 *      descriptor: ([Ljava/lang/String;)V
 *      flags: ACC_PUBLIC, ACC_STATIC
 *      Code:
 *      stack=0, locals=1, args_size=1
 *      0: invokestatic  #5                  // Method sayHello:()V
 *      3: return
 *      LineNumberTable:
 *      line 15: 0
 *      line 16: 3
 *      LocalVariableTable:
 *      Start  Length  Slot  Name   Signature
 *      0       4     0  args   [Ljava/lang/String;
 * ...
 */
public class StaticResolution {

    public static void sayHello() {
        System.out.println("Hello World");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }

}
