package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/26
 * @desc : 因为有了擦除, 数组运行时的类型只能是 Object[]
 */
public class GenericArray<T> {

    private T[] array;

    public GenericArray(int size) {
        // 成功创建泛型数组的唯一方式就是创建一个被擦除类型的新数组, 然后对其转型
        array = (T[]) new Object[size];
    }

    public void put(int index, T t) {
        array[index] = t;
    }

    public T get (int index) {
        return array[index];
    }

    public T[] getArray() {
        return array;
    }

    public static void main(String[] args) {
        GenericArray<Integer> ga = new GenericArray<>(10);
        // java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.Integer
        Integer[] ia = ga.getArray();
        // this is ok
        Object[] oa = ga.getArray();
    }
}
