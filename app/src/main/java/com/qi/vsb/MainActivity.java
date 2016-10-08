package com.qi.vsb;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qi.vsb.Acti.Lookinfo;
import com.qi.vsb.service.Myservice;
import com.qi.vsb.util.DatabaseHelper;
import com.qi.vsb.view.Call;
import com.qi.vsb.view.Check;
import com.qi.vsb.view.Home;
import com.qi.vsb.view.Park;
import com.qi.vsb.view.Warn;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;
    private BottomNavigationBar bottomNavigationBar;
    private Home home;
    private Check check;
    private Call call;
    private Warn warn;
    private Park park;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("安乘智能");
        setSupportActionBar(toolbar);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_home, "首页").setActiveColor("#00838f"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_statue, "推广").setActiveColor("#00838f"))
                //.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "提醒").setActiveColor("#00838f"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_warn, "警告").setActiveColor("#00838f"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_my, "我").setActiveColor("#00838f"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                setfragment(position);
            }
            @Override
            public void onTabUnselected(int position) { }
            @Override
            public void onTabReselected(int position) {}
        });

        setDefaultFragment(); //设置默认frag

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.alert");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);


        DatabaseHelper database = new DatabaseHelper(MainActivity.this);//这段代码放到Activity类中才用this
        db = database.getWritableDatabase();
        database.onCreate(db);





    }



    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int statue = intent.getIntExtra("statue",9);
            String action = intent.getAction();
            if (action.equals("action.alert"))
            {
                alertmsg(statue);
            }
        }
    };


    private void setDefaultFragment()      //设置默认frag
    {
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        home = new Home();
        transaction.replace(R.id.fragment_main, home);
        transaction.commit();
    }

    private void setfragment(int id){     //切换fragment
        FragmentManager fm = getFragmentManager();  // 开启Fragment事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        hideFragments(transaction);
        switch (id) {
            case 0: {
                if (home == null) {
                    home = new Home();
                    transaction.add(R.id.fragment_main, home);
                }else{
                    transaction.show(home);
                }
                break;
            }
            case 1: {
                if (check == null) {
                    check = new Check();
                    transaction.add(R.id.fragment_main, check);
                }else {
                    transaction.show(check);
                }
                break;
            }
//            case 2: {
//                if (call == null) {
//                    call = new Call();
//                }
//                transaction.replace(R.id.fragment_main, call);
//                break;
//            }
            case 2: {
                if (warn == null) {
                    warn = new Warn();
                    transaction.add(R.id.fragment_main, warn);
                }else {
                    transaction.show(warn);
                }
                break;
            }
            case 3: {
                if (park == null) {
                    park = new Park();
                    transaction.add(R.id.fragment_main, park);
                }else {
                    transaction.show(park);
                }
                break;
            }
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (home != null) {
            transaction.hide(home);
        }
        if (check != null) {
            transaction.hide(check);
        }
        if (warn != null) {
            transaction.hide(warn);
        }
        if (park != null) {
            transaction.hide(park);
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void alertmsg(int statue){
        switch (statue){
            case 0:
                insertdata("碰撞警告");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("碰撞警告")
                        .setView(R.layout.alert_info)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent x = new Intent();   //通知service重置变量
                                x.setAction("action.reset");
                                sendBroadcast(x);
                            }
                        }).setNegativeButton("误报", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
                break;
            case 1:
                insertdata("翻车警告");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("翻车警告")
                        .setView(R.layout.alert_info1)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent x = new Intent();   //通知service重置变量
                                x.setAction("action.reset");
                                sendBroadcast(x);
                            }
                        }).setNegativeButton("误报", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
                break;
            case 2:
                insertdata("检测到异常");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("检测到异常")
                        .setView(R.layout.alert_info2)
                        .setPositiveButton("异常详情", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                        Intent x = new Intent();   //通知service重置变量
//                        x.setAction("action.reset");
//                        sendBroadcast(x);
                            }
                        }).create().show();
                break;
        }
        //写入sqlite进行历史记录




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            Intent intent = new Intent(MainActivity.this, Lookinfo.class);
            //startActivity(intent);
            return true;
        }else if(id == R.id.action_infodelet){
            infodelete();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }


    private void insertdata(String type){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        ContentValues cv = new ContentValues();
        cv.put("type",type);
        cv.put("time",str);
        db.insert("info",null,cv);
    }

    private void infodelete(){
        String sql = "delete from info where type='碰撞警告'";//删除操作的SQL语句
        db.execSQL(sql);//执行删除操作
        String sql1 = "delete from info where type='翻车警告'";//删除操作的SQL语句
        db.execSQL(sql1);//执行删除操作
    }

}
