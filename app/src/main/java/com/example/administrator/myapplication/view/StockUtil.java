package com.example.administrator.myapplication.view;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.zero.cdownload.CDownload;
import com.zero.cdownload.config.CDownloadConfig;
import com.zero.cdownload.config.ConnectConfig;
import com.zero.cdownload.config.ThreadPoolConfig;
import com.zero.cdownload.listener.CDownloadListener;

import java.io.File;
import java.util.Locale;

/**
 * Created by lianjh on 2018\10\22 0022.
 * Current page
 */

public class StockUtil {

    public void downLoadFile(final Context context, String fileurl) {
//        String downLoadUrl = "http://118.190.115.150:8889" + fileurl;
        String downLoadUrl = "http://118.190.115.150:8889/jeesite/upload/20181018/signaturefiles/1/EAM2.0.pdf";
        CDownloadConfig downloadConfig = CDownloadConfig.build()

                .setDiskCachePath("/sdcard/Download")

                .setConnectConfig(ConnectConfig.build().setConnectTimeOut(10000).setReadTimeOut(20000))

                .setIoThreadPoolConfig(ThreadPoolConfig.build().setCorePoolSize(4).setMaximumPoolSize(100).setKeepAliveTime(60));

        CDownload.getInstance().init(downloadConfig);

        CDownload.getInstance().create(downLoadUrl, new CDownloadListener() {
            @Override
            public void onPreStart() {
                Log.e("HongLi", "onPreStart");
            }

            @Override
            public void onProgress(long maxSIze, long currentSize) {
                Log.e("HongLi", "in onProgress maxSIze:" + maxSIze + ";currentSize:" + currentSize);
            }

            @Override
            public void onComplete(String localFilePath) {
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) context,new String[]{ "Manifest.permission.WRITE_EXTERNAL_STORAGE"},1);
                }
                Intent intent=new Intent("android.intent.action.VIEW");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                File file = new File(localFilePath);
                Uri data;
                // 判断版本大于等于7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                }
                data = Uri.fromFile(file);
                String type = getMIMEType(file);
                openFile(context, data, type);
            }

            @Override
            public void onError(String errorMessage) {

            }

            @Override
            public void onCancel() {
                Log.e("HongLi", "onCancel");
            }
        });
        CDownload.getInstance().start(downLoadUrl);
    }

    private String getMIMEType(File file) {

        String type="*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名*/
        String end=fName.substring(dotIndex,fName.length()).toLowerCase(Locale.CHINA);
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<FileFormatParams.MIME_MapTable.length;i++){
            if(end.equals(FileFormatParams.MIME_MapTable[i][0]))
                type = FileFormatParams.MIME_MapTable[i][1];
        }
        return type;
    }

    public void openFile(Context context, Uri uri, String type){
        try{
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(/*uri*/uri, type);
            context.startActivity(intent);
            Intent.createChooser(intent, "请选择对应的软件打开该附件！");
        }catch (ActivityNotFoundException e) {
            Toast.makeText(context, "sorry附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
        }
    }
}
