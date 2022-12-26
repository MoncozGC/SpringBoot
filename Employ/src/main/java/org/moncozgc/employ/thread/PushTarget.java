package org.moncozgc.employ.thread;

/**
 * Created by MoncozGC on 2022/12/26
 */
public class PushTarget implements Runnable {
    private ProductStore productStore;

    public PushTarget(ProductStore productStore) {
        this.productStore = productStore;
    }

    @Override
    public void run() {
        while (true) {
            productStore.push();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
