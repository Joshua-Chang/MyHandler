package com.example.myhandler.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {
    //！！！！差异：Android中是nativePollOnce和weak底层操作
    BlockingQueue<Message> blockingQueue=new ArrayBlockingQueue<Message>(50);
    public boolean enqueueMessage(Message msg, long uptimeMillis) {
        try {
            blockingQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Message next() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
