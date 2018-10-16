package com.example.administrator.myapplication.dialog;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;

/**
 * Created by wxs on 2017/12/28.
 */
public class LightSwitch {
    private CameraManager manager;// 声明CameraManager对象
    private Camera m_Camera = null;// 声明Camera对象

    public LightSwitch(Context context) {
        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//        try {
//            String[] camerList = manager.getCameraIdList();
//            for (String str : camerList) {
//            }
//        } catch (CameraAccessException e) {
//            Log.e("error", e.getMessage());
//        }
    }
    /**
     * 手电筒控制方法
     *
     * @param context
     * @param lightStatus
     * @return
     */
    public void lightSwitch(Context context, final boolean lightStatus) {



        if (lightStatus) { // 关闭手电筒
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (m_Camera != null) {
                    m_Camera.stopPreview();
                    m_Camera.release();
                    m_Camera = null;
                }
            }
        } else { // 打开手电筒
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                final PackageManager pm = context.getPackageManager();
                final FeatureInfo[] features = pm.getSystemAvailableFeatures();
                for (final FeatureInfo f : features) {
                    if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) { // 判断设备是否支持闪光灯
                        if (null == m_Camera) {
                            m_Camera = Camera.open();
                        }
                        final Camera.Parameters parameters = m_Camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        m_Camera.setParameters(parameters);
                        m_Camera.startPreview();
                    }
                }
            }
        }
    }
}
