package com.example.myhandler;

import android.util.Log;

import com.example.myhandler.core.Handler;
import com.example.myhandler.core.Looper;
import com.example.myhandler.core.Message;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void MyHandler(){
        Looper.prepare();

//        final Handler handler=new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                System.out.println("xxx"+msg.obj);
//                return false;
//            }
//        }){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                System.out.println(msg.obj);
//            }
//        };

        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.obj=1;
                handler.sendMessage(message);
            }
        }).start();

        handler.post(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.obj=2;
                handler.sendMessage(message);
            }
        });
        Looper.loop();
    }
}