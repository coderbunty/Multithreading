package com.wm.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Future response from thread can be useful in scenarios where we just start multiple tasks and
 * we don't care about the results immediately. We will collect all the results in some time.
 * This way we don't have to wait by running all the tasks serially & move forward with our execution.
 */

public class FuturesTest {

  public static void main(String[] args) {
    // using Futures from ExecutorService
    new FuturesTest().startTasks();

    // using CompletableFuture
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "Java Multithreading - concurrent & parallel programming.";
    });

    try {
      System.out.println(future.get());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void startTasks() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    List<Future<Integer>> futureList = new ArrayList<>();
    int i = 50;
    // submits 50 Callable tasks to Blocking queue, but only 5 get processed at any single point
    while (i-- != 0) {
      futureList.add(executorService.submit(new RandomPrimeGenerator()));
    }

    for (Future<Integer> future : futureList) {
      try {
        // prints the results as & when they are ready
        System.out.println("Generated random prime number is " + future.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  // this is a callable task which generates a random prime number < 100000
  private class RandomPrimeGenerator implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
      Random rand = new Random();
      Integer prime = null;
      Thread.sleep(100);
      while (true) {
        int num = rand.nextInt(100000);
        if (isPrime(num)) {
          prime = num;
          break;
        }
      }
      System.out.println(Thread.currentThread().getName() + " generated prime number - " + prime);
      return prime;
    }

    private boolean isPrime(int num) {
      if (num == 2 || num == 3) {
        return true;
      }
      boolean isPrime = true;
      int maxDivisor = (int) Math.round(Math.sqrt(num));
      int divisor = 2;
      while (true) {
        if (num % divisor == 0) {
          isPrime = false;
          break;
        } else if (divisor >= maxDivisor) {
          break;
        } else {
          divisor++;
        }
      }

      return isPrime;
    }

  }

}
