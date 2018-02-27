package com.ltchen.java.demo.lang;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ltchen on 2018/2/27
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            final Thread t = new Thread() {
                @Override
                public void run() {
                    System.out.println("First Time: Current Thread:" + Thread.currentThread().getName() + ", Thread Id:" + ThreadId.get());
                    try {
                        Thread.sleep(new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Second Time: Current Thread:" + Thread.currentThread().getName() + ", Thread Id:" + ThreadId.get());
                }
            };
            t.start();
        }
    }

    static class ThreadId {
        /**
         * 一个递增的序列, 使用 AtomicInger 原子变量保证线程安全
         */
        private static final AtomicInteger nextId = new AtomicInteger(0);
        /**
         * 线程本地变量, 为每个线程关联一个唯一的序号
         */
        private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                // 相当于 nextId++ ,由于 nextId++ 这种操作是个复合操作而非原子操作，会有线程安全问题(可能在初始化时就获取到相同的ID，所以使用原子变量
                return nextId.getAndIncrement();
            }
        };

        /**
         * 返回当前线程的唯一的序列，如果第一次get, 会先调用initialValue
         * @return
         */
        public static int get() {
            return threadId.get();
        }
    }
}