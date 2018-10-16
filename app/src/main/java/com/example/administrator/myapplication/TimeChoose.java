package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.myapplication.fragment.Child1Fragment;
import com.example.administrator.myapplication.fragment.Child2Fragment;
import com.example.administrator.myapplication.fragment.Child3Fragment;
import com.example.administrator.myapplication.view.DataBean;
import com.example.administrator.myapplication.view.RxBus;
import com.example.administrator.myapplication.view.RxBusMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2018\1\30 0030.
 */

public class TimeChoose extends Activity implements OnRefreshListener, OnRefreshLoadMoreListener{
    private EditText time;
//    private TextView eventbus;
    //当标签数目小于等于3个时，标签栏不可滑动
//    public static final int MOVABLE_COUNT = 3;
//    private int tabCount = 3;
    RxBus rxBus;
    SmartRefreshLayout refreshLayout;
    ViewPager mViewPager;
    TabLayout tableLayout;
    RecyclerView recyclerview;
    Toolbar toolbar;
    /**
     * 时间控件
     */
    private DatePickerDialog datePickerDialog;
    private CumTimePickerDialog timePickerDialog;

    private int layoutnum;
    StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        findViewById();
        initView();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
//                    page++;
//                    commonViewModel.getSelectLiveData(StockDetailActivity.this, HttpManager.getStockDetailLine("", stockEntity.getSTOCKNUM(), page, 10), StockLineEntity.class).observe(StockDetailActivity.this, new Observer<ArrayList<StockLineEntity>>() {
//                        @Override
//                        public void onChanged(ArrayList<StockLineEntity> attendanceEntities) {
//                            if (TestUtil.isNotNull(attendanceEntities)) {
//                                mAdapter.loadMore(attendanceEntities);
//                                refreshLayout.finishLoadMore();
//                            } else {
//                                Toast.makeText(getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                                refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
//                            }
//                        }
//                    });
            }
        }, 2000);
    }

    @Override
    public void onRefresh(final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
//                page = 1;
//                commonViewModel.getSelectLiveData(StockDetailActivity.this, HttpManager.getStock("", page, 1, UDSTOCKID), StockEntity.class).observe(StockDetailActivity.this, new Observer<ArrayList<StockEntity>>() {
//                    @Override
//                    public void onChanged(ArrayList<StockEntity> attendanceEntities) {
//                        if (TestUtil.isNotNull(attendanceEntities)) {
//                            stockEntity = attendanceEntities.get(0);
//                            setValue(attendanceEntities.get(0));
//                            commonViewModel.getSelectLiveData(StockDetailActivity.this, HttpManager.getStockDetailLine("", attendanceEntities.get(0).getSTOCKNUM(), page, 10), StockLineEntity.class).observe(StockDetailActivity.this, new Observer<ArrayList<StockLineEntity>>() {
//                                @Override
//                                public void onChanged(ArrayList<StockLineEntity> attendanceEntities) {
//                                    mAdapter.refresh(attendanceEntities);
//                                    refreshLayout.finishRefresh();
//                                    refreshLayout.setNoMoreData(false);
//                                }
//                            });
//                        }
//                    }
//                });
            }
        }, 2000);
    }

    public enum Item {
        Fragment1("工作人员", Child1Fragment.class),
        Fragment2("作业启停记录", Child2Fragment.class),
        Fragment3("机组启停记录", Child3Fragment.class),
        ;
        public String name;
        public Class<? extends Fragment> clazz;
        Item(String name, Class<? extends Fragment> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }
    protected void findViewById() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        recyclerview= (RecyclerView) findViewById(R.id.recyclerview);
//        tableLayout = (TabLayout) findViewById(R.id.tableLayout);
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        time = (EditText) findViewById(R.id.time);
    }

    protected void initView() {
        initRxBus();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        initRxBus();
//        //MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
//        tableLayout.setTabMode(tabCount <= MOVABLE_COUNT ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
//        //指示条的颜色
//        tableLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
//        tableLayout.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.dimens_1sp));
//        //关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
//        tableLayout.setupWithViewPager(mViewPager, true);

        refreshLayout.setOnRefreshLoadMoreListener((OnRefreshLoadMoreListener) this);
        setDataListener();
        time.setOnClickListener(new MydateListener());

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
//        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerview.setBackgroundColor(getResources().getColor(R.color.background1));

//        recyclerview.setAdapter(mAdapter = new BaseRecyclerAdapter<StockLineEntity>(R.layout.list_stock_child) {
//            @Override
//            protected void onBindViewHolder(SmartViewHolder holder, final StockLineEntity model, int position) {
//                holder.text(R.id.ITEMNUM, "物资编码:" + model.getITEMNUM());
//                holder.text(R.id.ITEMDESCRIPTION, "物资描述:" + model.getITEMDESCRIPTION());
//                holder.text(R.id.MSEHL, "单位:" + model.getMSEHL());
//                holder.text(R.id.TOTAL, TestUtil.isNotNull(model.getTOTAL()) ? "账存数量:" + model.getTOTAL() : "暂无描述");
//                holder.text(R.id.ACTUALQTY, TestUtil.isNotNull(model.getACTUALQTY()) ? "实盘数量:" + model.getACTUALQTY() : "暂无描述");
//                holder.itemView.findViewById(R.id.icon_delete).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new AlertDialog.Builder(StockDetailActivity.this)
//                                .setTitle("删除")
//                                .setMessage("确认删除？")
//                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//
//                                    }
//                                })
//                                .setNegativeButton(
//                                        "确定",
//                                        new DialogInterface.OnClickListener() {
//
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                delete(String.valueOf(model.getUDSTOCKLINEID()));
//                                                dialog.dismiss();
//                                            }
//                                        })
//                                .show();
//                    }
//                });
//            }
//        });
//
//        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//            }
//        });
    }
    private void initRxBus() {
        rxBus = RxBus.getIntanceBus();
        registerRxBus(RxBusMessage.class, new Consumer<RxBusMessage>() {
            @Override
            public void accept(@NonNull RxBusMessage rxBusMessage) throws Exception {

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
    /**
     * 设置时间选择器*
     */
    private void setDataListener() {
        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        int hour = objTime.get(Calendar.HOUR_OF_DAY);
        int minute = objTime.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(this, new datelistener(), iYear, iMonth, iDay);
        timePickerDialog = new CumTimePickerDialog(this, new timelistener(), hour, minute, true);
    }

    private class MydateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            layoutnum = 0;
            sb = new StringBuffer();
            layoutnum = view.getId();
            datePickerDialog.show();
        }
    }

    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                sb.append(year % 100 + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year % 100 + "-" + monthOfYear + "-" + dayOfMonth);
            }
            timePickerDialog.show();
        }
    }

    private class timelistener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            sb.append(" ");
            if (i1 < 10) {
                sb.append(i + ":" + "0" + i1 + ":00");
            } else {
                sb.append(i + ":" + i1 + ":00");
            }

//            Log.i(TAG,"sb="+sb);
            if (layoutnum == time.getId()) {
                time.setText(sb);
            }
        }
    }
}
