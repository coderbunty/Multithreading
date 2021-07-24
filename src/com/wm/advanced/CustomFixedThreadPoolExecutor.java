package com.wm.advanced;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/*
  When using ThreadPool from ExecutorService, like below,

  Executor executor = Executors.newFixedThreadPool(10);
                        |
  public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

  The way it works is, there will always be 10 threads in the thread pool. Whenever a new task / Runnable comes in,
  the ExecutorService adds the task to the Queue, if a thread in the pool is free, it picks up that task.
  If all 10 threads are busy executing other tasks, then the present task is queued in the BlockingQueue.
  The reason we use blocking queue here is because the consumers (the fixed threads in the pool), will always be
  trying to access the Blocking Queue to get new tasks, at that time, queue needs to be thread safe. Producer here
  is the Executor which just adds the Runnable's to the Queue.
*/

public class CustomFixedThreadPoolExecutor implements Executor {

  private final BlockingQueue<Runnable> bq;
  private final Thread[] workerThreads;

  public CustomFixedThreadPoolExecutor(int numOfThreads) {
    bq = new LinkedBlockingQueue<>();
    workerThreads = new Thread[numOfThreads];
    for (int i = 0; i < numOfThreads; i++) {
      workerThreads[i] = new Worker("CustomPoolThread" + i);
      workerThreads[i].start();
    }
  }

  @Override
  public void execute(Runnable command) {
    try {
      bq.put(command);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public void shutdown() {
    // to be implemented - need to stop all the running threads (one option is thread.interrupt()) 
  }

  private class Worker extends Thread {

    public Worker(String name) {
      super(name);
    }

    @Override
    public void run() {
      while (true) {
        try {
          bq.take().run();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
