package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/23
 * @desc : 泛型因为擦除导致任何在运行时需要知道确切类型信息的操作都将无法进行
 */
public class ClassTypeCapture<T> {

    private Class<T> clazz;

    public ClassTypeCapture(Class<T> clazz) {
        this.clazz = clazz;
    }

    public boolean method(Object obj) {
        // 引入类型标签, 转而使用动态的 isInstance()
        return clazz.isInstance(obj);
    }

    public static void main(String[] args) {
        ClassTypeCapture ctcOne = new ClassTypeCapture(Building.class);
        System.out.println(ctcOne.method(new Building()));
        System.out.println(ctcOne.method(new House()));
        ClassTypeCapture ctcTwo = new ClassTypeCapture(House.class);
        System.out.println(ctcTwo.method(new Building()));
        System.out.println(ctcTwo.method(new House()));
    }
}

class Building{}

class House extends Building{}


class Erased<T> {
    private static final int SIZE = 100;
    public void method(Object obj) {
        // 获取不到类型信息
        // if (obj instanceof T) {}
        // 一部分原因是不能获取到类型信息, 另一部分原因是不能验证 T 具有无参的构造函数
        // T t = new T();
        // T[] array = new T[SIZE];
        T[] array = (T[]) new Object[SIZE];
    }
}
