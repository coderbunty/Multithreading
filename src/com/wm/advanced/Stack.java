package com.wm.advanced;

public class Stack<K extends Comparable<K>> {

    private K[] arr;
    private int size;
    private int index = -1;

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
        return arr[index--];
    }

    public void push(K data) {
        if (isFull())
            throw new RuntimeException("Stack is full.");
        arr[++index] = data;
    }

    /*public K pop() {
        if (this.isEmpty())
            throw new RuntimeException("Stack is empty.");
        return arr[index--];
    }

    public void push(K data) {
        if (isFull())
            throw new RuntimeException("Stack is full.");
        arr[++index] = data;
    }*/

    public K peek() {
        if (this.isEmpty())
            throw new RuntimeException("Stack is empty.");
        return arr[index];
    }

    public boolean isFull() {
        if (index == arr.length - 1)
            return true;
        return false;
    }

    public boolean isEmpty() {
        if (index == -1)
            return true;
        return false;
    }

}
