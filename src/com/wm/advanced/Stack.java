package com.wm.advanced;

public class Stack<K extends Comparable<K>> {

    private K[] arr;
    private int size;
    private int topOfStack = -1;

    public Stack(int size) {
        this.size = size;
        arr = (K[]) new Comparable[this.size];
    }

    public K pop() {
        synchronized (this) {
            while (this.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        K retVal = arr[topOfStack];
        topOfStack--;
        notify();
        return retVal;
    }

    public void push(K data) {
        synchronized(this) {
            while (isFull()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            arr[++topOfStack] = data;
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
