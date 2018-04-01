package com.ltchen.java.jvm.chapter7;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : ltchen
 * @date : 2018/4/1
 * @desc : 自定义一个类加载器, 与系统应用加载器加载同一个类并实例化, 判定自定义类加载器实例化的类 instanceof 系统类加载器加载的 Class 是否为 true
 * 由于不同类加载器加载同一个类(同一个 Class 文件更准确), 但在不同的类加载器中是两个不同的 Class 对象, 所以只要类加载器不同, 那么两个类也必定不同
 */
public class DifferentClassLoaderLoadSameClass {

    public static void main(String[] args) throws Exception {
        ClassLoader cl = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        // 自定义加载器加载后实例化
        Object obj1 = cl.loadClass(DifferentClassLoaderLoadSameClass.class.getCanonicalName()).newInstance();
        System.out.println(obj1.getClass());
        System.out.println(obj1 instanceof DifferentClassLoaderLoadSameClass);
        // 系统应用加载器加载后实例化
        Object obj2 = new DifferentClassLoaderLoadSameClass();
        System.out.println(obj2.getClass());
        System.out.println(obj2 instanceof DifferentClassLoaderLoadSameClass);
        // 两个类加载器是否一致
        System.out.println(obj1.getClass().getClassLoader().equals(obj2.getClass().getClassLoader()));
        // 两个类加载器加载的同一份 Class 文件生成的两个不同的 Class 对象
        System.out.println(obj1.getClass().equals(obj2.getClass()));
    }
}
