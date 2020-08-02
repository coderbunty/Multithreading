package com.wm.basic;

class DaemonCounter extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println("Daemon - " + i);
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Runner implements Runnable {

    @Override
    public void run() {
        for (int i = 10; i >= 0; i--) {
            System.out.println("Runner - " + i);
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadCreation1 {
    public static void main(String[] args) {
        System.out.println("Thread creation techniques..");

        // user thread
        // thread creation using a class that implements Runnable interface
        Thread runner1 = new Thread(new Runner(), "Slow runner");   // thread name can also be passed as an argument
        runner1.start();        // this method spawns a new thread

        // thread creation using a anonymous class that implements Runnable interface
        Thread runner2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 20; i >= 10; i--) {
                    System.out.println("Fast runner - " + i);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        runner2.start();

        // daemon thread
        // thread creation using a class that extends java.lang.Thread which in turn implements Runnable interface
        DaemonCounter dr = new DaemonCounter();
        dr.setDaemon(true);
        dr.start();

        // using lambda expression
        Thread runner3 = new Thread(() -> {
            System.out.println("Super fast Runner, spawned using lambda expression.");
        });
        runner3.start();
    }
}
