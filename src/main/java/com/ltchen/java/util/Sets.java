package com.ltchen.java.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : ltchen
 * @date : 2017/11/21
 * @desc : Set 工具类
 */
public class Sets {

    /**
     * 并集
     * @param one 一个 Set
     * @param anohter 另一个 Set
     * @param <T> 泛型
     * @return Set<T>
     */
    public static <T> Set<T> union(Set<T> one, Set<T> anohter) {
        HashSet<T> set = new HashSet<T>(one);
        set.addAll(anohter);
        return set;
    }

    /**
     * 交集
     * @param one 一个 Set
     * @param anohter 另一个 Set
     * @param <T> 泛型
     * @return Set<T>
     */
    public static <T> Set<T> intersection(Set<T> one, Set<T> anohter) {
        HashSet<T> set = new HashSet<T>(one);
        set.retainAll(anohter);
        return set;
    }

    /**
     * 差集
     * @param one 一个 Set
     * @param anohter 另一个 Set
     * @param <T> 泛型
     * @return Set<T>
     */
    public static <T> Set<T> difference(Set<T> one, Set<T> anohter) {
        HashSet<T> set = new HashSet<T>(one);
        set.removeAll(anohter);
        return set;
    }

    /**
     * 补集
     * @param one 一个 Set
     * @param anohter 另一个 Set
     * @param <T> 泛型
     * @return Set<T>
     */
    public static <T> Set<T> complement(Set<T> one, Set<T> anohter) {
        return difference(union(one, anohter), intersection(one, anohter));
    }
}
