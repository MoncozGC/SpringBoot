package org.moncozgc.employ.thread;

/**
 * Created by MoncozGC on 2022/12/26
 */
public class ThreadTestMain {
    public static void main(String[] args) {
        ProductStore productStore = new ProductStore();

        PushTarget pushTarget = new PushTarget(productStore);
        TakeTarget takeTarget = new TakeTarget(productStore);

        //启动5个生产产品的线程
        new Thread(pushTarget).start();
        new Thread(pushTarget).start();
        new Thread(pushTarget).start();
        new Thread(pushTarget).start();
        new Thread(pushTarget).start();

        //启动3个消费产品的线程
        new Thread(takeTarget).start();
        new Thread(takeTarget).start();
        new Thread(takeTarget).start();
    }
}
