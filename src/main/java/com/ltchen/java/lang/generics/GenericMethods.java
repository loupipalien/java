package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/21
 * @desc : 泛型方法, 非泛型类和泛型类都可以定义泛型方法, 只需将泛型参数列表置于方法返回值之前即可
 * 另外如果使用泛型方法可以取代将整个类泛型化那么就应该只使用泛型方法;
 * 对于一个 static 的方法而言, 无法访问到泛型类的类型参数, 所以如果 static 方法需要使用泛型能力就必须成为泛型方法
 */
public class GenericMethods {

    public <T> void print(T t) {
        System.out.println(t.getClass().getCanonicalName());
    }

    public static void main(String[] args) {
        GenericMethods gm = new GenericMethods();
        gm.print("");
        gm.print('c');
        gm.print(true);
        gm.print(1);
        gm.print(2L);
        gm.print(3.0F);
        gm.print(4.0);
        gm.print(gm);
    }

}
