package com.wm.basic;

// in this app we are trying to increment a variable count from 0 using 2 threads 10000 times each. So ideally, the value of the count must be 20000 at the end of execution.

/*
 * counter++ is comprised of 3 operations - reading count value, incrementing it & updating it.
 * when 2 threads simultaneously try to update the count value we have a situation
 * say counter=100 now both t1 & t2 read this value together, t1 updates count to 101 first & t2 updates the already read 100 by adding 1. Now count again gets set to 101 after 2 increments.
 * this is a problem when 2 threads access a common critical section.
 * using 'synchronized' keyword will allow only 1 thread at a time into a particular section of code.
 * now thread will need to acquire a lock on the piece of code (current object's lock) before executing it & other threads wait till they get a lock on it or till the other thread releases the lock
 * try running the code removing the 'synchronized' keyword.
 * because accessing a volatile variable never holds a lock, it is not suitable for cases where we want to read-update-write as an atomic operation
 */

public class Synchronized1 {

    private int counter = 0;

    public synchronized void increment() {
        counter++;  // count = count + 1 -> 3 operations
    }

    public void startCounting() {
        // increment counter by 20000
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++)
                    increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++)
                increment();
        });

        t1.start();
        t2.start();

        // without this block, 'counter' would have been printed as 0 (or some small number) as still the threads wouldn't
        // have completed running and main thread would have finished
        // even without join(), t1 & t2 would run & complete, it is just that main thread wouldn't have been able to print the final value
        try {
            t1.join();	// waits for t1 to finish (t1 & t2 are already running at this time)
            t2.join();	// waits for t2 to finish (t2 might still be running at this time)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        Synchronized1 sync = new Synchronized1();
        sync.startCounting();
        System.out.println(sync.getCounter());
    }
}
