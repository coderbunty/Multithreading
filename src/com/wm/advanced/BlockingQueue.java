package com.wm.advanced;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Blocking queue implementation using reentrant locks.
 */
public class BlockingQueue<T extends Comparable<T>> {

    class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    private Node head, tail;
    private int size;
    private volatile int currSize;

    public BlockingQueue(int size) {
        this.size = size;
        this.currSize = 0;
        head = null;
        tail = null;
    }

    public void insert(T data) {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " inserting " + data);
        try {
            while (currSize == size)// while full
                notFull.await();    // await till not full
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
            System.out.println(Thread.currentThread().getName() + " signalling Q not empty anymore.");
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public T remove() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " removing data.");
        try {
            while (currSize == 0)   // while empty
                notEmpty.await();   // await till not empty
            // remove element now
            Node node = head;
            head = head.next;
            if (head == null)
                tail = null;
            currSize--;
            System.out.println(Thread.currentThread().getName() + " signalling Q not full anymore.");
            notFull.signal();
            return node.data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

}
