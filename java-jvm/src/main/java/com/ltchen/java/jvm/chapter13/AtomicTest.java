package com.ltchen.java.jvm.chapter13;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : ltchen
 * @date : 2018/5/7
 * @desc : Atomic 变量多线程下自增运算测试
 */
public class AtomicTest {

    public static AtomicInteger race = new AtomicInteger(0);

    public static void increase() {
        race.incrementAndGet();
    }

    private static final int THREADS_COUNT = 10;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        // 等待所有线程结束
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(race);
    }
}
