package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018\7\19 0019.
 */

public class ClickAllActivity extends AppCompatActivity {
    private RecyclerView mRvTextList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        mRvTextList= (RecyclerView) findViewById(R.id.rv_text_list);
        mRvTextList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvTextList.setAdapter(new TextListAdapter(this));
    }
}
