package com.ltchen.java.jvm.chapter8;

/**
 * @author : ltchen
 * @date : 2018/4/6
 * @desc : 方法动态分派演示
 */
public class DynamicDispatch {

    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Man Say Hello!");
        }
    }

    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Woman Say Hello!");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
        man = new Woman();
        man.sayHello();
    }

}
