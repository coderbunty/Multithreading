package com.wm.advanced;

import java.util.stream.Stream;

class ProducerOfInt implements Runnable {

    private BlockingQueue q;

    public ProducerOfInt(BlockingQueue q) {
        this.q = q;
    }

    @Override
    public void run() {
        Stream.iterate(1, e -> e + 1)
              .forEach(q::insert);
    }
}

class Consumer implements Runnable {

    private BlockingQueue q;

    public Consumer(BlockingQueue q) {
        this.q = q;
    }

    @Override
    public void run() {
        while(true)
            q.remove();
    }
}

public class QClient {
    public static void main(String[] args) {
        BlockingQueue<Integer> bq = new BlockingQueue<>(5);
        new Thread(new ProducerOfInt(bq), "Integer-producer-1").start();
        new Thread(new Consumer(bq), "Consumer of elements").start();
        new Thread(new ProducerOfInt(bq), "Integer-producer-2").start();
    }
}
