package com.ltchen.java.lang.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ltchen
 * @date : 2017/11/21
 * @desc : 泛型方法和可变参数
 */
public class GenericAndVarargs {

    /**
     * 将数组转换为列表, 形如 java.util.Arrays.asList();
     * @param args 可变参数
     * @param <T> 泛型
     * @return List<T>
     */
    public static <T> List<T> makeList(T... args) {
        List<T> list = new ArrayList<T>();
        for (T arg : args) {
            list.add(arg);
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> list = makeList("A");
        System.out.println(list);
        list = makeList("A", "B", "C");
        System.out.println(list);
    }


}
