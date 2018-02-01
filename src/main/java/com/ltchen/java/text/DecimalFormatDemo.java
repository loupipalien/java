package com.ltchen.java.text;

import java.text.DecimalFormat;

/**
 * @author: ltchen
 * @date: 2018/2/1
 * @reference: http://blog.csdn.net/xiaoyezi1001/article/details/44815371
 */
public class DecimalFormatDemo {

    public static void main(String[] args) {
        useFormat();
        use0Format();
    }

    /**
     * 使用 # 方式来代替
     */
    public static void useFormat(){
        DecimalFormat format = new DecimalFormat("#.####");
        double d = 12.42;
        // 输出 12.42
        System.out.println(format.format(d));
    }


    public static void use0Format(){
        DecimalFormat format = new DecimalFormat("#.0000");
        double d = 12.42;
        // 输出 12.4200
        System.out.println(format.format(d));
    }
}
