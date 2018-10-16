package com.example.administrator.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by lianjh on 2018\10\15 0015.
 * Current page
 */

public class DialogManager {
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;

    private TextView mLabel;

    private Context mContext;

    public DialogManager(Context context){
        mContext = context;
    }
    public void showRecordingDialog(){
        mDialog=new Dialog(mContext,R.style.AudioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recorder_dialog,null);
        mDialog.setContentView(view);

        mIcon = (ImageView) mDialog.findViewById(R.id.dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.dialog_voice);
        mLabel = (TextView) mDialog.findViewById(R.id.dialog_label);

        mDialog.show();
    }
    public void recording(){
        if (mDialog!=null && mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.recorder);
            mLabel.setText("手指上滑，取消发送");
        }
    }
    public void wantCancel(){
        if (mDialog!=null && mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.cancel);
            mLabel.setText("松开手指，取消发送");
        }
    }
    public void tooShort(){
        if (mDialog!=null && mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.voice_to_short);
            mLabel.setText("录音时间过短");
        }
    }
    public void dimissDialog(){
        if (mDialog!=null && mDialog.isShowing()){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    //通过level更新voice的图片
    public void updateVoiceLevel(int level){
        if (mDialog!=null && mDialog.isShowing()){
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            mLabel.setVisibility(View.VISIBLE);

            int resId = mContext.getResources().getIdentifier("v"+level,"drawable",mContext.getPackageName());
            mVoice.setImageResource(resId);

        }
    }
}
