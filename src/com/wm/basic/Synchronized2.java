package com.wm.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * In this code, we are trying to run 2 critical sections of code. Without threads, total execution should ideally take
   a little over 2 seconds (1000 * 1ms * 2)
 * if we use 2 threads to run both stages (both stages run twice as there are 2 threads), then still the code runs in 2
   seconds instead of 4 seconds if only main thread had run it.
 * If we use 2 threads to run critical sections, then there will be sync issues (code still runs in around 2 seconds,
   but list sizes will not be correct), so we need to use 'synchronized' keyword
 * now time taken will be around 4 seconds as another thread will be idle while one thread has the lock & it has used
   that lock to run one critical section. This performance is just like only main thread running both the stages twice.
 * had there been 2 locks, execution would have been faster. we achieve this by creating 2 dummy objects & using their
   locks instead
 */

public class Synchronized2 {
    List<Integer> list1 = new ArrayList<Integer>();
    List<Integer> list2 = new ArrayList<Integer>();

    Object lock1 = new Object();
    Object lock2 = new Object();

    Random rand = new Random();

    private void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);    // some activity that takes 1ms
            } catch (Exception e) {
                e.printStackTrace();
            }
            list1.add(rand.nextInt(100));
        }
    }

    private void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);    // some activity that takes 1ms
            } catch (Exception e) {
                e.printStackTrace();
            }
            list2.add(rand.nextInt(100));
        }
    }

    private void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void begin() {
        System.out.println("Starting processing");
        long stTime = System.currentTimeMillis();
        Thread t1 = new Thread(() -> process());
        Thread t2 = new Thread(() -> process());

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long enTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (enTime - stTime));
        System.out.println("List1 size: " + list1.size() + ", List2 size: " + list2.size());
    }

    public static void main(String[] args) {
        new Synchronized2().begin();
    }

}
