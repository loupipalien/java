package com.ltchen.java.util;

import java.util.*;

/**
 * @author : ltchen
 * @date : 2017/11/21
 * @desc :
 */
public class New {

    public static <K, V> Map<K, V> map() {
        return new HashMap<K, V>(16);
    }

    public static <T> List<T> list() {
        return new ArrayList<T>();
    }

    public static <T> Set<T> set() {
        return new HashSet<T>();
    }

    public static <T> Queue<T> queue() {
        return new LinkedList<T>();
    }

    public static void main(String[] args) {
        // 赋值可进行类型推断
        Map<String, List<String>> map = New.map();
        List<String> list = New.list();
        Set<String> set = New.set();
        Queue<String> queue = New.queue();
    }
}
