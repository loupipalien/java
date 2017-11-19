package com.ltchen.java.lang.generics.coffee;

import com.ltchen.java.util.Generator;

import java.util.Iterator;
import java.util.Random;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc : 咖啡生成器, 实现了 Generator 接口, 同时还实现了 Iterable 接口
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {

    private Class[] types = {Latte.class, Mocha.class, Cappuccino.class, Americano.class, Breve.class};
    private static Random random = new Random(47);
    /**
     * 为了迭代循环
     */
    private int size = 0;

    public CoffeeGenerator() {}

    public CoffeeGenerator(int size) {
        this.size = size;
    }

    @Override
    public Coffee next() {
        try {
            return (Coffee) types[random.nextInt(types.length)].newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class CoffeeIterator implements Iterator<Coffee> {
        int count  = size;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Coffee next() {
            count--;
            return CoffeeGenerator.this.next();
        }
    }

    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }

    public static void main(String[] args) {
        int size = 5;
        CoffeeGenerator cg = new CoffeeGenerator();
        for (int i = 0; i < size; i++) {
            System.out.println(cg.next());
        }
        for (Coffee coffee : new CoffeeGenerator(size)) {
            System.out.println(coffee);
        }
    }
}
