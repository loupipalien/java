package com.ltchen.java.lang.generics;

import java.util.*;

/**
 * @author : ltchen
 * @date : 2017/11/22
 * @desc : 泛型擦除, 在泛型代码内部无法获得任何有关泛型参数类型的信息
 * 者意味着在使用泛型时, 任何具体的类型信息都被擦除了, 唯一知道的就是在使用一个对象
 */
public class GenericTypeErasure {

    public static void main(String[] args) {
        Class typeOne = new ArrayList<String>().getClass();
        Class typeTwo = new ArrayList<String>().getClass();
        System.out.println(typeOne == typeTwo);
        System.out.println(typeOne.equals(typeTwo));

        List<Frob> list = new ArrayList<Frob>();
        Map<Frob,Fnorkle> map = new HashMap<Frob,Fnorkle>();
        Quark<Fnorkle> quark = new Quark<Fnorkle>();
        Particle<Long, String> particle = new Particle<Long, String>();
        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(particle.getClass().getTypeParameters()));
    }
}

class Frob {}
class Fnorkle {}
class Quark<Q> {}
class Particle<Position, momentum> {}


