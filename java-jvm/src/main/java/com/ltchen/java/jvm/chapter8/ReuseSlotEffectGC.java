package com.ltchen.java.jvm.chapter8;

/**
 * @author : ltchen
 * @date : 2018/4/2
 * @desc : 重用局部变量表中的 Slot 会影响系统 GC
 * GC 打印信息的 VM 参数: -verbose:gc
 */
public class ReuseSlotEffectGC {

    /**
     * 1. System.gc() 时, placeHolder 变量还在作用域中, 是不会回收内存的
     */
//    public static void main(String[] args) {
//        byte[] placeHolder = new byte[64 * 1024 * 1024];
//        System.gc();
//    }

    /**
     * 2. System.gc() 时, placeHolder 变量已不在作用域中, 应该会回收内存的, 但是没有
     */
//    public static void main(String[] args) {
//        {
//            byte[] placeHolder = new byte[64 * 1024 * 1024];
//        }
//        System.gc();
//    }

    /**
     * 3. System.gc() 时, placeHolder 变量已不在作用域中, 添加在一个新的局部变量, 这时 placeHolder 被回收了
     *    这是因为 placeHolder 的变量已无用, 且使用的 Slot 被变量 a 复用, GC 时就回收了内存
     */
    public static void main(String[] args) {
        {
            byte[] placeHolder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }

}
