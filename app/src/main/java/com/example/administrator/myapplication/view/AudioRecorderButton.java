package com.example.administrator.myapplication.view;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;

import java.io.File;

/**
 * Created by lianjh on 2018\10\15 0015.
 * Current page
 */

public class AudioRecorderButton extends android.support.v7.widget.AppCompatButton implements AudioManager.AudioStateListen {
    private static final int DISTANCE_CANCEL_Y=50;
    private static final int STATE_NORMAL=1;
    private static final int STATE_RECORDING=2;
    private static final int STATE_CANAEL=3;
    //已经开始录音
    private boolean isRecording = false;

    private int mCurrent = STATE_NORMAL;

    private DialogManager dialogManager;

    private AudioManager audioManager;

    private float mTime;
    //是否触发LongClick
    private boolean mReady;

    public AudioRecorderButton(Context context) {
        this(context,null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        dialogManager = new DialogManager(getContext());
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        String dir = null;
        if   (sdCardExist)
        {
            dir = Environment.getExternalStorageDirectory()+"/test_recorder";
        }
        audioManager = AudioManager.getInstance(dir);
        audioManager.setOnAudioStateListen(this);
       setOnLongClickListener(new OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               mReady = true;
               audioManager.prepareAudio();
               return false;
           }
       });
    }
    //录音完成后的回调
    public interface AudioFinishRecorderListener{
        void onFinish(float second,String filepath);
    }
    private AudioFinishRecorderListener mListener;
    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener){
        mListener = listener;
    }
    //获取音量大小的
    private Runnable mGetVoiceLevelRuanable = new Runnable() {
        @Override
        public void run() {
            while (isRecording){
                try {
                    Thread.sleep(100);
                    mTime +=0.1f;
                    handler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    private static final int MSG_AUDIO_PREPARED = 0X110;
    private static final int MSG_VOICE_CHANGED= 0X111;
    private static final int MSG_DIALOG_DIMISS= 0X112;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_AUDIO_PREPARED:
                    //显示在audio end prepared以后
                    dialogManager.showRecordingDialog();
                    isRecording=true;
                    new Thread(mGetVoiceLevelRuanable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    dialogManager.updateVoiceLevel(audioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS:
                    dialogManager.dimissDialog();
                    break;
            }
        };
    };
    @Override
    public void wellPrepared() {
        handler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording){
                    //根据x，y的坐标，判断是否想要取消
                    if (wantToCancel(x,y)){
                        changeState(STATE_CANAEL);
                    }else {
                        changeState(STATE_RECORDING);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (!mReady){
                    reset();
                    return super.onTouchEvent(event);
                }
                if (!isRecording || mTime<0.6f){
                    dialogManager.tooShort();
                    audioManager.cancel();
                    handler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS,1300);
                }else if (mCurrent == STATE_RECORDING){//正常录制结束
                    dialogManager.dimissDialog();
                    audioManager.release();

                    if (mListener!= null){
                        mListener.onFinish(mTime,audioManager.getCurrentFilePath());
                    }

                }else if (mCurrent == STATE_CANAEL){
                    dialogManager.dimissDialog();
                    audioManager.cancel();
                }
                reset();
                break;
        }

        return super.onTouchEvent(event);
    }
    //恢复状态及标志位
    private void reset(){
        mTime=0;
        mReady = false;
        isRecording = false;
        changeState(STATE_NORMAL);
    }
    private boolean wantToCancel(int x, int y) {
        if (x<0 || x> getWidth()){
            return true;
        }
        if (y< -DISTANCE_CANCEL_Y || y>getHeight()+DISTANCE_CANCEL_Y){
            return true;
        }
        return false;
    }

    private void changeState(int stateRecording) {
        if (mCurrent != stateRecording){
            mCurrent = stateRecording;
            switch (stateRecording){
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.str_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_recording);
                    //更新diolog
                    if (isRecording){
                        dialogManager.recording();
                    }
                    break;
                case STATE_CANAEL:
                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_cancal);
                    dialogManager.wantCancel();
                    break;
            }
        }
    }

}
