package com.example.administrator.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.administrator.myapplication.view.ImageUtil;

/**
 * Created by lianjh on 2018\8\28 0028.
 * Current page
 */

public class PictureWaterActivity extends Activity {
    private ImageView mSourImage;
    private ImageView mWartermarkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturewater);

        initView();
    }
    private void initView(){
//        mSourImage = (ImageView) findViewById(R.id.sour_pic);
        mWartermarkImage = (ImageView) findViewById(R.id.wartermark_pic);
        Bitmap sourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.w);
        //根据文件路径处理为Bitmap对象
//        Bitmap sourBitmap = BitmapFactory.decodeFile(fileName);
//        mSourImage.setImageBitmap(sourBitmap);

//        Bitmap waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b);
//
//        Bitmap watermarkBitmap = ImageUtil.createWaterMaskCenter(sourBitmap, waterBitmap);
//        watermarkBitmap = ImageUtil.createWaterMaskLeftBottom(this, watermarkBitmap, waterBitmap, 0, 0);
//        watermarkBitmap = ImageUtil.createWaterMaskRightBottom(this, watermarkBitmap, waterBitmap, 0, 0);
//        watermarkBitmap = ImageUtil.createWaterMaskLeftTop(this, watermarkBitmap, waterBitmap, 0, 0);
//        watermarkBitmap = ImageUtil.createWaterMaskRightTop(this, watermarkBitmap, waterBitmap, 0, 0);

//        Bitmap textBitmap = ImageUtil.drawTextToLeftTop(this, sourBitmap, "左上角", 16, Color.RED, 0, 0);
//        textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "右下角", 16, Color.RED, 0, 0);
//        textBitmap = ImageUtil.drawTextToRightTop(this, textBitmap, "右上角", 16, Color.RED, 0, 0);
//        textBitmap = ImageUtil.drawTextToLeftBottom(this, textBitmap, "左下角", 16, Color.RED, 0, 0);
        Bitmap textBitmap = ImageUtil.drawTextToCenter(this, sourBitmap, "中间", 30, Color.RED);
//        String fileNames = ImageUtil.saveBitmap(this,textBitmap);
        mWartermarkImage.setImageBitmap(textBitmap);
    }

}
