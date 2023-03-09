package org.moncozgc.employ.thread;

/**
 * Created by MoncozGC on 2022/12/26
 */
public class ProductStore {
    private int count;
    private final int MAX_COUNT = 5;

    public synchronized void push() {
        while (count >= MAX_COUNT) {
            System.out.println(Thread.currentThread().getName() + "库存数量已到达上线, 生产者停止生成!");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "库存数量=【" + count + "】, 生产者开始生产....");
        count++;
        // 通知消费者消费
        notifyAll();
    }

    public synchronized void take() {
        while (count < 0) {
            System.out.println(Thread.currentThread().getName() + "库存数量=0, 消费者等待!");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "消费者开始消费, 库存数量=【" + count + "】");
        // 通知生产者生产
        notifyAll();
    }
}
