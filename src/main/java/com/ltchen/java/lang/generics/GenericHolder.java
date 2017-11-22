package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/23
 * @desc : 对象进入和离开的地点正是编译器执行类型检查并插入转型代码的地点
 */
public class GenericHolder<T> {

    private T obj;

    public T get() {
        return obj;
    }

    public void set(T obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        GenericHolder<String> holder= new GenericHolder<String>();
        holder.set("item");
        String str = holder.get();
    }
}

/** javap -c GenericHolder.class 反编译如下
Compiled from "GenericHolder.java"
public class com.ltchen.java.lang.generics.GenericHolder<T> {
  public com.ltchen.java.lang.generics.GenericHolder();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public T get();
    Code:
       0: aload_0
       1: getfield      #2                  // Field obj:Ljava/lang/Object;
       4: areturn

  public void set(T);
    Code:
       0: aload_0
       1: aload_1
       2: putfield      #2                  // Field obj:Ljava/lang/Object;
       5: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #3                  // class com/ltchen/java/lang/generics/GenericHolder
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
