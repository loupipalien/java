package com.ltchen.java.jvm.chapter12;

/**
 * @author : ltchen
 * @date : 2018/4/28
 * @desc : volatile 关键字测试
 */
public class VolatileTest {

    public static volatile int race = 0;

    public static void increase() {
        race++;
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
        while (Thread.activeCount() > 1) {
            Thread.yield();
            Thread.
            System.out.println(race);
        }

        System.out.println(race);
    }
}
