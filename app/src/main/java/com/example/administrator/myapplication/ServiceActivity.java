package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.service.CaculateService;

/**
 * Created by Administrator on 2018\7\12 0012.
 */

public class ServiceActivity extends Activity{
    private EditText chineses,math,english;
    private Button btnOk;
    private TextView avg;

    private CaculateService.CaculateBind binder=null;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder=(CaculateService.CaculateBind)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        //启动service
        Intent service = new Intent();
        service.setAction("my.CaculateService");
        service.setPackage("com.example.administrator.myapplication");
        bindService(service,conn,BIND_AUTO_CREATE);
        initview();
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    private void initview() {
        chineses = (EditText) findViewById(R.id.Chinese);
        math = (EditText) findViewById(R.id.math);
        english = (EditText) findViewById(R.id.English);
        avg = (TextView) findViewById(R.id.avg);
        btnOk = (Button) findViewById(R.id.ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double chinese = Double.parseDouble(chineses.getText().toString());
                    double maths = Double.parseDouble(math.getText().toString());
                    double englisg = Double.parseDouble(english.getText().toString());
                    if (binder!=null){
                        double result=binder.Avg(chinese,maths,englisg);
                        avg.setText("平均成绩为：" + result);
                    }
                }catch (NumberFormatException e){

                }
            }
        });
    }
}
