package com.qi.vsb.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.qi.vsb.MainActivity;
import com.qi.vsb.R;
import com.qi.vsb.ixd.Getinfall;
import com.qi.vsb.ixd.Getinfmotor;
import com.qi.vsb.util.Chechview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by qigang on 16/7/15.
 */

public class Home extends Fragment implements View.OnClickListener{
    private ViewGroup viewGroup;
    private LinearLayout linearLayout;
    private  ArrayList arrayList;
    private ProgressBar progressBar;
    private TextView tvstatue;
    private VideoView videoView;
    int flag = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_activity, container, false);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);


        Getinfmotor getinfmotor = new Getinfmotor();
        Chechview chechview = new Chechview(getActivity().getApplicationContext());
        //viewGroup = (ViewGroup) view.findViewById(R.id.home_head);



        videoView = (VideoView)view.findViewById(R.id.videoView);
        String uri = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.tes;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });





        linearLayout = (LinearLayout) view.findViewById(R.id.home_second);
        tvstatue = (TextView)view.findViewById(R.id.resetview);




        if(getinfmotor.get() == 1){
            TextView textView = (TextView)view.findViewById(R.id.textView21);
            linearLayout.removeView(textView);

            checkstatue();
        }

        return view;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            flag++;
            int i = msg.what;
            if(i != arrayList.size()){
                tvstatue.setText("正在扫描:"+arrayList.get(i).toString());
            }else{
                tvstatue.setText("扫描结束");

                videoView.pause();
            }
            if(i == 0){
                return;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LayoutInflater inflater3 = LayoutInflater.from(getActivity());
            View test = inflater3.inflate(R.layout.test_check_info, null);
            TextView tv1 = (TextView) test.findViewById(R.id.checkinfo1);
            tv1.setText(arrayList.get(i-1).toString());
            tv1.setTextSize(20);
            tv1 = (TextView) test.findViewById(R.id.checkinfo2);
            if(flag == 3){
                tv1.setText("异常");
                tv1.setTextColor(getResources().getColor(R.color.textred));
            }
            else {
                tv1.setText("正常");
            }
            tv1.setTextSize(20);
            test.setLayoutParams(lp);
            linearLayout.addView(test,0);


//            if(msg.what == arrayList.size()-1){
//                progressBar.setAlpha(0f);
//            }
        }
    };

    @Override
    public void onClick(View v) {

    }

    private void checkstatue(){
        Getinfall getinfall = new Getinfall();
        arrayList = getinfall.get();
        for(int i = 0; i<=arrayList.size();i++) {
            final int x = i;
            final int time = 1000*(i);
            new Thread(new Runnable(){
                public void run(){
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.sendEmptyMessage(x);
                }

            }).start();

        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void alertdialog(){

    }
}
