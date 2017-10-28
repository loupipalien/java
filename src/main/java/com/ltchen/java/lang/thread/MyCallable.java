 package com.ltchen.java.javase.lang.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 
 * @file : MyCallable.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 使用Callable和Future接口创建线程。具体是创建Callable接口的实现类,并实现clall()方法。并使用FutureTask类来包装Callable实现类的对象,且以此FutureTask对象作为Thread对象的target来创建线程。
 * 
 * 在实现Callable接口中,此时不再是run()方法了,而是call()方法,此call()方法作为线程执行体,同时还具有返回值！在创建新的线程时,是通过FutureTask来包装MyCallable对象,同时作为了Thread对象的target。
 * FutureTask类实际上是同时实现了Runnable和Future接口,由此才使得其具有Future和Runnable双重特性。通过Runnable特性,可以作为Thread对象的target,而Future特性,使得其可以取得新创建线程中的call()方法的返回值。
 * 
 * 执行下此程序,sum = 4950永远都是最后输出的。而“主线程for循环执行完毕..”则很可能是在子线程循环中间输出。由CPU的线程调度机制,“主线程for循环执行完毕..”的输出时机是没有任何问题的。
 * sum =4950会永远最后输出,原因在于通过ft.get()方法获取子线程call()方法的返回值时,当子线程此方法还未执行完毕,ft.get()方法会一直阻塞,直到call()方法执行完毕才能取到返回值。
 */
public class MyCallable implements Callable<Integer> {

	private int i = 0;
	
	// 与run()方法不同的是,call()方法具有返回值
	@Override
	public Integer call() throws Exception {
        int sum = 0;
        for (; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            sum += i;
        }
        return sum;
	}
	
	public static void main(String[] args){
		// 创建MyCallable对象
	    Callable<Integer> myCallable = new MyCallable();    
	    //使用FutureTask来包装MyCallable对象
        FutureTask<Integer> ft = new FutureTask<Integer>(myCallable); 

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if (i == 30) {
            	//FutureTask对象作为Thread对象的target创建新的线程
                Thread thread = new Thread(ft); 
                //线程进入到就绪状态
                thread.start();                      
            }
        }

        System.out.println("主线程for循环执行完毕..");
       
        try {
        	//取得新创建的新线程中的call()方法返回的结果
            int sum = ft.get();            
            System.out.println("sum = " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
	}
}
