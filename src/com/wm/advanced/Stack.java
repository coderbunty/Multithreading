package com.wm.advanced;

public class Stack<K extends Comparable<K>> {

    private K[] arr;
    private int size;
    private volatile int topOfStack = -1;

    public Stack(int size) {
        this.size = size;
        arr = (K[]) new Comparable[this.size];
    }

    public K pop() {
        synchronized (this) {
            System.out.println(Thread.currentThread() + ": popping");
            while (this.isEmpty()) {
                try {
                    System.out.println(Thread.currentThread() + ": waiting to pop");
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread() + " interrupted");
                    //e.printStackTrace();
                }
            }
            K retVal = arr[topOfStack];
            topOfStack--;
            System.out.println(Thread.currentThread() + ": notifying after popping");
            notify();
            return retVal;
        }
    }

    public void push(K data) {
        synchronized(this) {
            System.out.println(Thread.currentThread() + ": pushing");
            while (isFull()) {
                try {
                    System.out.println(Thread.currentThread() + ": waiting to push");
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread() + " interrupted");
                    //e.printStackTrace();
                }
            }
            arr[++topOfStack] = data;
            System.out.println(Thread.currentThread() + ": notifying after pushing");
            notify();
        }
    }

    public K peek() {
        if (this.isEmpty())
            throw new RuntimeException("Stack is empty.");
        return arr[topOfStack];
    }

    public boolean isFull() {
        if (topOfStack == arr.length - 1)
            return true;
        return false;
    }

    public boolean isEmpty() {
        if (topOfStack == -1)
            return true;
        return false;
    }

    /*public K pop() {
        if (this.isEmpty())
            throw new RuntimeException("Stack is empty.");
        return arr[topOfStack--];
    }

    public void push(K data) {
        if (isFull())
            throw new RuntimeException("Stack is full.");
        arr[++topOfStack] = data;
    }*/

}
