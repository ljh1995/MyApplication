package com.example.administrator.myapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseFragment;
import com.example.administrator.myapplication.dialog.SelectDateDialog;
import com.jeek.calendar.widget.calendar.OnCalendarClickListener;
import com.jeek.calendar.widget.calendar.schedule.ScheduleLayout;
import com.jeek.calendar.widget.calendar.schedule.ScheduleRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2018\5\17 0017.
 */

public class DateFragment extends BaseFragment implements OnCalendarClickListener, SelectDateDialog.OnSelectDateListener {
    private TextView yearTextView; //显示日期
    /**
     * 日历
     **/
    private ScheduleLayout slSchedule;
    /**
     * 显示数据
     **/
    private ScheduleRecyclerView rvScheduleList;

    /**
     * 没有数据
     **/
    private RelativeLayout rLNoTask;

    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;

    private long mTime;

    private String dailylogdate; //日志日期

    @Nullable
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_logs, container, false);
    }

    @Override
    protected void bindView() {
        yearTextView = searchViewById(R.id.year_text_id);
        rLNoTask = searchViewById(R.id.rlNoTask);
        slSchedule = searchViewById(R.id.slSchedule);
        slSchedule.setOnCalendarClickListener(this);
        initUi();
        initScheduleList();
    }

    private void initUi() {
        String time = Calendar.getInstance().get(Calendar.YEAR) + "年" + (Calendar.getInstance().get(Calendar.MONTH)) + "月";
        yearTextView.setText(time);
    }

    @Override
    protected void initData() {
        super.initData();
        initDate();
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
    }

    public void resetMainTitleDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        if (year == calendar.get(Calendar.YEAR) &&
                month == calendar.get(Calendar.MONTH) &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            yearTextView.setText(year + "年" + month + "月");
        } else {
            if (year == calendar.get(Calendar.YEAR)) {
                yearTextView.setText(year + "年" + month + "月");
            } else {

                yearTextView.setText(year + "年" + month + "月");
            }
        }
    }
    @Override
    protected void bindData() {
        super.bindData();
        resetScheduleList();
    }
    public void resetScheduleList() {
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        setCurrentSelectDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClickDate(int year, int month, int day) {
        setCurrentSelectDate(year, month, day);
        resetScheduleList();
    }
    @Override
    public void onPageChange(int year, int month, int day) {
    }

    /**
     * 初始化数据
     **/
    private void initScheduleList() {
        rvScheduleList = slSchedule.getSchedulerRecyclerView();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvScheduleList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvScheduleList.setItemAnimator(itemAnimator);
//        initAdapter(new ArrayList<R_WORKREPORT.WORKREPORT>());

    }
    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear = year;
        mCurrentSelectMonth = month + 1; //下标加1表示月份
        mCurrentSelectDay = day;
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
        if (mCurrentSelectMonth < 10) {
            dailylogdate = mCurrentSelectYear + "-0" + mCurrentSelectMonth + "-" + mCurrentSelectDay;

        } else {
            dailylogdate = mCurrentSelectYear + "-" + mCurrentSelectMonth + "-" + mCurrentSelectDay;
        }
        rLNoTask.setVisibility(View.GONE);
//        myProjectAdapter.removeAll(myProjectAdapter.getData());
        showLoadingDialog(getResources().getString(R.string.load_more_text));
//        getData();

    }
    @Override
    public void onSelectDate(final int year, final int month, final int day, long time, int position) {
        slSchedule.getMonthCalendar().setCurrentItem(position);
        slSchedule.postDelayed(new Runnable() {
            @Override
            public void run() {
                slSchedule.getMonthCalendar().getCurrentMonthView().clickThisMonth(year, month, day);
            }
        }, 100);
        mTime = time;
    }

//    /**
//     * 获取数据*
//     */
//    private void initAdapter(final List<R_WORKREPORT.WORKREPORT> list) {
//        myProjectAdapter = new MyProjectAdapter(getActivity(), R.layout.list_item_my_project, list);
//        rvScheduleList.setAdapter(myProjectAdapter);
//        myProjectAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), MyReallyActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("workreport", (Serializable) myProjectAdapter.getData().get(position));
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 0);
//
//            }
//        });
//    }
}
