package com.ltchen.java.jvm.chapter8;

/**
 * @author : ltchen
 * @date : 2018/4/6
 * @desc :
 */
public class Dispatch {

    static class QQ {}

    static class _360 {}

    public static class Father {
        public void hardChoice(QQ arg) {
            System.out.println("Father choose qq!");
        }

        public void hardChoice(_360 arg) {
            System.out.println("Father choose 360!");
        }
    }

    public static class Son extends Father {
        public void hardChoice(QQ arg) {
            System.out.println("Father choose qq!");
        }

        public void hardChoice(_360 arg) {
            System.out.println("Father choose 360!");
        }
    }
}
