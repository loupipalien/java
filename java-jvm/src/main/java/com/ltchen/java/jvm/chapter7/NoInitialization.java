package com.ltchen.java.jvm.chapter7;

/**
 * @author : ltchen
 * @date : 2018/3/26
 * @desc : 被动使用类字段演示 (JVM 参数 -XX:+TraceClassLoading 可打印加载日志)
 * 1. 通过子类因用户父类的静态字段, 不会导致子类初始化
 * 2. 通过数组定义来引用类, 不会触发此类的初始化
 * 3. 常量在编译阶段会存入调用类的常量池中, 本质上并没有直接引用到定义常量的类, 因此不会触发定义常量的类的初始化
 */
public class NoInitialization {

    public static void main(String[] args) {
        // 1 从加载日志中可看到 SubClass 被加载的日志, 但并未输出 static 代码块的内容, 大概因为 JDK 版本不同, 当前使用为 JKD 1.8
        System.out.println(SubClass.value);

        // 2
        SuperClass[] superClasses = new SuperClass[10];

        // 3
        System.out.println(ConstClass.HELLO_WORLD);

    }
}

class SuperClass {
    public static int value = 123;

    static {
        System.out.println("SuperClass init!");
    }
}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
    }
}

class ConstClass {
    public static final String HELLO_WORLD = "hello world!";
    static {
        System.out.println("ConstClass init!");
    }
}