package com.qi.vsb.service;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.qi.vsb.Acti.Firstview;
import com.qi.vsb.MainActivity;
import com.qi.vsb.R;

import java.io.IOException;
import java.util.UUID;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by qigang on 16/7/19.
 */

public class Myservice extends Service {
    BluetoothAdapter mAdapter;
    BluetoothDevice dcvice;
    BluetoothSocket clienSocket= null;
    private int flag = 9;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.reset"))
            {
                flag = 9;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.reset");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        //Toast.makeText(this,"the service is Creating",Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAdapter= BluetoothAdapter.getDefaultAdapter();
                if (!mAdapter.isEnabled()) {
                    mAdapter.enable();
                }
                String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
                UUID uuid = UUID.fromString(SPP_UUID);
                dcvice = mAdapter.getRemoteDevice("80:D6:C2:26:A8:79");
                //dcvice = mAdapter.getRemoteDevice("80:D7:96:7D:A0:C5");
                try {
                    clienSocket = dcvice. createRfcommSocketToServiceRecord(uuid);
                    clienSocket.connect();

                    handler1.sendEmptyMessage(4);

                    while(true){
                        byte[] buffer = new byte[1024];
                        int read = 123;
                        read = clienSocket.getInputStream().read(buffer);
                        Message message = new Message();
                        message.what = new Integer((new String(buffer,0,read)).toString());
                        handle.sendMessage(message);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }



    Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //Toast.makeText(getBaseContext(),"sb",Toast.LENGTH_SHORT).show();
            if(flag == 0 || flag == 1){
                return;
            }else {
                flag = msg.what;
            }
            Intent x = new Intent();     //发送广播刷新前一个界面
            x.putExtra("statue",msg.what);
            x.setAction("action.alert");
            sendBroadcast(x);
            //Toast.makeText(getBaseContext(),msg.arg1+"@"+flag,Toast.LENGTH_SHORT).show();
        }
    };

    Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);   //在service里启动intent跳转到另一个界面
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        }
    };






    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
