package com.ltchen.java.jvm.chapter8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author : ltchen
 * @date : 2018/4/7
 * @desc : JSR-292 Method Handle 基础用法演示
 */
public class MethodHandleTest {

    static class ClassA {
        public void println(String str) {
            System.out.println(str);
        }
    }

    public static void main(String[] args) throws Throwable {

        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();

        // 无论 obj 最终是哪个实现类, 这句代码都能正确的调用到 println 方法
        getPrintlnMH(obj).invokeExact("ltchen");

    }

    private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
        /*
         * MethodType: 代表 "方法类型", 包含了方法的返回值 (methodType() 的第一参数) 和具体参数 (methodType() 第二个及以后的参数)
         */
        MethodType mt = MethodType.methodType(void.class, String.class);

        /*
         * MethodHandles.lookup(): 是在指定类中查找符合给定的方法名称, 方法类型, 并且符合调用权限的方法句柄
         * 因为这里调用的是一个虚方法, 按照 Java 语言的规则, 方法的第一个参数是隐式的, 代表该方法的接收者, 也是 this 指向的对象, 这个参数以前
         * 是放在参数列表中进行传递的, 而现在提供了 bingTo() 方法完成这件事情
         */
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
    }

}
