package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

/**
 * Created by Administrator on 2018\1\30 0030.
 */

public class RereshActivity extends Activity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView ListView_Id;
    private ListViewAdapter listviewadapter;
    private String key="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        Instantiation();
    }
    public void Instantiation(){

        ListView_Id = (ListView) findViewById(R.id.ListView_Id);
//        listviewadapter = new ListViewAdapter(getApplication(),key);
//        ListView_Id.setAdapter(listviewadapter);
        /**
         * 首页下拉刷新
         */
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.Indigo_nav_color);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.white);
        swipeRefreshLayout.setProgressViewEndTarget(true, 200);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        data.clear();
//                        for(int i=0;i<50;i++){
//                            data.add("SwipeRefreshLayout下拉刷新"+i);
//                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
    }
    //handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    key="1";
                    swipeRefreshLayout.setRefreshing(false);
//                    listviewadapter = new ListViewAdapter(getApplication(),key);
//                    ListView_Id.setAdapter(listviewadapter);
//                    adapter.notifyDataSetChanged();
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };
}
