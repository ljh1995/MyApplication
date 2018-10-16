package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;
import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

/**
 * Created by Administrator on 2018\7\13 0013.
 */

public class CircleActivity extends AppCompatActivity {
    private CircleMenu circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_circle);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        final ViewGroup snackbarContainer = (ViewGroup) findViewById(R.id.snackbar_contaner);
        circleMenu = (CircleMenu) findViewById(R.id.circleMenu);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()){
                    case R.id.favorite:
                        Toast.makeText(CircleActivity.this, "0", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        Toast.makeText(CircleActivity.this, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.alert:
                        Toast.makeText(CircleActivity.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.place:
                        Toast.makeText(CircleActivity.this, "3", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.edit:
                        Toast.makeText(CircleActivity.this, "4", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {

            }

            @Override
            public void onMenuCollapsed() {

            }
        });
    }
}
