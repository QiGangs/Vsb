package com.qi.vsb.Acti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qi.vsb.MainActivity;
import com.qi.vsb.R;
import com.qi.vsb.service.Myservice;

/**
 * Created by qigang on 16/7/15.
 */

public class Firstview extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstview_activity);

        new Thread(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(getBaseContext(), Myservice.class));
                handler.sendEmptyMessage(0);
            }
        }).start();

//        new Thread(new Runnable(){
//            public void run(){
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Intent intent = new Intent(Firstview.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//        }).start();


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //finish();
        }
    };

}
