package com.ltchen.java.jvm.chapter8;

import java.io.Serializable;

/**
 * @author : ltchen
 * @date : 2018/4/6
 * @desc : 重载方法匹配优先级
 * 匹配顺序: char -> int -> long -> [float -> double ->] Character -> Serializable|Comparable<Character> -> Object -> char...|Character...
 */
public class Overload {

    public static void sayHello(Object obj) {
        System.out.println("Hello, Object!");
    }

    public static void sayHello(int arg) {
        System.out.println("Hello, int!");
    }

    public static void sayHello(long arg) {
        System.out.println("Hello, long!");
    }

    public static void sayHello(Character arg) {
        System.out.println("Hello, Character!");
    }

    public static void sayHello(char arg) {
        System.out.println("Hello, char!");
    }

    /**
     * 基本类型和包装类型的数组参数会冲突
     * @param arg
     */
    public static void sayHello(char... arg) {
        System.out.println("Hello, char...!");
    }

    /**
     * Character 的同级接口会冲突
     * @param arg
     */
    public static void sayHello(Serializable arg) {
        System.out.println("Hello, Serializable!");
    }

    public static void main(String[] args) {
        sayHello('a');
    }

}
