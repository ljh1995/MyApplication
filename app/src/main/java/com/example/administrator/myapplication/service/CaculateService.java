package com.example.administrator.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018\7\12 0012.
 */

public class CaculateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CaculateBind();
    }

    public class CaculateBind extends Binder {
        public double Avg(double...doubles){
            int count = doubles.length;
            if (count==0){
                return 0;
            }
            double sum=0;
            for (double s:doubles){
                sum+=s;
            }
            return sum/count;
        }
    }
}
