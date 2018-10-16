package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018\2\8 0008.
 */

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 * Created by zhuwentao on 2016-08-10.
 */
public class PopupActivity extends AppCompatActivity implements View.OnClickListener {

    /** TextView选择框 */
    private TextView mSelectTv;

    /** popup窗口里的ListView */
    private ListView mTypeLv;

    /** popup窗口 */
    private PopupWindow typeSelectPopup;

    /** 模拟的假数据 */
    private List<String> testData;

    /** 数据适配器 */
    private ArrayAdapter<String> testDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        initUI();
        initListener();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mSelectTv = (TextView) findViewById(R.id.tv_select_input);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mSelectTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_input:
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
                    typeSelectPopup.showAsDropDown(mSelectTv, 0, 10);
                }
                break;
        }
    }

    /**
     * 初始化popup窗口
     */
    private void initSelectPopup() {
        mTypeLv = new ListView(this);
        TestData();
        // 设置适配器
        testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, testData);
        mTypeLv.setAdapter(testDataAdapter);

        // 设置ListView点击事件监听
        mTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里获取item数据
                String value = testData.get(position);
                // 把选择的数据展示对应的TextView上
                mSelectTv.setText(value);
                // 选择完后关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
        typeSelectPopup = new PopupWindow(mTypeLv, mSelectTv.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 取得popup窗口的背景图片
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_filter_down);
        typeSelectPopup.setBackgroundDrawable(drawable);
        typeSelectPopup.setFocusable(true);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
    }

    /**
     * 模拟假数据
     */
    private void TestData() {
        testData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String str = new String("数据" + i);
            testData.add(str);
        }
    }
}