package com.ltchen.java.lang.generics;

import java.awt.*;

/**
 * @author : ltchen
 * @date : 2017/11/26
 * @desc : 因为擦除移除了类型信息,　所以可以用无界泛型参数调用的方法只是那些可以用
 */
public class BasicBounds {

    public static void main(String[] args) {
        Solid<Bounded> solid = new Solid<Bounded>(new Bounded());
        solid.getColor();
        solid.getX();
        solid.getY();
        solid.getZ();
        solid.weight();
    }

}

interface HasColor {

    /**
     * 获取颜色
     * @return
     */
    Color getColor();
}

class Colored<T extends HasColor> {

    private T t;

    public Colored(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public Color getColor() {
        return t.getColor();
    }
}

class Dimension {

    private int x;
    private int y;
    private int z;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

}


/**
 * 类在前, 接口在后
 * @param <T>
 */
class ColoredDimension<T extends Dimension & HasColor> {

    private T t;

    public ColoredDimension(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public Color getColor() {
        return t.getColor();
    }

    public int getX() {
        return t.getX();
    }

    public int getY() {
        return t.getY();
    }

    public int getZ() {
        return t.getZ();
    }

}

interface Weight {

    /**
     * 获取重量
     * @return
     */
    int weight();
}

/**
 * 只能继承一个类, 但可以继承多个接口
 * @param <T>
 */
class Solid<T extends Dimension & HasColor & Weight> {

    private T t;

    public Solid(T t) {
        this.t = t;
    }

    public Color getColor() {
        return t.getColor();
    }

    public int getX() {
        return t.getX();
    }

    public int getY() {
        return t.getY();
    }

    public int getZ() {
        return t.getZ();
    }

    public int weight() {
        return t.weight();
    }
}

class Bounded extends Dimension implements HasColor, Weight {

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public int weight() {
        return 0;
    }
}