package com.wm.advanced;

public abstract class StackUser implements Runnable {

    protected Stack stack;

    protected StackUser(Stack stack, String threadName) {
        this.stack = stack;
        Thread runner = new Thread(this, threadName);
        runner.setDaemon(true);
        runner.start();
    }

}

class StackPusher extends StackUser {
    private Comparable data;

    public <K extends Comparable<K>> StackPusher(Stack stack, String threadName, K data) {
        super(stack, threadName);
        this.data = data;
    }

    @Override
    public void run() {
        while(true)
            stack.push(data);
    }
}

class StackPopper extends StackUser {

    public StackPopper(Stack stack, String threadName) {
        super(stack, threadName);
    }

    @Override
    public void run() {
        while (true)
            stack.pop();
    }
}
