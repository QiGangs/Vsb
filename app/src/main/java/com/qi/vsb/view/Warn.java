package com.qi.vsb.view;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qi.vsb.R;
import com.qi.vsb.util.DatabaseHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qigang on 16/7/15.
 */

public class Warn extends Fragment {
    private View view;
    SQLiteDatabase db = null;
    private ListView listView;
    private SimpleAdapter simpleAdapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Map<String,Object>> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.warn_activity, container, false);

        DatabaseHelper database = new DatabaseHelper(getActivity());//这段代码放到Activity类中才用this
        db = database.getWritableDatabase();

        initview();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = getinfo();
                simpleAdapter = new SimpleAdapter(getActivity(),list, R.layout.listitem, new String[]{"type", "time"}, new int[]{R.id.listview1, R.id.listview2});
                listView.setAdapter(simpleAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        list = getinfo();
        simpleAdapter = new SimpleAdapter(getActivity(),list, R.layout.listitem, new String[]{"type", "time"}, new int[]{R.id.listview1, R.id.listview2});
        listView.setAdapter(simpleAdapter);

        return view;
    }


    public List<Map<String, Object>> getinfo(){
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        Cursor c = db.rawQuery("select * from info",null);
        while(c.moveToNext()) {
            String type = c.getString(c.getColumnIndex("type"));
            String time = c.getString(c.getColumnIndex("time"));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("type", type);
            map.put("time", time);
            data.add(map);
        }
        Collections.reverse(data);
        return data;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initview(){
        listView = (ListView)view.findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.srl);
    }
}
