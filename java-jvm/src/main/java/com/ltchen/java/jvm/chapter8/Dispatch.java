package com.ltchen.java.jvm.chapter8;

/**
 * @author : ltchen
 * @date : 2018/4/6
 * @desc : 单分派和多分派演示: Java 是一门静态多分派, 动态单分派的语言
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

        @Override
        public void hardChoice(QQ arg) {
            System.out.println("Father choose qq!");
        }

        @Override
        public void hardChoice(_360 arg) {
            System.out.println("Father choose 360!");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }
}