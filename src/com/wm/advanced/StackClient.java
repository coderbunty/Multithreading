package com.wm.advanced;

public class StackClient {

    public static void main(String[] args) throws InterruptedException {
        Stack<Integer> stack = new Stack<>(5);

        new StackPusher(stack, "Pusher1", 2020);
        new StackPusher(stack, "Pusher2", 2020);
        new StackPopper(stack, "Popper");
        System.out.println("Main thread sleeping..");
        Thread.sleep(10);
        System.out.println("Exiting main thread..");
    }
}
