package com.example.myhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.e(">>>",msg.obj.toString());
                return false;
            }
        }){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e(">>>",msg.obj.toString());
//                Toast.makeText(MainActivity.this, "msg"+msg.obj, Toast.LENGTH_SHORT).show();
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
//        new HandlerThread("thread0"){
//            @Override
//            public void run() {
//                super.run();
//            }
//        };
    }
}