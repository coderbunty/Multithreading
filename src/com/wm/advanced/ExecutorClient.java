package com.wm.advanced;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorClient {

  public static void main(String[] args) {
    ExecutorClient et = new ExecutorClient();
    //et.executeTasks();
    et.executeTasksInCustomThreadPool();
  }

  public void executeTasks() {
    Executor executor = Executors.newFixedThreadPool(10);
    while (true) {
      executor.execute(new Task());
    }
  }

  public void executeTasksInCustomThreadPool() {
    Executor executor = new CustomFixedThreadPoolExecutor(10);
    while (true) {
      executor.execute(new Task());
    }
  }

  private class Task implements Runnable {

    @Override
    public void run() {
      Random rand = new Random();
      try {
        System.out.println(Thread.currentThread().getName() + " started running.");
        Thread.sleep(rand.nextInt(200));  // some random time consuming operation
        System.out.println(Thread.currentThread().getName() + " finished running.");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
