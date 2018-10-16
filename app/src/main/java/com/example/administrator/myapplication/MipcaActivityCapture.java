package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.camera.CameraManager;
import com.example.administrator.myapplication.decoding.CaptureActivityHandler;
import com.example.administrator.myapplication.decoding.InactivityTimer;
import com.example.administrator.myapplication.dialog.AssetNumDialog;
import com.example.administrator.myapplication.dialog.LightSwitch;
import com.example.administrator.myapplication.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Initial the camera
 * @author tc
 */
public class MipcaActivityCapture extends Activity implements Callback {
//    ArrayList<Wpmaterial> items = new ArrayList<>();
//    ArrayList<Udinspoasset> udinspoassets = new ArrayList<>();

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private TextView back;
    private LinearLayout btn_write, btn_open;
    ImageView btn_write_img, btn_open_img;
    boolean isOpen = false;
    LightSwitch lightSwitch;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        getData();
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        btn_write = (LinearLayout) findViewById(R.id.btn_write);
        btn_open = (LinearLayout) findViewById(R.id.btn_open);
        btn_write_img = (ImageView) findViewById(R.id.btn_write_img);
        btn_open_img = (ImageView) findViewById(R.id.btn_open_img);
        lightSwitch = new LightSwitch(this);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        btn_write.setOnClickListener(btn_writeOnClickListener);
        btn_open.setOnClickListener(btn_openOnClickListener);
    }

    public void getData() {
//        Intent intent = getIntent();
//        type = intent.getStringExtra("type");
//        if(intent.hasExtra("data")) {
//            items = (ArrayList<Wpmaterial>) intent.getSerializableExtra("data");
//        }
//        if(intent.hasExtra("udinspoassets")) {
//            udinspoassets = (ArrayList<Udinspoasset>) intent.getSerializableExtra("udinspoassets");
//        }
//        if(intent.hasExtra("branch")) {
//            branch =  intent.getStringExtra("branch");
//        }
//        if(intent.hasExtra("udbelong")) {
//            udbelong =  intent.getStringExtra("udbelong");
//        }
//        if(intent.hasExtra("assettype")) {
//            assettype =  intent.getStringExtra("assettype");
//        }
    }

    private OnClickListener btn_writeOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            AssetNumDialog assetNumDialog = new AssetNumDialog(MipcaActivityCapture.this, "请输入设备号", "搜索");
        }
//        @Override
//        public void onClick(View v) {
//            AssetNumDialog assetNumDialog = new AssetNumDialog(MipcaActivityCapture.this, "请输入设备号", "搜索");
//            assetNumDialog.setOnSelectSetListener(new AssetNumDialog.OnSelectSetListener() {
//                @Override
//                public void OnSelectSet(String assetNum) {
//                    if(type.equals("shouye")) {
//                        Intent resultIntent = new Intent();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("result", assetNum);
//                        Log.i("MipcaActivityCapture", "resultString=" + assetNum);
//                        resultIntent.putExtras(bundle);
//                        resultIntent.setClass(MipcaActivityCapture.this, Results_Activity.class);
////            this.setResult(RESULT_OK, resultIntent);
//                        startActivityForResult(resultIntent, RESULT_OK);
//                    }else if(type.equals("wuliao")) {
//                        Log.i("MipcaActivityCapture", "resultString=" + assetNum);
//                        boolean flag = false;
//                        int position = 0;
//                        Wpmaterial wpmaterial1 = new Wpmaterial();
//                        for(int i = 0; i < items.size(); i++) {
//                            if(items.get(i).itemnum.equals(assetNum)) {
//                                flag = true;
//                                wpmaterial1 = items.get(i);
//                                position = i;
//                                break;
//                            }
//                        }
//                        if(flag == false) {
//                            Toast cheatSheet = Toast.makeText(MipcaActivityCapture.this, "物料不在领料单中", Toast.LENGTH_SHORT);
//                            cheatSheet.show();
//                        }else {
//                            Intent resultIntent = new Intent();
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("wpmaterial", wpmaterial1);
//                            bundle.putInt("position", position);
//                            resultIntent.putExtras(bundle);
//                            resultIntent.setClass(MipcaActivityCapture.this, WpmaterialDetailsActivity.class);
////            this.setResult(RESULT_OK, resultIntent);
//                            startActivityForResult(resultIntent, RESULT_OK);
//                        }
//                    }else if(type.equals("inspo")) {
//                        Log.i("MipcaActivityCapture", "resultString=" + assetNum);
//                        boolean flag = false;
//                        int position = 0;
//                        Udinspoasset udinspoasset = new Udinspoasset();
//                        for(int i = 0; i < udinspoassets.size(); i++) {
//                            if(udinspoassets.get(i).getUdinspoassetnum().equals(assetNum)) {
//                                flag = true;
//                                udinspoasset = udinspoassets.get(i);
//                                position = i;
//                                break;
//                            }
//                        }
//                        if(flag == false) {
//                            Toast cheatSheet = Toast.makeText(MipcaActivityCapture.this, "资产不在巡检项中", Toast.LENGTH_SHORT);
//                            cheatSheet.show();
//                        }else {
//                            Intent resultIntent = new Intent();
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("udinspoassetnum", udinspoasset.getUdinspoassetnum());
//                            bundle.putString("branch", branch);
//                            bundle.putString("udbelong", udbelong);
//                            bundle.putString("assettype", assettype);
////                            udinspoassetnum = getIntent().getExtras().getString("udinspoassetnum");
////                            branch = getIntent().getExtras().getString("branch");
////                            udbelong = getIntent().getExtras().getString("udbelong");
////                            assettype = getIntent().getExtras().getString("assettype");
//
//                            resultIntent.putExtras(bundle);
//                            resultIntent.setClass(MipcaActivityCapture.this, UdinspojxxmNew_Activity.class);
////            this.setResult(RESULT_OK, resultIntent);
//                            startActivityForResult(resultIntent, RESULT_OK);
//                        }
//                    }
//                }
//            });
//        }
    };

    private OnClickListener btn_openOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!isOpen) {
                isOpen = true;
                btn_open_img.setImageDrawable(getResources().getDrawable(R.drawable.flashlight_open_icon));
                CameraManager.get().flashHandler();
//                lightSwitch.lightSwitch(MipcaActivityCapture.this, false);
            }else {
                isOpen = false;
                btn_open_img.setImageDrawable(getResources().getDrawable(R.drawable.flashligth_close_icon));
                CameraManager.get().flashHandler();
//                lightSwitch.lightSwitch(MipcaActivityCapture.this, true);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     * @param result
     */
    public void handleDecode(Result result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();


//        if (resultString.equals("")) {
//            Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
//        }else {
//            if(type.equals("shouye")) {
//                Intent resultIntent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("result", resultString);
//                Log.i("MipcaActivityCapture", "resultString=" + resultString);
//                resultIntent.putExtras(bundle);
//                resultIntent.setClass(MipcaActivityCapture.this, Results_Activity.class);
////            this.setResult(RESULT_OK, resultIntent);
//                startActivityForResult(resultIntent, RESULT_OK);
//            }else if(type.equals("wuliao")) {
//                Log.i("MipcaActivityCapture", "resultString=" + resultString);
//                boolean flag = false;
//                int position = 0;
//                Wpmaterial wpmaterial1 = new Wpmaterial();
//                for(int i = 0; i < items.size(); i++) {
//                    if(items.get(i).itemnum.equals(resultString)) {
//                        flag = true;
//                        wpmaterial1 = items.get(i);
//                        position = i;
//                        return;
//                    }
//                }
//                if(flag == false) {
//                    Toast cheatSheet = Toast.makeText(this, "物料不在领料单中", Toast.LENGTH_SHORT);
//                    cheatSheet.show();
//                }else {
//                    Intent resultIntent = new Intent();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("wpmaterial", wpmaterial1);
//                    bundle.putInt("position", position);
//                    resultIntent.putExtras(bundle);
//                    resultIntent.setClass(MipcaActivityCapture.this, WpmaterialDetailsActivity.class);
////            this.setResult(RESULT_OK, resultIntent);
//                    startActivityForResult(resultIntent, RESULT_OK);
//                }
//            }else if(type.equals("inspo")) {
//                Log.i("MipcaActivityCapture", "resultString=" + resultString);
//                boolean flag = false;
//                int position = 0;
//                Udinspoasset udinspoasset = new Udinspoasset();
//                for(int i = 0; i < udinspoassets.size(); i++) {
//                    if(udinspoassets.get(i).getUdinspoassetnum().equals(resultString)) {
//                        flag = true;
//                        udinspoasset = udinspoassets.get(i);
//                        position = i;
//                        break;
//                    }
//                }
//                if(flag == false) {
//                    Toast cheatSheet = Toast.makeText(MipcaActivityCapture.this, "资产不在巡检项中", Toast.LENGTH_SHORT);
//                    cheatSheet.show();
//                }else {
//                    Intent resultIntent = new Intent();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("udinspoassetnum", udinspoasset.getUdinspoassetnum());
//                    bundle.putString("branch", branch);
//                    bundle.putString("udbelong", udbelong);
//                    bundle.putString("assettype", assettype);
////                            udinspoassetnum = getIntent().getExtras().getString("udinspoassetnum");
////                            branch = getIntent().getExtras().getString("branch");
////                            udbelong = getIntent().getExtras().getString("udbelong");
////                            assettype = getIntent().getExtras().getString("assettype");
//
//                    resultIntent.putExtras(bundle);
//                    resultIntent.setClass(MipcaActivityCapture.this, UdinspojxxmNew_Activity.class);
////            this.setResult(RESULT_OK, resultIntent);
//                    startActivityForResult(resultIntent, RESULT_OK);
//                }
//            }
//        }
//        MipcaActivityCapture.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}