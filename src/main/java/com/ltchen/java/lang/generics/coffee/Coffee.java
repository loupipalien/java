package com.ltchen.java.lang.generics.coffee;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc :
 */
public class Coffee {

    private static long counter = 0;
    private final long id = counter++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}
