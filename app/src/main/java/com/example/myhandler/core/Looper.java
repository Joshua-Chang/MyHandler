package com.example.myhandler.core;


public class Looper {
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    public MessageQueue mQueue;
    private Looper(boolean quitAllowed) {
        mQueue=new MessageQueue();
    }
    public static void prepare() {
        prepare(true);
    }

    /**
     * todo 1.looper.prepare
     * 创建looper：创建消息队列
     * ThreadLocal存、取
     */
    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }
    static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * todo 2.loop轮询
     * 在ThreadLocal中根据线程取出looper
     * 取出looper中的消息队列
     * 遍历队列
     * 分发队列里的消息
     * 消息的target为Handler：执行handler的分发方法
     */
    public static void loop() {
        final Looper me = myLooper();//ThreadLocal中唯一的Looper
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;//Looper中唯一的队列
        for (; ; ) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                return;
            }
            msg.target.dispatchMessage(msg);//todo 3.分发
        }
    }
}
