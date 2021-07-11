package com.wm.advanced;

/*
  Blocking queue - A Queue that additionally supports operations that wait for the queue to become non-empty when retrieving an element,
  and wait for space to become available in the queue when storing an element.
  BlockingQueue methods come in four forms, with different ways of handling operations that cannot be satisfied immediately,
  but may be satisfied at some point in the future: one throws an exception, the second returns a special value (either null or false, depending on the operation),
  the third blocks the current thread indefinitely until the operation can succeed, and the fourth blocks for only a given maximum time limit before giving up.
  Blocking Queue implementation using wait() & notify().
*/
public class BlockingQueueWN<T extends Comparable<T>> {

  private Node head;
  private Node tail;
  private int size;
  private volatile int currSize;

  public BlockingQueueWN(int size) {
    this.size = size;
    this.currSize = 0;
  }

  public synchronized void insert(T data) throws InterruptedException {
    System.out
        .println(Thread.currentThread().getName() + " to insert " + data + " in BlockingQueue");
    while (currSize >= size) {
      System.out.println(Thread.currentThread().getName() + " waiting for queue to have space");
      wait();
    }
    if (head == null) {
      head = new Node(data);
      tail = head;
    } else {
      tail.next = new Node(data);
      tail = tail.next;
    }
    currSize++;
    /*
     * Wakes up a single thread (chosen arbitrarily) that is waiting on this object's monitor.
     * The awakened thread will not be able to proceed until the current thread relinquishes the lock on this object.
     * The awakened thread will compete in the usual manner with any other threads (in 'waiting for lock acquisition' state) that might be actively competing to synchronize on this object.
     * There can be other threads which did not call object's wait() but are still in  'waiting for lock acquisition' state as they are trying to acquire lock to get into the synchronized block of code.
     */
    notify();
    System.out
        .println(Thread.currentThread().getName() + " inserted " + data
            + ", now the current queue size is " + currSize);
  }

  public synchronized T remove() throws InterruptedException {
    System.out.println(Thread.currentThread().getName() + " to remove data from BlockingQueue");
    while (currSize == 0) {
      System.out.println(Thread.currentThread().getName() + " waiting for queue to have elements");
      wait();
    }
    Node retNode = head;
    head = head.next;
    retNode.next = null;
    currSize--;
    notify();
    System.out
        .println(Thread.currentThread().getName() + " removed " + retNode.data
            + ", now the current queue size is " + currSize);
    return retNode.data;
  }

  class Node {

    T data;
    Node next;

    public Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

}
