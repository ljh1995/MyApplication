package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.view.GestureLockViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\7\30 0030.
 */

public class Lock2Activity extends AppCompatActivity{
    private GestureLockViewGroup mGestureLockViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_login);

        findViewById();
        initView();
    }
    protected void findViewById() {
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
    }

    private void initView() {
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        Toast.makeText(Lock2Activity.this, "错误5次...",
                                Toast.LENGTH_SHORT).show();
                        mGestureLockViewGroup.setUnMatchExceedBoundary(5);
                    }

                    @Override
                    public void onChoose(List<Integer> list) {
                        //调用登陆接口  用户名和手势密码
//                        ImageLogin(AccountUtils.getUserName(FastLoginActivity.this),list.toString());
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {

                    }

                    @Override
                    public void onBlockSelected(int cId) {
                    }
                });
    }

    /**
     * 手势登录*
     */
//    private void ImageLogin(final String username,final String password) {
//        getLoadingDialog("正在登陆...").show();
//        new AsyncTask<String, String, String>() {
//            @Override
//            protected String doInBackground(String... strings) {
//                return AndroidClientService.mobilelogin_ImageLogin(FastLoginActivity.this,username,password);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                if (isJsonArrary(s)){
//                    try {
//                        JSONArray jsonArray = new JSONArray(s);
//                        if (jsonArray.getJSONObject(0).has("success")){
////                            Toast.makeText(FastLoginActivity.this,jsonArray.getJSONObject(0).getString("success"),Toast.LENGTH_SHORT).show();
//                            getUserApp(AccountUtils.getUserName(FastLoginActivity.this));
//                        }else {
//                            mGestureLockViewGroup.reset();
//                            getLoadingDialog("正在登陆...").dismiss();
//                            MessageUtils.showErrorMessage(FastLoginActivity.this,"手势密码错误");
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }else {
//                    mGestureLockViewGroup.reset();
//                    getLoadingDialog("正在登陆...").dismiss();
//                    MessageUtils.showErrorMessage(FastLoginActivity.this,"手势密码错误");
//                    mGestureLockViewGroup.reset();
//                }
//            }
//        }.execute();
//    }

    /**
     * 跳转至主界面*
     */
    private void startIntent(ArrayList<String> list) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("appidArray",list);//根据appid获取权限
        startActivity(intent);
    }

    /**
     * 获取用户名密码数据*
     */
//    private void getUserApp(final String username) {
//        new AsyncTask<String, String, String>() {
//            @Override
//            protected String doInBackground(String... strings) {
//                return AndroidClientService.mobilelogin_getUserApp(FastLoginActivity.this,username);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                mGestureLockViewGroup.reset();
//                getLoadingDialog("正在登陆...").dismiss();
//                if (isJsonArrary(s)){
//                    try {
//                        JSONArray jsonArray = new JSONArray(s);
//                        JSONObject jsonObject;
//                        ArrayList<String > arrayList = new ArrayList<String>();
//                        for (int i =0;i<jsonArray.length();i++){
//                            jsonObject = jsonArray.getJSONObject(i);
//                            if (jsonObject.has("appid")){
//                                arrayList.add(jsonObject.getString("appid"));
//                            }
//                        }
//                        startIntent(arrayList);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }else {
//                    MessageUtils.showErrorMessage(FastLoginActivity.this,"获取权限失败");
//                }
//            }
//        }.execute();
//    }

    private boolean isJsonArrary(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
