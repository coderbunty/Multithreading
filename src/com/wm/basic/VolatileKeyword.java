package com.wm.basic;

import java.util.Scanner;

/*
 * Volatile variables ensure that the updates to the variable are propagated predictably to other threads.
 * When a field is declared volatile, the compiler & runtime are put on notice that this variable is shared and that
   other operations on it should not be reordered with other memory options.
 * Volatile variables are not cached in registers or in caches where they are hidden from other processors, so a read of
   a volatile variable always returns the most recent write by any thread.
 * There are 2 threads in the below code, runner1 & runner2.
 * 'running' variable is a shared variable across Runnerr threads.
 * If runner1 runs on one core & runner2 runs on a different core (multi-core processor), then both cores can have this
   variable value cached for faster execution, in these scenarios the threads will miss the latest values of this variable.
 * If this caching happens & if some other thread changes the value of this variable, then t1 will still be reading the old value from the cache
 * To avoid this situation, we can use 'volatile' keyword, due to which always the most recent write value of the variable is returned.
 * Declaring a variable 'volatile' means,
 * 	- The value of this variable will never be cached thread-locally: all reads and writes will go straight to "main memory".
 * 	- Access to the variable acts as though it is enclosed in a synchronized block, synchronized on itself. But as accessing
 *    a volatile variable performs no locking and so cannot cause the executing thread to block, making volatile variables a
 *    lighter-weight synchronization mechanism.
 */

class Runnerr extends Thread {
    private static volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Runner is running..");
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void shutdown() {
        running = false;
    }
}

public class VolatileKeyword {

    public static void main(String[] args) {
        Runnerr runner1 = new Runnerr();
        runner1.start();
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        Runnerr runner2 = new Runnerr();
        runner2.shutdown();
    }
}
