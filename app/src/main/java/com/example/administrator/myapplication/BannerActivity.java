package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.Arrays;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

public class BannerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        findViewById();
    }
    protected void findViewById() {
        Banner banner = (Banner)findViewById(R.id.banner);
        banner.setImageLoader(new BannerImageLoader());
        banner.setImages(Arrays.asList(R.drawable.b,R.drawable.d));
        banner.start();
    }
    private class BannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource((Integer)path);
        }
    }
}
