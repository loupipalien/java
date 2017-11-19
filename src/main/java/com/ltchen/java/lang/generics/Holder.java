package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc : 泛型即实现了参数化类型的概念, 其实和其他类型差不多, 只不过碰巧有类型参数
 */
public class Holder<T> {

    private T t;

    public Holder(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public static void main(String[] args) {
        Holder<Integer> holder = new Holder<Integer>(1);
        // 无类型转换
        Integer i = holder.get();
        holder.set(i);
        // 出错
        //holder.set(2L);

    }
}
