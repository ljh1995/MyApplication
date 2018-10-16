package com.example.administrator.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lianjh on 2018\10\15 0015.
 * Current page
 */

public class RecorderAdapter extends ArrayAdapter<RecorderActivity.Recorder> {
    private ArrayList<RecorderActivity.Recorder> mDatas;
    private Context mContext;

    private int minItemWidth;
    private int maxItemWidth;

    private LayoutInflater inflater;

    public RecorderAdapter(@NonNull Context context, ArrayList<RecorderActivity.Recorder> datas) {
        super(context, -1,datas);
        mContext = context;
        mDatas = datas;

        inflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        maxItemWidth = (int) (outMetrics.widthPixels*0.7f);
        minItemWidth =  (int) (outMetrics.widthPixels*0.15f);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_recorder,parent,false);
            holder = new ViewHolder();
            holder.seconds = (TextView) convertView.findViewById(R.id.recorder_time);
            holder.length = convertView.findViewById(R.id.recorder_length);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.seconds.setText(Math.round(getItem(position).time)+"\"");
        ViewGroup.LayoutParams LP = holder.length.getLayoutParams();
        LP.width = (int) (minItemWidth + (maxItemWidth/60f*(getItem(position).time)));
        return convertView;
    }
    private class ViewHolder{
        TextView seconds;
        View length;
    }
}
