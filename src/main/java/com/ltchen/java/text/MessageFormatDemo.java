package com.ltchen.java.text;

import java.text.*;
import java.util.Date;

/**
 * @author: ltchen
 * @date: 2018/2/1
 * @reference: http://blog.csdn.net/xiaoyezi1001/article/details/44815371
 */
public class MessageFormatDemo {

    public static void main(String[] args) {
        formatText();
        formatNumber();
        formatDate();
        subFormat();
    }

    /**
     * 对应的 {0},{1},{2},{3},{4},{5},{6}
     * 为{"一","二","三","四","五","六","日"}
     */
    public static void formatText(){
        String message = "今天是星期{6}";
        String [] day={"一","二","三","四","五","六","日"};
        // 输出结果为“今天是星期日”
        System.out.println(MessageFormat.format(message,day));
    }

    public static void formatNumber(){
        String message = "圆周率是{0,number,#.##}";
        Double yl[] = {new Double(3.1415)};
        // 输出3.14
        System.out.println(MessageFormat.format(message,yl));
    }

    /***
     * number：调用NumberFormat进行格式化
     * date：调用DateFormat进行格式化
     * time：调用DateFormat进行格式化
     * choice：调用ChoiceFormat进行格式化
     */
    public static void formatDate(){
        String message = "今天是{0,date,yyyy/MM/dd HH:mm:ss}";
        Date date = new Date();
        System.out.println(MessageFormat.format(message,date));
    }

    public static void subFormat(){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String message = "今天是:{0}==={1}";
        DecimalFormat numFormat = new DecimalFormat("###,###,###");
        MessageFormat mf = new MessageFormat(message);
        Date date = new Date();
        Double d1 = new Double(12345666);
        // 设置 {0} 使用的格式化方式和 {1} 使用的格式化方式
        mf.setFormatsByArgumentIndex(new Format[]{format,numFormat});
        // 输出"今天是:2015/04/01 22:24:24===12,345,666"
        System.out.println(mf.format(new Object[]{date,d1}));
    }
}
