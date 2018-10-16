package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

/**
 * Created by Administrator on 2018\7\13 0013.
 */

public class FloatActivity extends AppCompatActivity {
    private ActionMenu actionMenuBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_float);

        actionMenuBottom = (ActionMenu) findViewById(R.id.actionMenuBottom);

        actionMenuBottom.addView(R.drawable.search, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
        actionMenuBottom.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
        actionMenuBottom.addView(R.drawable.write);


        actionMenuBottom.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                switch (index){
                    case 1:
                        showMessage("Click " + index);
                        break;
                    case 2:
                        showMessage("Click " + index);
                        break;
                    case 3:
                        showMessage("Click " + index);
                        break;
                }
            }

            @Override
            public void onAnimationEnd(boolean isOpen) {
                //       showMessage("animation end : " + isOpen);
            }
        });
    }
    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }

    private void showMessage(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
