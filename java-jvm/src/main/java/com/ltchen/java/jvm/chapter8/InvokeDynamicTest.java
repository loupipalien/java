package com.ltchen.java.jvm.chapter8;

import java.lang.invoke.*;

/**
 * @author : ltchen
 * @date : 2018/4/7
 * @desc : invokedynamic 指令演示
 */
public class InvokeDynamicTest {

    public static void main(String[] args) throws Throwable {
        INDY_BootstrapMethod().invokeExact("ltchen");
    }

    public static void testMethod(String str) {
        System.out.println("Hello String:" + str);
    }

    public static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType mt) throws NoSuchMethodException, IllegalAccessException {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, mt));
    }

    public static MethodType MT_BootstrapMethod() {
        return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
    }

    private static MethodHandle MH_BootstrapMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findStatic(InvokeDynamicTest.class, "BootstrapMethod", MT_BootstrapMethod());
    }

    private static MethodHandle INDY_BootstrapMethod() throws Throwable {
        CallSite cs = (CallSite) MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(), "testMethod", MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null));
        return cs.dynamicInvoker();
    }
}
