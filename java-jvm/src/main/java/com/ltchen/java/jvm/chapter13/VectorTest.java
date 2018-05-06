package com.ltchen.java.jvm.chapter13;

import java.util.Vector;

/**
 * @author : ltchen
 * @date : 2018/5/6
 * @desc : Java API 中的线程安全类绝大多数都不是绝对的线程安全
 */
public class VectorTest {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println(vector.get(i));
                    }
                }
            });

            removeThread.start();
            printThread.start();

            // 不要同时产生过多的线程, 否则会导致操作系统假死
            while (Thread.activeCount() > 20);
        }
    }
}
