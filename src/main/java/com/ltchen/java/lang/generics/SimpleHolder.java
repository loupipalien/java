package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/23
 * @desc : 简单容器类, 用于对比泛型容器类的类型参数擦除和转换
 */
public class SimpleHolder {

    private Object obj;

    public Object get() {
        return obj;
    }

    public void set(Object obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        SimpleHolder holder = new SimpleHolder();
        holder.set("item");
        String str = (String) holder.get();
    }
}

/** javap -c SimpleHolder.class 反编译如下
Compiled from "SimpleHolder.java"
public class com.ltchen.java.lang.generics.SimpleHolder {
  public com.ltchen.java.lang.generics.SimpleHolder();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public java.lang.Object get();
    Code:
       0: aload_0
       1: getfield      #2                  // Field obj:Ljava/lang/Object;
       4: areturn

  public void set(java.lang.Object);
    Code:
       0: aload_0
       1: aload_1
       2: putfield      #2                  // Field obj:Ljava/lang/Object;
       5: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #3                  // class com/ltchen/java/lang/generics/SimpleHolder
       3: dup
       4: invokespecial #4                  // Method "<init>":()V
       7: astore_1
       8: aload_1
       9: ldc           #5                  // String item
      11: invokevirtual #6                  // Method set:(Ljava/lang/Object;)V
      14: aload_1
      15: invokevirtual #7                  // Method get:()Ljava/lang/Object;
      18: checkcast     #8                  // class java/lang/String
      21: astore_2
      22: return
}
 */
