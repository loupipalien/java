package com.ltchen.java.jvm.chapter10;

/**
 * @author : ltchen
 * @date : 2018/4/23
 * @desc : 反面示例
 */
public class BadlyNamedCode {

    enum colors {red, blue, green;}

    final static int _FORTY_TWO = 42;

    public static int NOT_A_CONSTANT = _FORTY_TWO;

    protected void BadlyNamedCode() {
        return;
    }

    public void NOTcamelCASEmethodName() {
        return;
    }
}
