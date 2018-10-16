package com.example.administrator.myapplication.view;

import android.media.*;

import java.io.IOException;

/**
 * Created by lianjh on 2018\10\16 0016.
 * Current page
 */

public class MediaManager {
    private static MediaPlayer mediaPlayer;
    private static boolean isPause;

    public static void playSound(String filepath,MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    return false;
                }
            });
        }else {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void pause(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            isPause = true;
        }
    }

    public static void resume(){
        if (mediaPlayer != null && isPause){
            mediaPlayer.start();
            isPause = false;
        }
    }
    public static void release(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
