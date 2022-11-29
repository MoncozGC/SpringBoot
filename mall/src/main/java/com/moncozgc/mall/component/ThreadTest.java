package com.moncozgc.mall.component;

/**
 * 线程测试
 * Created by MoncozGC on 2022/11/28
 */
public class ThreadTest {
    public static void main(String[] args) {

        MyThread t1 = new MyThread();
        MyThreadRunnable target = new MyThreadRunnable();
        Thread t2 = new Thread(target);

        //启动线程1
        t1.start();
        //启动线程2
        t2.start();

    }

}

//创建线程方式一
class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("方式一-->" + i);
        }
    }
}

//创建线程方式二
class MyThreadRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("方式二-->" + i);
        }
    }
}
