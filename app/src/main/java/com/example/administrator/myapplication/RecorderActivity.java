package com.example.administrator.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.myapplication.view.AudioManager;
import com.example.administrator.myapplication.view.AudioRecorderButton;
import com.example.administrator.myapplication.view.MediaManager;

import java.util.ArrayList;

/**
 * Created by lianjh on 2018\10\15 0015.
 * Current page
 */

public class RecorderActivity  extends AppCompatActivity implements View.OnClickListener {
    private ListView recyclerView;
    private ArrayAdapter<Recorder> mAdapter;
    private ArrayList<Recorder> mDatas = new ArrayList<>();

    private View animView;

    private AudioRecorderButton audioRecorderButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        recyclerView = (ListView) findViewById(R.id.recyclerView);
        audioRecorderButton = (AudioRecorderButton) findViewById(R.id.recorder_btn);
        audioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float second, String filepath) {
                Recorder recorder = new Recorder(second,filepath);
                //更新数据集
                mDatas.add(recorder);
                mAdapter.notifyDataSetChanged();
                //显示list的最新数据
                 recyclerView.setSelection(mDatas.size()-1);
            }
        });
        mAdapter = new RecorderAdapter(this,mDatas);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断如果第一条在播放，点击第二条播放时，第一条暂停播放
                if (animView != null){
                    animView.setBackgroundResource(R.drawable.adj);
                    animView = null;
                }
                //播放动画
                animView = view.findViewById(R.id.id_anim);
                animView.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable animationDrawable = (AnimationDrawable) animView.getBackground();
                animationDrawable.start();
                //播放音频
                MediaManager.playSound(mDatas.get(position).filepath, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        animView.setBackgroundResource(R.drawable.adj);
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    class Recorder{
        float time;
        String filepath;

        public Recorder(float time,String filepath){
            super();
            this.filepath=filepath;
            this.time=time;
        }

        public float getTime() {
            return time;
        }

        public void setTime(float time) {
            this.time = time;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }
    }
    @Override
    public void onClick(View v) {

    }
}
