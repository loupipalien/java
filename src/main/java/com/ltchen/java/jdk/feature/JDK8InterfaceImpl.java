package com.ltchen.java.jdk.feature;

/**
 * @author : ltchen
 * @date : 2017/10/28
 * @desc : JDK8Interface实现类
 */
public class JDK8InterfaceImpl implements JDK8Interface {

    // 接口中的默认方法可以不重写, 但是如果实现的两个接口类有同名的默认方法则必须重写
    @Override
    public void defaultMethod() {
        System.out.println("实现类重写接口中的默认方法");
    }

    public static void main(String[] args) {
        // 接口的静态方法必须通过接口类调用
        JDK8Interface.staticMethod();
        // 接口的默认方法必须通过实现类对象调用
        new JDK8InterfaceImpl().defaultMethod();
    }
}
