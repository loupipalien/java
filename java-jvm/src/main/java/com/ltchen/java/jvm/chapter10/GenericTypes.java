package com.ltchen.java.jvm.chapter10;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ltchen
 * @date : 2018/4/19
 * @desc : 当泛型遇见重载 (请使用 JDK 1.6 编译), 自动装箱, 条件编译
 */
public class GenericTypes {

    /*
     * 方法签名相同, 编译不通过
    public static void method(List<String> list) {
        System.out.println("invoke method(List<String> list)");
    }

    public static void method(List<Integer> list) {
        System.out.println("invoke method(List<Integer> list)");
    }
     */

    /*
     * 方法签名相同, 返回值不同, JDK 8 编译也不通过
    public static String method(List<String> list) {
        System.out.println("invoke method(List<String> list)");
        return "";
    }

    public static Integer method(List<Integer> list) {
        System.out.println("invoke method(List<Integer> list)");
        return 1;
    }
     */

    public static void main(String[] args) {
       // method(new ArrayList<String>());
       // method(new ArrayList<Integer>());

        // 自动装箱的陷阱
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));

        // 条件编译
        if (true) {
            System.out.println("haha");
        } else {
            System.out.println("wawa");
        }
    }
}
