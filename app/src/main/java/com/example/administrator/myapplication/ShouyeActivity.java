package com.example.administrator.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.myapplication.fragment.DateFragment;
import com.example.administrator.myapplication.view.DataBean;
import com.example.administrator.myapplication.view.RefreshActivity;
import com.example.administrator.myapplication.view.RxBus;
import com.example.administrator.myapplication.view.RxBusMessage;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.function.Consumer;

/**
 * Created by Administrator on 2018\1\30 0030.
 */

public class ShouyeActivity extends Activity{
    private long mPressedTime = 0;
    private Button time;
    private Button refresh;
    private Button picture;
    private Button slid;
    private Button occur;
    private Button popup;
    private Button popup2;
    private Button saoma;
    private Button dialog;
    private Button occurs;
    private Button fragment;
    private Button refresh1;
    private Button pickpicture;
    private Button flybutton;
    private Button date;
    private Button service;
    private Button muchfloat;
    private Button CircleMenu;
    private Button notification;
    private Button clickall;
    private Button lock;
    private Button lock2;
    private Button choose;
    private Button delete;
    private Button pageWater;
    private Button pictureWatre;
    private Button Bus;
    private Button recorder;

    private PopupWindow popupWindow;

    private NotificationManager notificationManager;
    private static final int flag = 1;

    private LinearLayout youLinearlayout;
    private LinearLayout meLinearlayout;
    private LinearLayout usLinearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);
        notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        findViewById();
        initView();
    }
    protected void findViewById() {
        recorder= (Button) findViewById(R.id.recorder);
        Bus= (Button) findViewById(R.id.Bus);
        time = (Button) findViewById(R.id.time);
        refresh = (Button) findViewById(R.id.refresh);
        picture = (Button) findViewById(R.id.picture);
        slid = (Button) findViewById(R.id.slid);
        occur = (Button) findViewById(R.id.occur);
        popup = (Button) findViewById(R.id.popupwindow);
        popup2 = (Button) findViewById(R.id.popup2);
        saoma = (Button) findViewById(R.id.saoma);
        dialog = (Button) findViewById(R.id.dialog);
        occurs= (Button) findViewById(R.id.occurs);
        fragment= (Button) findViewById(R.id.fragment);
        refresh1= (Button) findViewById(R.id.refresh1);
        pickpicture= (Button) findViewById(R.id.pickpicture);
        flybutton= (Button) findViewById(R.id.flybutton);
        date= (Button) findViewById(R.id.date);
        service= (Button) findViewById(R.id.service);
        muchfloat= (Button) findViewById(R.id.muchfloat);
        CircleMenu= (Button) findViewById(R.id.CircleMenu);
        notification= (Button) findViewById(R.id.notification);
        clickall= (Button) findViewById(R.id.clickall);
        lock= (Button) findViewById(R.id.lock);
        lock2= (Button) findViewById(R.id.lock2);
        choose= (Button) findViewById(R.id.choose);
        delete= (Button) findViewById(R.id.delete);
        pageWater= (Button) findViewById(R.id.pageWater);
        pictureWatre= (Button) findViewById(R.id.pictureWatre);
    }
    protected void initView() {
        pictureWatre.setOnClickListener(pictureWatreOnClickListener);
        pageWater.setOnClickListener(pageWaterOnClickListener);
        delete.setOnClickListener(deleteOnClickListener);
        choose.setOnClickListener(chooseOnClickListener);
        clickall.setOnClickListener(clickallOnClickListener);
        CircleMenu.setOnClickListener(CircleMenuOnClickListener);
        muchfloat.setOnClickListener(muchfloatOnClickListener);
        service.setOnClickListener(serviceOnClickListener);
        time.setOnClickListener(timeOnClickListener);
        refresh.setOnClickListener(refreshOnClickListener);
        picture.setOnClickListener(pictureOnClickListener);
        slid.setOnClickListener(slidOnClickListener);
        occur.setOnClickListener(occurOnClickListener);
        popup.setOnClickListener(popupOnClickListener);
        popup2.setOnClickListener(popup2OnClickListener);
        saoma.setOnClickListener(saomaOnClickListener);
        dialog.setOnClickListener(dialogOnClickListener);
        occurs.setOnClickListener(occursOnClickListener);
        fragment.setOnClickListener(fragmentOnClickListener);
        refresh1.setOnClickListener(refresh1OnClickListener);
        pickpicture.setOnClickListener(pickpictureOnClickListener);
        flybutton.setOnClickListener(flybuttonOnClickListener);
        date.setOnClickListener(dateOnClickListener);
        notification.setOnClickListener(notificationOnClickListener);
        lock.setOnClickListener(lockOnClickListener);
        lock2.setOnClickListener(lock2OnClickListener);
        Bus.setOnClickListener(BusOnClickListener);
        recorder.setOnClickListener(recorderOnClickListener);
    }
    private View.OnClickListener recorderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShouyeActivity.this, RecorderActivity.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener BusOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //RxBus与EventBus冲突，只可选其一使用
            Intent intent = new Intent(ShouyeActivity.this, BusActivity.class);
//            EventBus.getDefault().postSticky(new DataBean("1","我是eventbus","传输数据"));
            RxBusMessage rxBusMessage = new RxBusMessage();
            rxBusMessage.setMessage("我是RxBus");
            RxBus.getIntanceBus().post(rxBusMessage);
            startActivity(intent);
        }
    };
    private View.OnClickListener pictureWatreOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShouyeActivity.this, PictureWaterActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener pageWaterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShouyeActivity.this, PageWaterActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShouyeActivity.this, DeleteActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener chooseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShouyeActivity.this, ChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener lock2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShouyeActivity.this, Lock2Activity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener lockOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShouyeActivity.this, LockActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener notificationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //震动也有两种设置方法,与设置铃声一样,在此不再赘述
            long[] vibrate = new long[]{0, 500, 1000, 1500};
            //获取NotificationManager实例
            NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //获取PendingIntent
            Intent mainIntent = new Intent(ShouyeActivity.this, FloatActivity.class);
            PendingIntent mainPendingIntent = PendingIntent.getActivity(ShouyeActivity.this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //创建 Notification.Builder 对象
            NotificationCompat.Builder builder = new NotificationCompat.Builder(ShouyeActivity.this)
                    .setSmallIcon(R.mipmap.ic_launcher)
//                    //点击通知后自动清除
//                    .setAutoCancel(true)
                    .setContentTitle("我是带Action的Notification")
                    .setContentText("点我会打开MainActivity")
                    //使用系统默认的震动参数,会与自定义的冲突
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                    //自定义震动效果
//                    .setVibrate(vibrate)
                    .setContentIntent(mainPendingIntent);
            //发送通知
            Notification notification = builder.build();
            //设置 Notification 的 flags = FLAG_NO_CLEAR
            //FLAG_AUTO_CANCEL 表示该通知能被状态栏的清除按钮给清除掉
            //等价于 builder.setAutoCancel(true);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notifyManager.notify(flag, notification);
        }
    };

    private View.OnClickListener clickallOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, ClickAllActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener CircleMenuOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, CircleActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener muchfloatOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, FloatActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener serviceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, ServiceActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, XmxsActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener flybuttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, DragFloatActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener pickpictureOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, WxDemoActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener refresh1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, RefreshActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener fragmentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, FragmentActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener occursOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, BannerActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener timeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, TimeChoose.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener refreshOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, RereshActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener pictureOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, MainActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener slidOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, MainActivity1.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener occurOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, ChoseitemActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener popupOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, PopupActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener popup2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow(popup2);
        }
    };
    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(ShouyeActivity.this).inflate(
                R.layout.work_popup_window, null);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.abc_popup_background_mtrl_mult));
        popupWindow.showAsDropDown(view, 0, 20);

        youLinearlayout = (LinearLayout) contentView.findViewById(R.id.you);
        meLinearlayout = (LinearLayout) contentView.findViewById(R.id.me);
        usLinearlayout = (LinearLayout) contentView.findViewById(R.id.us);
        youLinearlayout.setOnClickListener(youOnClickListener);
        meLinearlayout.setOnClickListener(meOnClickListener);
        usLinearlayout.setOnClickListener(usOnClickListener);
    }
    private View.OnClickListener youOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(Work_DetailsActivity.this, Work_PlanActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("workOrder", workOrder);
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener meOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(Work_DetailsActivity.this, Work_PlanActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("workOrder", workOrder);
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener usOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(Work_DetailsActivity.this, Work_PlanActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("workOrder", workOrder);
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener saomaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("type", "shouye");
            intent.setClass(ShouyeActivity.this, MipcaActivityCapture.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener dialogOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ShouyeActivity.this, DialogAcitivity.class);
            startActivityForResult(intent, 0);
        }
    };
//双击退出
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        }else{//退出程序
            this.finish();
            System.exit(0);
        }
    }
}
