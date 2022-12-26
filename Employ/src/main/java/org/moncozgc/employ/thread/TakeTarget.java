package org.moncozgc.employ.thread;

/**
 * Created by MoncozGC on 2022/12/26
 */
public class TakeTarget implements Runnable {
    private ProductStore productStore;

    public TakeTarget(ProductStore productStore) {
        this.productStore = productStore;
    }

    @Override
    public void run() {
        while (true) {
            productStore.take();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
