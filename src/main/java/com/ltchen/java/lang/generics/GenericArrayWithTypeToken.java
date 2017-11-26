package com.ltchen.java.lang.generics;

import java.lang.reflect.Array;

/**
 * @author : ltchen
 * @date : 2017/11/26
 * @desc : 类型标记 Class<T> 被传递到构造器中, 以便从擦除中恢复, 从而得到需要的实际类型的数组
 */
public class GenericArrayWithTypeToken<T> {

    private T[] array;

    public GenericArrayWithTypeToken(Class<T> type, int size) {
        array = (T[]) Array.newInstance(type,size);
    }

    public void put(int index, T t) {
        array[index] = t;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] getArray() {
        return array;
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> ga = new GenericArrayWithTypeToken<>(Integer.class, 10);
        // this is ok
        Integer[] ia = ga.getArray();
    }
}
