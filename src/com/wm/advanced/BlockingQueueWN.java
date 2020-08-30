package com.wm.advanced;

/*
Blocking queue implementation using wait() & notify()
 */
public class BlockingQueueWN<T extends Comparable<T>> {

    class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head, tail;
    private int size;
    private volatile int currSize;

    public BlockingQueueWN(int size) {
        this.size = size;
        this.currSize = 0;
        head = null;
        tail = null;
    }

    public synchronized void insert(T data) {
        System.out.println(Thread.currentThread().getName() + " inserting " + data);
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // insert element now
        if (head == null) {
            head = new Node(data);
            tail = head;
            currSize++;
            return;
        }
        tail.next = new Node(data);
        tail = tail.next;
        currSize++;
        System.out.println(Thread.currentThread().getName() + " notifying all waiting threads.");
        notifyAll();
    }

    public synchronized T remove() {
        System.out.println(Thread.currentThread().getName() + " removing data.");
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // remove element now
        Node node = head;
        head = head.next;
        if (head == null)
            tail = null;
        currSize--;
        System.out.println(Thread.currentThread().getName() + " notifying all waiting threads.");
        notifyAll();
        return node.data;
    }

    public synchronized boolean isEmpty() {
        return currSize == 0;
    }

    public synchronized boolean isFull() {
        return currSize == size;
    }
}
