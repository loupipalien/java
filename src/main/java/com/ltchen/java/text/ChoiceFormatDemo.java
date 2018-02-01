package com.ltchen.java.text;

import java.text.ChoiceFormat;

/**
 * @author: 01139983
 * @date: 2018/2/1
 * @reference: http://blog.csdn.net/xiaoyezi1001/article/details/44815371
 */
public class ChoiceFormatDemo {

    public static void main(String[] args) {
        // 以下2个方法的效果是一样的
        patternFormat();
        System.out.println();
        limitFormat();
    }

    public static void patternFormat(){
        String pattern = "0#[0,3)|3#[3,6)|6#[6,9)|9#[9]";
        ChoiceFormat format = new ChoiceFormat(pattern);
        // 输出的结果是: 0=[0,3)|1=[0,3)|2=[0,3)|3=[3,6)|4=[3,6)|5=[3,6)|6=[6,9)|7=[6,9)|8=[6,9)|9=[9]|
        for(int i=0;i<10;i++){
            System.out.print(i+"="+format.format(i)+"|");
        }
    }

    public static void limitFormat(){
        double limit[] = {0,3,6,9};
        String [] mes = {"[0,3)","[3,6)","[6,9)","[9]"};
        ChoiceFormat format = new ChoiceFormat(limit, mes);
        // 输出的结果是: 0=[0,3)|1=[0,3)|2=[0,3)|3=[3,6)|4=[3,6)|5=[3,6)|6=[6,9)|7=[6,9)|8=[6,9)|9=[9]|
        for(int i=0;i<10;i++){
            System.out.print(i+"="+format.format(i)+"|");
        }
    }
}
