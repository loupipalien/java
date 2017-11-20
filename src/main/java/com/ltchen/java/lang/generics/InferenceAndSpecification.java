package com.ltchen.java.lang.generics;

import com.ltchen.java.util.Generator;
import com.ltchen.java.util.New;

import java.util.List;
import java.util.Map;

/**
 * @author : ltchen
 * @date : 2017/11/21
 * @desc : 参数赋值也可作类型推断, 明确指定类型反而多余且降低可读性
 */
public class InferenceAndSpecification {

    public static void method(Map<Fibonacci, List<? extends Generator<Integer>>> map) {}

    public static void main(String[] args) {
        // 类型推断
        method(New.map());
        // 类型指定
        method(New.<Fibonacci, List<? extends Generator<Integer>>>map());
    }
}
