package com.ltchen.java.jvm.chapter8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author : ltchen
 * @date : 2018/4/8
 * @desc :
 */
public class InvokeGrandfatherClassMethod {

    class GrandFather {
        void thinking() {
            System.out.println("I am grandfather!");
        }
    }

    class Father extends GrandFather {
        @Override
        void thinking() {
            System.out.println("I am father!");
        }
    }

    class Son extends Father {
        @Override
        void thinking() {
            try {
                // 可以利用 super 关键字拿到父类的引用, 但是拿不到祖父类的引用, 无法调用到祖父类的方法; 利用 MethodHandle 对象处理
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new InvokeGrandfatherClassMethod().new Son()).thinking();
    }
}

