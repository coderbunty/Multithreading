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
        //BlockingQueue<Integer> bq = new BlockingQueue<>(5);
        //new Thread(new ProducerOfInt(bq), "Integer-producer-1").start();
        //new Thread(new Consumer(bq), "Consumer of elements").start();
        //new Thread(new ProducerOfInt(bq), "Integer-producer-2").start();
        BlockingQueueWN<Integer> bq = new BlockingQueueWN<>(10);

        Thread p1 = new Thread(() -> {
          while (true) {
            Random rand = new Random();
            try {
              Thread.sleep(
                  100);  // thread sleep doesn't alter its lock state, it still has the lock at & after this point
              bq.insert(rand.nextInt());
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        });

        Thread p2 = new Thread(() -> {
          while (true) {
            Random rand = new Random();
            try {
              Thread.sleep(150);
              bq.insert(rand.nextInt());
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        });

        Thread c1 = new Thread(() -> {
          while (true) {
            try {
              Thread.sleep(200);
              bq.remove();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        });

        p1.start();
        p2.start();
        c1.start();
      }


     /*
      Thread-0 inserted 1125371842, now the current queue size is 10
      Thread-1 to insert 1131470738 in BlockingQueue
      Thread-1 waiting for queue to have space          // thread1 waiting to insert
      Thread-0 to insert 2004293405 in BlockingQueue
      Thread-0 waiting for queue to have space          // thread0 waiting to insert
      Thread-2 to remove data from BlockingQueue
      Thread-2 removed 654538715, now the current queue size is 9
      Thread-1 inserted 1131470738, now the current queue size is 10    // thread1 gets the lock & inserts
      Thread-0 waiting for queue to have space                          // thread0 got the lock next, but couldn't insert as the queue is still full, so waits again
      Thread-1 to insert 807759560 in BlockingQueue
      Thread-1 waiting for queue to have space
      Thread-2 to remove data from BlockingQueue
      Thread-2 removed -1692000371, now the current queue size is 9
      Thread-0 inserted 2004293405, now the current queue size is 10    // thread0 gets the lock & inserts
      Thread-1 waiting for queue to have space
      */
    }
}
