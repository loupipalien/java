package com.ltchen.java.jdk.feature;

/**
 * @author : ltchen
 * @date : 2017/10/28
 * @desc : JDK8接口新特性: 支持静态方法和默认方法
 */

public interface JDK8Interface {
    // static修饰符定义静态方法
    static void staticMethod() {
        System.out.println("接口中的静态方法");
    }

    default void defaultMethod() {
        System.out.println("接口中的默认方法");
    }

}