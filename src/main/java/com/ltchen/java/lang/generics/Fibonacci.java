package com.ltchen.java.lang.generics;

import com.ltchen.java.util.Generator;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc : 斐波那契生成器
 */
public class Fibonacci implements Generator<Integer> {

    private static final int ONE = 1;
    private static final int TWO = 2;

    private int count = 0;

    @Override
    public Integer next() {
        return fib(count++);
    }

    /**
     * 返回第 N 个斐波那契数
     * @param n
     * @return int
     */
    private int fib(int n) {
        if (n < TWO) {
            return ONE;
        }
        return fib(n - ONE) + fib(n - TWO);
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        int n = 18;
        for (int i = 0; i < n; i++) {
            System.out.println(i + ":" +fibonacci.next());
        }
    }
}
