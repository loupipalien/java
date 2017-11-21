package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc : 常常想调用一个方法返回多个对象, 可创建元组打包对象返回
 */
public class TwoTuple<A,B> {

    /**
     * 用 public + final 达到 private + get 一样的访问保险, 而且这样更简洁明了
     */
    public final A first;
    public final B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "TwoTuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
