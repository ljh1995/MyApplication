package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\1\30 0030.
 */

public class ChoseitemActivity extends Activity {
    private EditText item;
    /**
     * 故障等级*
     */
    private ArrayList<DialogMenuItem> tMenuItems = new ArrayList<>();
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choseitem);
        findViewById();
        initView();
        addCulevelData();//工作票是否合格
    }

    protected void findViewById() {
        item = (EditText) findViewById(R.id.item);
    }

    protected void initView() {
        item.setOnClickListener(itemOnClickListener);
    }


    private View.OnClickListener itemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final NormalListDialog dialog = new NormalListDialog(ChoseitemActivity.this, tMenuItems);
            dialog.title("请选择")//
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            dialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                    item.setText(tMenuItems.get(position).mOperName);

                    dialog.dismiss();
                }
            });
        }
    };

    /**
     * 添加数据*
     */
    private void addCulevelData() {
        String[] lctypes = getResources().getStringArray(R.array.isquliified_tab_titles);

        for (int i = 0; i < lctypes.length; i++)
            tMenuItems.add(new DialogMenuItem(lctypes[i], 0));


    }

}
