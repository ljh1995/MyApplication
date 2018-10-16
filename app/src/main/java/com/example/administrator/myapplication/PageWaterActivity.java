package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.myapplication.view.WaterMarkBg;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lianjh on 2018\8\28 0028.
 * Current page
 */

public class PageWaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagewater);
        @SuppressLint("SimpleDateFormat") String str = "信息技术部--小王--系统开发三处--" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        View view = findViewById(R.id.action_text);
        view.setBackground(new WaterMarkBg(this, str, -45, 14));
    }
}
