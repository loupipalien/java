package com.ltchen.java.javase.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @file : MyCountDownLatch.java
 * @date : 2017年5月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : CountDownLatch类是一个同步计数器,构造时传入int参数,该参数就是计数器的初始值，每调用一次countDown()方法，计数器减1,计数器大于0 时，await()方法会阻塞程序继续执行 
 */
public class MyCountDownLatch {
	 private static final int PLAYER_AMOUNT = 5;
	 
     public MyCountDownLatch() {}  
 
     public static void main(String[] args) {  
         //begin为了确保每个运动员都准备就绪了才能开始比赛，所以每个运动员都会持有一个begin，并且调用begin.await()方法进入等待。  
         CountDownLatch begin = new CountDownLatch(1);  
         //对于整个比赛，所有运动员结束后才算结束。主线程会持有end，确保所有的运动员都到终点才能宣布比赛结束。同时每个运动员到达终点的时候都会调用，end.countdown()方法。  
         CountDownLatch end = new CountDownLatch(PLAYER_AMOUNT);  
         Player[] plays = new Player[PLAYER_AMOUNT];  
           
         for(int i=0;i<PLAYER_AMOUNT;i++)  
             plays[i] = new Player(i+1,begin,end);  
           
         //设置特定的线程池，大小为5  
         ExecutorService exe = Executors.newFixedThreadPool(PLAYER_AMOUNT);  
         for(Player p:plays)  
            //分配线程，每个运动员都准备就绪，但是没有开始赛跑，因为他们持有的begin都调用了begin.await()  
             exe.execute(p);              
         System.out.println("Race begins!");//宣布比赛开始  
         //所有运动员开始赛跑，begin的参数减少1，变为0，持有begin.await的线程开始运行。  
         begin.countDown();  
         try{  
            //等待所有的运动员都到达终点，比赛结束。  
             end.await();              
         }catch (InterruptedException e) {  
             // TODO: handle exception  
             e.printStackTrace();  
         }finally{  
             System.out.println("Race ends!");//宣布比赛结束  
         }  
         exe.shutdown();  
     }
}

class Player implements Runnable {  
	  
    private int id;  
    private CountDownLatch begin;  
    private CountDownLatch end;  
    public Player(int i, CountDownLatch begin, CountDownLatch end) {  
        // TODO Auto-generated constructor stub  
        super();  
        this.id = i;  
        this.begin = begin;  
        this.end = end;  
    }  
  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        try{  
            begin.await();        //等待begin的状态为0，这里表示等待其他选手就绪，然后一起开始比赛  
            Thread.sleep((long)(Math.random()*100)*100);    //随机分配时间，即运动员完成时间  
            System.out.println("Play"+id+" arrived.");//选手到达终点  
        }catch (InterruptedException e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }finally{  
            end.countDown();    //使end状态减1，最终减至0，为0时比赛结束  
        }  
    }  
}  
