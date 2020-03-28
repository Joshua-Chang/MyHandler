package com.example.myhandler.core;


import android.util.Log;

public class Handler {
    private Looper mLooper;
    private MessageQueue mQueue;
    private Handler.Callback mCallback;

    public Handler() {//普通消息：无接口回调，分发时执行普通handleMessage方法。
        this(null);
    }

    /**
     * todo A、handler初始化
     * 获得当前线程的looper
     * 获得looper内的消息队列
     * 构造时，确认分发消息时，是回调Callback接口方式、还是实现方法方式
     * @param callback
     */
    public Handler(Callback callback) {
        mCallback = callback;//普通消息：有接口回调，分发时执行接口handleMessage方法。

        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
    }


    /**
     * todo B、发送消息，入队
     * 消息target变量赋值为handler自己，looper轮询分发时,会回调自己的dispatchMessage
     * 往looper的消息队列，添加消息
     * 发送普通消息：
     * 发送runnable：包装成消息（message内runnable变量赋值）
     */
    private boolean enqueueMessage(Message msg,long uptimeMillis) {
        msg.target = this;
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return queue.enqueueMessage(msg, uptimeMillis);
    }

    public final boolean sendMessage(Message msg) {//发送普通消息
        return enqueueMessage(msg,0);
    }
    public final boolean post(Runnable r) {//发送runnable消息，存入message。分发时message.callback.run();
        return enqueueMessage(getPostMessage(r),0);
    }
    private Message getPostMessage(Runnable r) {
        //!!!!源码差异：对象池创建Message m = Message.obtain();
        //当然我们自己创建Message时也可以用对象池创建
        Message m = new Message();
        m.callback = r;
        return m;
    }

    /**
     * todo C、3. 分发
     * post(runnable):走handleCallback-->message.callback.run();
     * handler(callback{handleMessage...}):有mCallback，走接口方法复写的handleMessage
     * handler(){handleMessage...}:走handleMessage
     */
    void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }

    private void handleCallback(Message message) {
        message.callback.run();
    }

    public interface Callback {
        boolean handleMessage(Message msg);
    }

    public void handleMessage(Message msg) {
    }
}
