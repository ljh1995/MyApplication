package com.example.administrator.myapplication.view;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by lianjh on 2018\10\15 0015.
 * Current page
 */

public class AudioManager {
    private MediaRecorder mediaRecorder;
    private String mDir;
    private String mCurrentFilePath;

    private static AudioManager mInstance;
    private boolean isPrepared;

    private AudioManager(String dir){
        mDir = dir;
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

    //回调准备完毕
    public interface AudioStateListen{
        void wellPrepared();
    }
    public AudioStateListen mListen;
    public void setOnAudioStateListen(AudioStateListen listen){
        mListen = listen;
    }

    public static AudioManager getInstance(String dir){
        if (mInstance == null){
            synchronized (AudioManager.class){
                if (mInstance == null)
                    mInstance = new AudioManager(dir);

            }
        }
        return mInstance;
    }

    public void prepareAudio(){
        try {
            isPrepared=false;
            File file= new File(mDir);
            if (!file.exists())
                file.mkdirs();

            String filename=generateFileName();
            File dir =new  File(file,filename);

            mCurrentFilePath = dir.getAbsolutePath();
            mediaRecorder = new MediaRecorder();
            //设置输出文件
            mediaRecorder.setOutputFile(dir.getAbsolutePath());
            //设置mediaRecorder的音频源为麦克风
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            //设置音频编码
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();

            //准备结束
            isPrepared=true;
            if (mListen!=null){
                mListen.wellPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//随机生成文件名
    private String generateFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    public int getVoiceLevel(int maxLevel){
        if (isPrepared){
            try {
                //mediaRecorder.getMaxAmplitude()的范围是1-32767
                return maxLevel * mediaRecorder.getMaxAmplitude()/32768 + 1;
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return 1;
    }
    public void release(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
    }
    public void cancel(){
        release();
        if (mCurrentFilePath!=null){
            File file=new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath=null;
        }
    }
}
