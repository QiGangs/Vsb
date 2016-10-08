package com.qi.vsb.view;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qi.vsb.R;
import com.qi.vsb.util.OkHttpClientManager;
import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 * Created by qigang on 16/7/15.
 */

public class Park extends Fragment {
    private View view;
    private EditText editText1;
    private EditText editText2;
    private Button button1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.park_activity, container, false);
        initview();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(editText1.getText().toString(),editText2.getText().toString());
            }
        });



        return view;
    }

    public void initview(){
        editText1 = (EditText)view.findViewById(R.id.account);
        editText2 = (EditText)view.findViewById(R.id.password);
        button1 = (Button)view.findViewById(R.id.loginbt);
    }

    private void login(String name,String passwd){
        OkHttpClientManager.postAsyn("http://192.168.1.104:8080/VBS/LoginService", new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(1);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String bytes) {
                        handler.sendEmptyMessage(0);
                    }
                },
                new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("account", name),
                        new OkHttpClientManager.Param("password", passwd)}
        );
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getActivity(),"验证成功"+msg.what,Toast.LENGTH_SHORT).show();
        }
    };
}
