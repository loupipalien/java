package com.ltchen.java.lang.generics;

import java.util.Iterator;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc : 实现了 Fibonacci 生成器, 还想进一步实现一个 Iterable 的 Fibonacci 生成器, 可以选择重写 Fibonacci 类,
 * 但并不是总有 Fibonacci 类的代码控制权, 而且通常重写代码也不是一个好的选择, 除非必须这么做
 * 那么还有另外一种选择就是通过继承 Fibonacci 类并实现所需 (Iterable) 接口来创建适配器类
 */
public class IterableFibonacci extends Fibonacci implements Iterable<Integer> {

    private int count;

    public IterableFibonacci(int count) {
        this.count = count;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return count > 0;
            }

            @Override
            public Integer next() {
                count--;
                return IterableFibonacci.this.next();
            }
        };
    }

    public static void main(String[] args) {
        for (Integer i : new IterableFibonacci(18)) {
            System.out.println(i);
        }
    }
}
