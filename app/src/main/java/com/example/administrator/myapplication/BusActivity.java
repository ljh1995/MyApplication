package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.view.CallBackUtil;
import com.example.administrator.myapplication.view.DataBean;
import com.example.administrator.myapplication.view.OkhttpUtil;
import com.example.administrator.myapplication.view.RxBus;
import com.example.administrator.myapplication.view.RxBusMessage;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lianjh on 2018\9\19 0019.
 * Current page
 */

public class BusActivity extends Activity{
    private TextView eventbus;
    private TextView rxbus;
    private Button okhttp_post;
    private Button okhttp_get;
    private Button OKHttp_upload;
    private Button OKHttp_download;
    Toolbar toolbar;
    RxBus rxBus;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        findViewById();
        initView();
//        EventBus.getDefault().register(this);
    }
    protected void findViewById() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        rxbus = (TextView) findViewById(R.id.rxbus);
        eventbus = (TextView)findViewById(R.id.eventbus);
        okhttp_post = (Button) findViewById(R.id.OKHttp_post);
        okhttp_get = (Button) findViewById(R.id.OKHttp_get);
        OKHttp_upload = (Button) findViewById(R.id.OKHttp_upload);
        OKHttp_download = (Button) findViewById(R.id.OKHttp_download);
    }
    protected void initView() {
        initRxBus();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        okhttp_post.setOnClickListener(okhttp_postOnClickListener);
        okhttp_get.setOnClickListener(okhttp_getOnClickListener);
        OKHttp_upload.setOnClickListener(OKHttp_uploadOnClickListener);
        OKHttp_download.setOnClickListener(OKHttp_downloadOnClickListener);
        if (message == null || message.equals("")){

        }else {
            rxbus.setText(message);
        }
    }

    private View.OnClickListener OKHttp_uploadOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<File> fileList = new ArrayList<>();
            fileList.add(new File("path01"));
            fileList.add(new File("path02"));
            String url = "https://www.baidu.com/";

            OkhttpUtil.okHttpUploadListFile(url, fileList, "files", OkhttpUtil.FILE_TYPE_FILE, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void onResponse(String response) {

                }
            });
              //单个文件上传
//            File file = new File(Environment.getExternalStorageDirectory()+"/kwwl/abc.jpg");
//            HashMap<String, String> paramsMap = new HashMap<>();
//            paramsMap.put("title","title");
//            OkhttpUtil.okHttpUploadFile(url, file, "image", OkhttpUtil.FILE_TYPE_IMAGE, paramsMap, new CallBackUtil.CallBackString() {
//                @Override
//                public void onFailure(Call call, Exception e) {
//
//                }
//
//                @Override
//                public void onProgress(float progress, long total) {
//
//                }
//
//                @Override
//                public void onResponse(String response) {
//
//                }
//            });
        }
    };
    private View.OnClickListener OKHttp_downloadOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "https://www.baidu.com/";
            //此时的回调接口是CallBackFile类型的，且在创建CallBackFile对象时需要传递两个参数：文件保存的目录和文件名。
            OkhttpUtil.okHttpDownloadFile(url, new CallBackUtil.CallBackFile("dir","fileName") {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void onProgress(float progress, long total) {//文件下载进度，执行在UI线程
                    super.onProgress(progress, total);
                }

                @Override
                public void onResponse(File response) {//请求成功时调用，执行在UI线程

                }
            });
        }
    };
    private View.OnClickListener okhttp_getOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "https://api.github.com/repos/square/retrofit/contributors";
            //传递参数
//            HashMap<String,String> paramsMap = new HashMap<>();
//            paramsMap.put("userName","zhangsan");
//            paramsMap.put("password","000000");
            OkhttpUtil.okHttpGet(url, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {}

                @Override
                public void onResponse(String response) {
                    Toast.makeText(BusActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    Log.d("kwwl",response);
                }
            });

              //返回一个javaBean对象的例子
//            OkhttpUtil.okHttpGet(url, new CallBackUtil<RxBusMessage>() {
//                @Override
//                public RxBusMessage onParseResponse(Call call, Response response) {//解析响应，执行在子线程
//                    try {
//                        String result = response.body().string();
//                        RxBusMessage student = new Gson.fromJson(result, RxBusMessage.class);
//                        return student;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//
//                @Override
//                public void onFailure(Call call, Exception e) {
//
//                }
//
//                @Override
//                public void onResponse(RxBusMessage response) {//返回Student对象，执行在UI线程
//
//                }
//            });
        }
    };
    private View.OnClickListener okhttp_postOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "https://www.baidu.com/";
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("title","title");
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void onResponse(String response) {

                }
            });
             //传递json格式参数的post请求例子
//            Student student = new Student();
//            student.userName = "zhangsan";
//            student.password = "000000";
//            String jsonStr = new Gson.toJson(student);
//            String url = "https://www.baidu.com/";
//
//            OkhttpUtil.okHttpPostJson(url, jsonStr, new CallBackUtil.CallBackString() {
//                @Override
//                public void onFailure(Call call, Exception e) {
//
//                }
//
//                @Override
//                public void onResponse(String response) {//请求成功时调用，执行在UI线程
//
//                }
//            });
        }
    };
    private void initRxBus(){
        rxBus = RxBus.getIntanceBus();
        registerRxBus(RxBusMessage.class, new Consumer<RxBusMessage>() {
            @Override
            public void accept(@NonNull RxBusMessage rxBusMessage) throws Exception {
                Log.e("NewsMainPresenter",rxBusMessage.getMessage());
                message = rxBusMessage.getMessage().toString();
                Toast.makeText(BusActivity.this,message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public <T> void registerRxBus(Class<T> eventType, Consumer<T> action) {
        Disposable disposable = rxBus.doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("NewsMainPresenter", throwable.toString());
            }
        });
        rxBus.addSubscription(this,disposable);
    }
    //定义处理接收的方法
//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void onMessageEvent(DataBean event){
//        eventbus.setText(event.title + event.desc);
//        Toast.makeText(BusActivity.this, event.title + event.desc, Toast.LENGTH_LONG).show();
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        注销注册
//        EventBus.getDefault().unregister(this);
    }
}
